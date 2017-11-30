/* Copyright (c) 2016, 2017                                               */
/*       Institute of Software, Chinese Academy of Sciences               */
/* This file is part of ROLL, a Regular Omega Language Learning library.  */
/* ROLL is free software: you can redistribute it and/or modify           */
/* it under the terms of the GNU General Public License as published by   */
/* the Free Software Foundation, either version 3 of the License, or      */
/* (at your option) any later version.                                    */

/* This program is distributed in the hope that it will be useful,        */
/* but WITHOUT ANY WARRANTY; without even the implied warranty of         */
/* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the          */
/* GNU General Public License for more details.                           */

/* You should have received a copy of the GNU General Public License      */
/* along with this program.  If not, see <http://www.gnu.org/licenses/>.  */

package cn.ac.ios.util;


import cn.ac.ios.machine.Machine;
import cn.ac.ios.machine.buchi.DBA;
import cn.ac.ios.machine.dfa.DFA;
import cn.ac.ios.machine.dfa.DFAAcc;
import cn.ac.ios.machine.fdfa.FDFA;
import cn.ac.ios.words.APList;
import dk.brics.automaton.Automaton;
import dk.brics.automaton.State;
import dk.brics.automaton.Transition;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntIntHashMap;
import gnu.trove.map.hash.TIntObjectHashMap;

import java.util.ArrayList;
import java.util.List;

public class UtilMachine {
	
	private UtilMachine() {
		
	}
	
	private static State getState(TIntObjectMap<State> map, int stateNr) {
		State state = map.get(stateNr);
		if(state == null) {
			state = new State();
			map.put(stateNr, state);
		}
		return state;
	}
	
	public static Automaton dfaToDkAutomaton(Machine machine) {
		TIntObjectMap<State> map = new TIntObjectHashMap<>();
		return dfaToDkAutomaton(map, machine);
	}
	
	private static Automaton dfaToDkAutomaton(TIntObjectMap<State> map, Machine machine) {
		dk.brics.automaton.Automaton dkAut = new dk.brics.automaton.Automaton();
		APList aps = machine.getInAPs();
		
		for(int stateNr = 0; stateNr < machine.getStateSize(); stateNr ++) {
			State state = getState(map, stateNr);
			// initial states
			if(machine.getInitialState() == stateNr) {
				dkAut.setInitialState(state);
			}
			// final states
			if(machine.getAcceptance().isFinal(stateNr)) {
				state.setAccept(true);
			}

			for (int letter = 0; letter < aps.size(); letter ++) {
				int succ = machine.getSuccessor(stateNr, letter);
				State stateSucc = getState(map, succ);
				state.addTransition(new Transition(aps.get(letter).toString().charAt(0),
						stateSucc));
			}
		}
		
		dkAut.setDeterministic(true);
		return dkAut;
	}
	static public DFA DBAToProgess(DFA B,int init) {
		// Require totality of B
		DFA A = B.clone();
		cn.ac.ios.machine.State accept = A.createState();
		DFAAcc f = A.getAcceptance();
		for (int letter = 0; letter < A.getInAPs().size(); letter++) {
			accept.addTransition(letter,accept.getIndex());
		}
		for (int i = 0; i < A.getStateSize(); i++) {
			cn.ac.ios.machine.State u = A.getState(i);
			cn.ac.ios.machine.Transition[] trans = u.getTransitions();

			for (int letter = 0; letter < A.getInAPs().size(); letter++) {
				cn.ac.ios.machine.Transition t = trans[letter];
				if (f.isFinal(t.getSuccessor())){
					u.addTransition(letter,accept.getIndex());
				}
			}
		}
		A.getAcceptance().finals.clear();
		A.getAcceptance().setFinal(accept.getIndex());
		A.setInitial(init);
		return A;
		/*
		DFA A = new DFA(B.getInAPs());


		cn.ac.ios.machine.State accept = A.createState();
		DFAAcc f = B.getAcceptance();
		TIntIntHashMap homo = new TIntIntHashMap();

		for (int i = 0; i < B.getStateSize(); i++) {
			if (f.isFinal(i)){
				homo.put(i,accept.getIndex());
			}
			else {
				cn.ac.ios.machine.State u = A.createState();
				homo.put(i,u.getIndex());
			}
		}

		for (int i = 0; i < B.getStateSize(); i++) {
			cn.ac.ios.machine.State u = B.getState(i);
			cn.ac.ios.machine.State v = A.getState(homo.get(i));

			cn.ac.ios.machine.Transition[] trans = u.getTransitions();
			for (int letter = 0; letter < B.getInAPs().size(); letter++) {
				v.addTransition(letter,homo.get(trans[letter].getSuccessor()));
			}
		}
		A.setInitial(init);
		A.getAcceptance().setFinal(accept.getIndex());

		return A;
		*/
	}
	static public FDFA DBAToFDFA(DFA B){
		// Require totality of B
		DFA leading = B.clone();
		leading.getAcceptance().finals.clear();
		int n = B.getStateSize();
		ArrayList<DFA> progess = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			progess.add(UtilMachine.DBAToProgess(B,i));
		}
		FDFA A = new FDFA(leading,progess);
		return A;
	}

}
