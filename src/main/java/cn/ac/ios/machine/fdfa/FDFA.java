package cn.ac.ios.machine.fdfa;

import cn.ac.ios.machine.Acceptance;
import cn.ac.ios.machine.AcceptanceType;
import cn.ac.ios.machine.dfa.*;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public class FDFA implements Acceptance {

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

    public FDFA(DFA leadDFA, List<DFA> proDFAs) {
        assert leadDFA != null;
        assert proDFAs != null;
        this.leadingDFA = leadDFA;
        this.progressDFAs = proDFAs;
    }

    private final DFA leadingDFA;
    private final List<DFA> progressDFAs;

    public DFA getLeadingDFA() {
        return leadingDFA;
    }

    private List<String> labels = new ArrayList<>();

    public void setLeadingLabels(List<String> labels) {
        this.labels = labels;
    }

    public DFA getProgressDFA(int state) {
        assert state < progressDFAs.size();
        return progressDFAs.get(state);
    }

    public int getNumProgressDFA() {
        return progressDFAs.size();
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("M: \n");
        builder.append(leadingDFA.toString() + "\n");
        for(int index = 0; index < progressDFAs.size(); index ++) {
            builder.append("P" + index + " " + labels.get(index) + "\n");
            builder.append(progressDFAs.get(index).toString() + "\n");
        }
        return builder.toString();
    }

}
