package cn.ac.ios.machine.fdfa;

import cn.ac.ios.machine.*;
import cn.ac.ios.machine.dfa.*;
import cn.ac.ios.words.APList;
import cn.ac.ios.words.Word;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FDFA implements Machine {

    private final DFA leadingDFA;
    private final List<DFA> progressDFAs;
    private final FDFAAcc acceptance;

    public FDFA(APList aps) {
        this.leadingDFA = new DFA(aps);
        this.progressDFAs = new ArrayList<>();
        this.acceptance = new FDFAAcc();
    }

    public FDFA(DFA leadDFA, List<DFA> proDFAs) {
        assert leadDFA != null;
        assert proDFAs != null;
        this.leadingDFA = leadDFA;
        this.progressDFAs = proDFAs;
        ArrayList<DFAAcc> proAcc = new ArrayList(
                this.progressDFAs.stream()
                        .map(DFA::getAcceptance)
                        .collect(Collectors.toList())
        );

        assert leadDFA.getAcceptance().getAccType() == AcceptanceType.DFA;
        this.acceptance = new FDFAAcc(proAcc);
    }

    public FDFA(DFA leadingDFA, List<DFA> progressDFAs, FDFAAcc acceptance, List<String> labels) {
        this.leadingDFA = leadingDFA;
        this.progressDFAs = progressDFAs;
        this.acceptance = acceptance;
        this.labels = labels;
    }

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

    @Override
    public FDFA clone() throws CloneNotSupportedException {
//        TODO
//                FDFA res =  new FDFA(
//                this.leadingDFA.clone(),
//                this.progressDFAs.clone(),
//                this.acceptance.clone(),
//                this.labels.clone();
//        );

        return null;
    }

    @Override
    public Acceptance getAcceptance() {
        return this.acceptance;
    }

    @Override
    public APList getInAPs() {
        return null;
    }

    @Override
    public APList getOutAPs() {
        assert false: "Not supported in FDFA";
        return null;
    }

    @Override
    public State getState(int index) {
        return null;
    }

    @Override
    public int getStateSize() {
        return 0;
    }

    @Override
    public State createState() {
        return null;
    }

    @Override
    public void setInitial(int state) {

    }

    @Override
    public void setInitial(State state) {

    }

    @Override
    public int getInitialState() {
        return 0;
    }


    @Override
    public int getSuccessor(int state, int letter) {
        return 0;
    }

    @Override
    public int getSuccessor(Word word) {
        return 0;
    }

    @Override
    public int getSuccessor(int state, Word word) {
        return 0;
    }

    @Override
    public Transition makeTransition(int state, int out) {
        return null;
    }

    @Override
    public State makeState(int index) {
        return null;
    }
}
