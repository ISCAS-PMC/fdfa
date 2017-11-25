package cn.ac.ios;

import cn.ac.ios.machine.fdfa.*;
import cn.ac.ios.machine.dfa.*;

public class FDFATest {

    public static void ComplementTest() {
        FDFA F1 = DFAGen.exmple_FDFA1();
        F1 = BasicOperations.complement(F1);
        DFA M = F1.leadingDFA;
        System.out.println(M);
        for (DFA progess : F1.progressDFAs){
            System.out.println(progess);
        }
    }

    public static void InterSectionTest() {
        FDFA F1 = DFAGen.exmple_FDFA1();
        FDFA F2 = DFAGen.exmple_FDFA2();
        FDFA C = BasicOperations.intersection(F1,F2);
        System.out.println(C);
    }

    public static void UnionTest() {
        FDFA F1 = DFAGen.exmple_FDFA1();
        FDFA F2 = DFAGen.exmple_FDFA2();
        FDFA C = BasicOperations.union(F1,F2);
        System.out.println(C);
    }

    public static void main(String[] argv){
        UnionTest();
    }


}
