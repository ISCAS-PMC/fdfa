package cn.ac.ios.machine.fdfa;

import cn.ac.ios.machine.*;
import cn.ac.ios.machine.dfa.*;
import cn.ac.ios.words.APList;
import cn.ac.ios.words.Word;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FDFA implements Machine, Cloneable {

    public final DFA leadingDFA;
    public final List<DFA> progressDFAs;
    private DFA currentDFA = null;
    private int currentDFAIndex = -1;
//    private final FDFAAcc acceptance;

//    public FDFA(APList aps) {
//        this.leadingDFA = new DFA(aps);
//        this.progressDFAs = new ArrayList<>();
////        this.acceptance = new FDFAAcc();
//    }

    public FDFA(DFA leadDFA, List<DFA> proDFAs) {
        assert leadDFA != null;
        assert proDFAs != null;
        this.leadingDFA = leadDFA;
        this.progressDFAs = proDFAs;
//        ArrayList<DFAAcc> proAcc = new ArrayList(
//                this.progressDFAs.stream()
//                        .map(DFA::getAcceptance)
//                        .collect(Collectors.toList())
//        );

        assert leadDFA.getAcceptance().getAccType() == AcceptanceType.DFA;
//        this.acceptance = new FDFAAcc(proAcc);
    }

//    public FDFA(DFA leadingDFA, List<DFA> progressDFAs, FDFAAcc acceptance, List<String> labels) {
//        this.leadingDFA = leadingDFA;
//        this.progressDFAs = progressDFAs;
////        this.acceptance = acceptance;
//        this.labels = labels;
//    }

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
//            builder.append("P" + index + " " + labels.get(index) + "\n");
            builder.append("P" + index + "\n");
            builder.append(progressDFAs.get(index).toString() + "\n");
        }
        return builder.toString();
    }

    @Override
    public FDFA clone() throws CloneNotSupportedException {

        DFA leadingClone = this.leadingDFA.clone();

        ArrayList<DFA> progressArray = new ArrayList<>();

        for (DFA progess : this.progressDFAs){
            DFA progessClone = progess.clone();
            progressArray.add(progessClone);
        }

        return new FDFA(leadingClone,progressArray);
//        FDFA res =  new FDFA(
//                this.leadingDFA.clone(),
//                this.progressDFAs.clone(),
//                this.acceptance.clone(),
//                this.labels.clone();
//        );
    }

//    @Override
//    public Acceptance getAcceptance() {
//        return this.acceptance;
//    }

    @Override
    public Acceptance getAcceptance() {
        return null;
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

    public int getCuurentDFAIndex() {
        return this.currentDFAIndex;
    }
    //TODO
    @Override
    public State resetCureentState() {
        this.currentDFA = this.leadingDFA;
        this.currentDFAIndex = -1;
        return currentDFA.resetCureentState();
    }

    @Override
    public State getCurrentState() {
        return currentDFA.getCurrentState();
    }


    @Override
    public State run(Word w) {
        this.resetCureentState();
        return this.currentDFA.run(w);
    }

    @Override
    public State continueRun(Word w) {
        return this.currentDFA.continueRun(w);
    }

    public State shiftToProgess(){
        this.currentDFAIndex = this.leadingDFA.getCurrentState().getIndex();
        this.currentDFA = this.progressDFAs.get(this.currentDFAIndex);
        System.out.println("Shift to P_" + this.currentDFAIndex);
        return this.currentDFA.resetCureentState();
    }


    public State run(WordPair P) {
        assert P.isNormalized();

        this.run(P.getU());
        for (int t = 0; t < P.getI(); t++) {
            this.continueRun(P.getV());
        }
        this.shiftToProgess();
        for (int t = 0; t < P.getJ(); t++) {
            this.currentDFA.run(P.getV());
        }
        return this.currentDFA.getCurrentState();
    }

}
