package cn.ac.ios.machine.dfa;

import cn.ac.ios.machine.Machine;
import cn.ac.ios.machine.dfa.DFA;
import cn.ac.ios.machine.dfa.DFAState;

public class ProudctState extends DFAState {

    private final int first;
    private final int second;
    private final int LSize;
    private final int RSize;

    public ProudctState(DFA machine, int LIndex, int LSize, int RIndex, int RSize) {
        super(machine, LIndex * RSize + RIndex);
        this.first = LIndex;
        this.second = RIndex;
        this.LSize = LSize;
        this.RSize = RSize;
    }
}
