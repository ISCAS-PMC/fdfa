package cn.ac.ios.machine.fdfa;

import cn.ac.ios.machine.Acceptance;
import cn.ac.ios.machine.AcceptanceType;
import cn.ac.ios.machine.dfa.DFAAcc;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public class FDFAAcc implements Acceptance, Cloneable{

    private final List<DFAAcc> progressAcceptance;

    public FDFAAcc() {
        this.progressAcceptance = new ArrayList<>();
    }

    public FDFAAcc(List<DFAAcc> progressAcceptance) {
        this.progressAcceptance = progressAcceptance;
    }

    public List<DFAAcc> getProgressAcceptance() {
        return progressAcceptance;
    }

    @Override
    public AcceptanceType getAccType() {
        return AcceptanceType.FDFA;
    }

    @Override
    public boolean isFinal(int state) {
        return false;
    }

    @Override
    public BitSet getFinals() {
        return null;
    }

    @Override
    public void setFinal(int state) {

    }

    @Override
    public FDFAAcc clone() throws CloneNotSupportedException {
        //TODO
        return null;
    }
}
