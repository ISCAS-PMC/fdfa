package cn.ac.ios;

import cn.ac.ios.machine.fdfa.*;
import cn.ac.ios.machine.dfa.*;
import cn.ac.ios.words.Word;
import cn.ac.ios.machine.State;

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

    public static void DFARunTest() {
        DFA A = DFAGen.example_M1();
        Word w = DFAGen.StringtoWord("ababababbbb");
        State s = A.run(w);
        System.out.println(A);
        System.out.println(s.getIndex());
    }
    public static void FDFARunTest() {
        FDFA A = DFAGen.exmple_FDFA1();
        Word u = DFAGen.StringtoWord("baa");
        Word v = DFAGen.StringtoWord("bbaaaaaa");
        WordPair P = BasicOperations.normalize(u,v,A.leadingDFA);
        System.out.println(A);
//        A.run(P);
        System.out.println(BasicOperations.MembershipCheck(A,P));
    }
    public static void EmptyTest(){
        FDFA A = DFAGen.exmple_FDFA1();
        System.out.println(BasicOperations.isEmpty(A));

    }
    public static void main(String[] argv){
        EmptyTest();
    }


}
