package cn.ac.ios.machine.fdfa;

import cn.ac.ios.machine.dfa.*;
import cn.ac.ios.machine.State;
import cn.ac.ios.words.APList;

import java.util.Arrays;

public class BasicOperations {
    // Boolean Operations
    static public FDFA complement(FDFA F){
        try {
            FDFA res = F.clone();
            FDFAAcc Acc = (FDFAAcc) res.getAcceptance();

            for (int i = 0; i < res.getNumProgressDFA() ; i++) {
                DFAAcc Fi = Acc.getProgressAcceptance().get(i);
                Fi.getFinals().flip(0,Fi.getFinals().size()-1);
            }

        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return null;
    }
    static public FDFA intersection(FDFA F1, FDFA F2){

        APList ap = F1.getInAPs();
        int LSize = F1.getStateSize();
        int RSize = F2.getStateSize();
        int ResSize = LSize * RSize;

        assert F2.getInAPs().equals(ap);

        FDFA res = new FDFA(F1.getInAPs());
        //TODO use template to refactoring
        ProductDFA leading = res.getLeadingDFA();
        for (int i = 0; i < ResSize ; i++) {
            leading.createState();
        }

        for (int i = 0; i < F1.getStateSize(); i++) {
            for (int j = 0; j < F2.getStateSize(); j++) {

                ProudctState u = leading.getState(i,j);
                State ui = F1.getLeadingDFA().getState(i);
                State uj = F1.getLeadingDFA().getState(i);

                for (int a = 0; a < ap.size(); a++){
                    int xIndex = ui.getSuccessor(a);
                    int yIndex = uj.getSuccessor(a);
                    u.addTransition(a,xIndex * RSize + yIndex);
                }
                DFA Pi = F1.getProgressDFA(i);
                DFA Pj = F1.getProgressDFA(j);
                //TODO DFA union intersection
                // add (i,j)-a->(x,y) in leading DFA for any a
                // construct Progess_i,j
                // set Acceptance for Progess_i,j in FDFAAcc


            }
        }
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
