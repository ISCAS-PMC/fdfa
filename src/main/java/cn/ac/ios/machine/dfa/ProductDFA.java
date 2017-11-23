package cn.ac.ios.machine.dfa;

import cn.ac.ios.machine.State;
import cn.ac.ios.words.APList;
import com.sun.tools.internal.xjc.reader.Ring;

public class ProductDFA extends DFA {

    private final int LSize;
    private final int RSize;

    public ProductDFA(APList aps, int LSize, int RSize) {
        super(aps);
        this.LSize = LSize;
        this.RSize = RSize;
    }

    @Override
    public State makeState(int Index) {
        int LIndex = Index / RSize;
        int RIndex = Index % RSize;
        return new ProudctState(this, LIndex, LSize, RIndex, RSize);
    }

    public ProudctState getState(int LState, int RState) {
        return (ProudctState) super.getState(LState * RSize + RState);
    }
}
