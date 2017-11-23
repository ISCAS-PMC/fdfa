package cn.ac.ios.machine.fdfa;
import cn.ac.ios.machine.dfa.DFA;
import cn.ac.ios.machine.dfa.DFAAcc;

public class BasicOperations {
    // Boolean Operations
    static public FDFA complement(FDFA F){
        try {
            FDFA res = F.clone();
            FDFAAcc Acc = (FDFAAcc) res.getAcceptance();

            for (int i = 0; i < res.getNumProgressDFA() ; i++) {
                DFAAcc Fi = Acc.getProgressAcceptance().get(i);
                Fi.getFinals().flip(0,Fi.getFinals().size());
            }

        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return null;
    }
    static public FDFA intersection(FDFA F1, FDFA F2){
        return null;
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
