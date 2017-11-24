package cn.ac.ios;

import cn.ac.ios.machine.fdfa.*;
import cn.ac.ios.machine.dfa.*;

import java.util.ArrayList;

public class FDFATest {
    public static void main(String[] argv){
//        Complement tested with example_FDFA1
//        FDFA F1 = DFAGen.exmple_FDFA1();
//        F1 = BasicOperations.complement(F1);
//        DFA M = F1.leadingDFA;
//        System.out.println(M);
//        for (DFA progess : F1.progressDFAs){
//            System.out.println(progess);
//        }
        FDFA F1 = DFAGen.exmple_FDFA1();
        FDFA F2 = DFAGen.exmple_FDFA2();
        FDFA C = BasicOperations.intersection(F1,F2);
        System.out.println(C);
//        F1 = BasicOperations.complement(F1);
//        DFA M = F1.leadingDFA;
//        System.out.println(M);
//        for (DFA progess : F1.progressDFAs){
//            System.out.println(progess);
//        }
    }


}
