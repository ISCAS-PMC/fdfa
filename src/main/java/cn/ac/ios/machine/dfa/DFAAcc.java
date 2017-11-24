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

import java.util.BitSet;

import cn.ac.ios.machine.Acceptance;
import cn.ac.ios.machine.AcceptanceType;

public class DFAAcc implements Acceptance {

	public final BitSet finals;
	
	public DFAAcc() {
		finals = new BitSet();
	}

	@Override
	public AcceptanceType getAccType() {
		return AcceptanceType.DFA;
	}

	@Override
	public boolean isFinal(int state) {
		// TODO Auto-generated method stub
		return finals.get(state);
	}

	@Override
	public BitSet getFinals() {
		return (BitSet) finals.clone();
	}
	
	@Override
	public void setFinal(int state) {
		finals.set(state);
	}

}
