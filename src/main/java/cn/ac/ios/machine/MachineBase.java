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

package cn.ac.ios.machine;

import java.util.ArrayList;
import java.util.List;

import cn.ac.ios.words.APList;
import cn.ac.ios.words.Word;

public abstract class MachineBase implements Machine {
	
	protected int initState;
	protected final APList iApList;
	protected final List<State> states;
	protected State currentState = null;

	public MachineBase(APList aps) {
		this.iApList = aps;
		this.states = new ArrayList<>();
	}

	public APList getInAPs() {
		return iApList;
	}
	
	public int getStateSize() {
		return states.size();
	}
	
	public void setInitial(int state) {
		initState = state;

	}
	
	public State createState() {
		State state = this.makeState(states.size());
		states.add(state);
		return state;
	}
	
	public State getState(int state) {
		assert state < states.size();
		return states.get(state);
	}
	
	public int getInitialState() {
		return initState;
	}
	public State InitalState(){
		return this.getState(initState);
	}
	public String toString() {
		return MachineExporterDOT.toString(this);
	}


	@Override
	public State resetCureentState() {
		return this.currentState = this.getState(this.initState);
	}
	@Override
	public State getCurrentState() {
		return currentState;
	}

	@Override
	public State run(Word w) {
		this.resetCureentState();
		return this.continueRun(w);
	}

	@Override
	public State continueRun(Word w) {
		for (int i = 0; i < w.length(); i++) {
			int nextIndex = currentState.getSuccessor(w.getLetter(i));
            System.out.println(
                    "State_" + this.currentState.getIndex() +
                    "  ---" + w.getAPs().get(w.getLetter(i)) + "--->  " +
                    "State_" + nextIndex
            );

            this.currentState = this.getState(nextIndex);
		}
		return this.currentState;
	}
}
