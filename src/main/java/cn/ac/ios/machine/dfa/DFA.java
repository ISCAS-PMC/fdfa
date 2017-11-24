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

package cn.ac.ios.machine.dfa;


import cn.ac.ios.machine.Acceptance;
import cn.ac.ios.machine.MachineBase;
import cn.ac.ios.machine.State;
import cn.ac.ios.machine.Transition;

import cn.ac.ios.words.APList;
import cn.ac.ios.words.Word;
import gnu.trove.map.hash.TIntObjectHashMap;
import java.util.HashMap;

public class DFA extends MachineBase implements Cloneable {
	
	private final DFAAcc acc;
	
	public DFA(APList aps) {
		super(aps);
		acc = new DFAAcc();
	}
	
	public APList getOutAPs() {
		assert false: "Not supported in DFA";
		return null;
	}
	
	public int runMealy(Word word) {
		assert false: "Not supported in DFA";
		return -1;
	}

	@Override
	public DFAAcc getAcceptance() {
		return acc;
	}

	@Override
	public Transition makeTransition(int state, int out) {
		return new DFATransition(state);
	}

	@Override
	public State makeState(int index) {
		return new DFAState(this, index);
	}

    @Override
    public DFA clone()  {
        DFA res = new DFA(this.iApList);

		TIntObjectHashMap<State> m = new TIntObjectHashMap<State>();

		for (State u : this.states)
        	m.put(u.getIndex(),res.createState());

		res.setInitial(m.get(this.initState));

		for (State u : this.states) {

			if (this.acc.isFinal(u.getIndex())){
				res.acc.setFinal(m.get(u.getIndex()).getIndex());
			}

			State v = m.get(u.getIndex());
			Transition[] trans = u.getTransitions();

			// TOTALITY
			for (int letter = 0; letter < this.iApList.size(); letter++) {
				v.addTransition(letter,m.get(trans[letter].getSuccessor()).getIndex());
			}
		}

        return res;
    }
}
