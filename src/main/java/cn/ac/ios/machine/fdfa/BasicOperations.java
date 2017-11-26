package cn.ac.ios.machine.fdfa;

import cn.ac.ios.machine.Transition;
import cn.ac.ios.machine.dfa.*;
import cn.ac.ios.machine.State;
import cn.ac.ios.util.UtilMachine;
import cn.ac.ios.words.APList;
import cn.ac.ios.words.Word;
import dk.brics.automaton.Automaton;
import gnu.trove.map.hash.TIntIntHashMap;

import javax.rmi.CORBA.Util;
import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;

public class BasicOperations {

    // Boolean Operations
    static public FDFA complement(FDFA F){
        try {
            FDFA res = F.clone();
            for (DFA progess : res.progressDFAs ) {
                DFAAcc f = progess.getAcceptance();
                f.finals.flip(0,progess.getStateSize());
            }
            return res;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    static public DFA ProductDFA(ArrayList<StatePair> decomp, DFA A1, DFA A2, BinaryOperator<Boolean> isAccepted){

        APList ap = A1.getInAPs();
        assert A2.getInAPs().equals(ap);

        DFA C = new DFA(ap);
        HashMap<StatePair,StatePair> m = new HashMap<>();
        LinkedList<StatePair> Q = new LinkedList<>();

        C.setInitial(C.createState());
        StatePair head = new StatePair(C.InitalState(),A1.InitalState(),A2.InitalState());

        m.put(head,head);
        decomp.add(head);
        Q.add(head);

        while ( Q.size() > 0 ) {
            head = Q.removeFirst();
//            boolean isAccepted = A1.getAcceptance().isFinal(head.s1.getIndex())
//                                 &&
//                                 A2.getAcceptance().isFinal(head.s2.getIndex());
            if (isAccepted.apply(
                    A1.getAcceptance().isFinal(head.s1.getIndex()),
                    A2.getAcceptance().isFinal(head.s2.getIndex())
            )) {
                C.getAcceptance().setFinal(head.s.getIndex());
            }
            Transition[] trans1 = head.s1.getTransitions();
            Transition[] trans2 = head.s2.getTransitions();
            for (int letter = 0; letter < ap.size(); letter++){
                Transition t1 = trans1[letter];
                Transition t2 = trans2[letter];
                StatePair p = new StatePair(
                        A1.getState(t1.getSuccessor()),
                        A2.getState(t2.getSuccessor())
                );

                StatePair q = m.get(p);
                if (q == null) {
                    p.s = C.createState();
                    m.put(p,p);
                    decomp.add(p);
                    Q.add(p);
                    q = p;
                }
                head.s.addTransition(letter,q.s.getIndex());
            }

        }
        return C;
    }

    static public FDFA ProductFDFA(FDFA F1, FDFA F2,BinaryOperator<Boolean> isAccepted){

        APList ap = F1.getInAPs();
        int LSize = F1.getStateSize();
        int RSize = F2.getStateSize();
        int ResSize = LSize * RSize;

        assert F2.getInAPs().equals(ap);

//        FDFA res = new FDFA(F1.getInAPs());

        ArrayList<StatePair> decomp = new ArrayList<>();
        DFA resLeading = BasicOperations.ProductDFA(
                decomp,
                F1.leadingDFA,
                F2.leadingDFA,
                isAccepted
        );

        ArrayList<DFA> progressList = new ArrayList<>();
        for (StatePair p : decomp) {
            DFA progress = BasicOperations.ProductDFA(
                    new ArrayList<>(),
                    F1.getProgressDFA(p.s1.getIndex()),
                    F2.getProgressDFA(p.s2.getIndex()),
                    isAccepted
            );
            // make sure that states in decomp are in the oder of State::getIndex()
            // res.progressDFAs.set(p.s.getIndex(),progress);
            progressList.add(progress);
        }
        FDFA res = new FDFA(resLeading,progressList);
        return res;
    }

    static public FDFA intersection(FDFA F1, FDFA F2){
        return BasicOperations.ProductFDFA(F1,F2,Boolean::logicalAnd);
    }

    static public FDFA union(FDFA F1, FDFA F2){
        return BasicOperations.ProductFDFA(F1,F2,Boolean::logicalOr);
    }

    // Decision Procedures
    static public WordPair normalize(Word u, Word v, DFA Q){
//        TIntIntHashMap pos = new TIntIntHashMap();
        int[] visited = new int[Q.getStateSize()];
        for (int i = 0; i < Q.getStateSize(); i++)
            visited[i] = -1;
        State s = Q.run(u);
        visited[s.getIndex()] = 0;
//        pos.put(0,s.getIndex());
        for (int n = 1; n <= Q.getStateSize() ; n++) {
            s = Q.continueRun(v);
            if (visited[s.getIndex()] >= 0){
                int i = visited[s.getIndex()];
                int j = n - i;
                WordPair res = new WordPair(
                        u,
                        v,
                        i,
                        j,
                        true,
                        Q
                );
                return res;
            }
            visited[s.getIndex()] = n ;
        }
        return null;
    }
    static public boolean MembershipCheck(FDFA A,WordPair P){
        if (!P.isNormalized()){
            P = BasicOperations.normalize(P.getU(),P.getV(),A.leadingDFA);
        }
        A.run(P);
        DFA Progess = A.getProgressDFA(A.getCuurentDFAIndex());
        return Progess.getAcceptance().isFinal(A.getCurrentState().getIndex());
    }
    static public boolean isEmpty(FDFA F){
        DFA leading = F.getLeadingDFA();
        assert leading.getAcceptance().getFinals().length() == 0;

        for (int u = 0; u < leading.getStateSize(); u++) {
            DFA LoopU = leading.clone();
            LoopU.setInitial(u);
            LoopU.getAcceptance().setFinal(u);
            Automaton dkLoopU = UtilMachine.dfaToDkAutomaton(LoopU);
            Automaton dkPorgU = UtilMachine.dfaToDkAutomaton(F.progressDFAs.get(u));
            Automaton inter = dk.brics.automaton.BasicOperations.intersection(dkLoopU,dkPorgU);
            System.out.println(dkLoopU);
            System.out.println(dkPorgU);
            System.out.println(inter);
            if (! inter.isEmpty()){
                return true;
            }
        }
        return false;
    }

    // TODO
    // implement university by emptiness
    // implement equality by containment


    /**
     * language containment
     * @param F1
     * @param F2
     * @return return true if [F1] <= [F2]
     */
    static public boolean contains(FDFA F1, FDFA F2){
        return BasicOperations.isEmpty(BasicOperations.minus(F1,F2));
    }
    
    static public FDFA minus(FDFA F1, FDFA F2){
        FDFA NF2 = BasicOperations.complement(F2);
        FDFA inter = BasicOperations.intersection(F1,NF2);
        return inter;
    }

}
