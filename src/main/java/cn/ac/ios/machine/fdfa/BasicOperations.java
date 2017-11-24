package cn.ac.ios.machine.fdfa;

import cn.ac.ios.machine.Transition;
import cn.ac.ios.machine.dfa.*;
import cn.ac.ios.machine.State;
import cn.ac.ios.words.APList;
import gnu.trove.map.hash.TIntObjectHashMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

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

    static public DFA intersection(ArrayList<StatePair> decomp,DFA A1, DFA A2){
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
            boolean isAccepted = A1.getAcceptance().isFinal(head.s1.getIndex())
                                 &&
                                 A2.getAcceptance().isFinal(head.s2.getIndex());
            if(isAccepted){
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

    static public FDFA intersection(FDFA F1, FDFA F2){

        APList ap = F1.getInAPs();
        int LSize = F1.getStateSize();
        int RSize = F2.getStateSize();
        int ResSize = LSize * RSize;

        assert F2.getInAPs().equals(ap);

//        FDFA res = new FDFA(F1.getInAPs());

        ArrayList<StatePair> decomp = new ArrayList<>();
        DFA resLeading = BasicOperations.intersection(decomp,F1.leadingDFA,F2.leadingDFA);

        ArrayList<DFA> progressList = new ArrayList<>();
        for (StatePair p : decomp) {
            DFA progress = BasicOperations.intersection(
                    new ArrayList<>(),
                    F1.getProgressDFA(p.s1.getIndex()),
                    F2.getProgressDFA(p.s2.getIndex())
            );
            // make sure that states in decomp are in the oder of State::getIndex()
            // res.progressDFAs.set(p.s.getIndex(),progress);
            progressList.add(progress);
        }
        FDFA res = new FDFA(resLeading,progressList);
        return res;
    }

    static public FDFA union(FDFA F1, FDFA F2){
        return null;
    }

    // Decision Procedures
    static boolean isEmpty(FDFA F){
        return false;
    }
    static public FDFA minus(FDFA F1, FDFA F2){
        return null;
    }
}
