package cn.ac.ios;

import java.util.ArrayList;
import java.util.Random;

import cn.ac.ios.machine.fdfa.*;
import cn.ac.ios.machine.Machine;
import cn.ac.ios.machine.State;
import cn.ac.ios.machine.dfa.DFA;
import cn.ac.ios.words.Alphabet;
import cn.ac.ios.words.*;

public class DFAGen {

    static int letter_a;
    static int letter_b;
    static public Alphabet alphabet = DFAGen.twoLetterAlaphabet();
    static public Alphabet twoLetterAlaphabet() {
        Alphabet input = new Alphabet(String.class);
        input.addLetter("a");
        input.addLetter("b");
        letter_a = input.getAPs().indexOf("a");
        letter_b = input.getAPs().indexOf("b");
        return input;
    }


    public static DFA exampleState(int num){
        assert num >= 1;
        Alphabet alphabet = DFAGen.twoLetterAlaphabet();

        DFA A = new DFA(alphabet.getAPs());
        State[] states = new State[3];
        for (int i = 0; i < num; i++) {
            states[i] = A.createState();
        }

        State lambda = states[0];
        State dead   = states[num-1];

        for (int i = 0; i < num; i++) {
            for (int letter = 0; letter < 2; letter++) {
                states[i].addTransition(letter,dead.getIndex());
            }
        }

        A.setInitial(lambda.getIndex());
        return A;
    }
    public static DFA example_M1() {

        DFA A = DFAGen.exampleState(2);
        State lambda = A.getState(0);
        State single = A.getState(1);

        lambda.addTransition(letter_a,lambda.getIndex());
        lambda.addTransition(letter_b,single.getIndex());
        single.addTransition(letter_a,lambda.getIndex());
        single.addTransition(letter_b,single.getIndex());
        return  A;
    }

    public static DFA exampleProgess(int letter) {

        DFA A = DFAGen.exampleState(2);
        State lambda = A.getState(0);

        lambda.addTransition(letter,lambda.getIndex());
        A.getAcceptance().setFinal(lambda.getIndex());

        return A;
    }


    public static FDFA exmple_FDFA1(){

        DFA leading = example_M1();
        DFA progesslambda = exampleProgess(letter_a);
        DFA progessSingle = exampleProgess(letter_b);

        ArrayList<DFA> progess = new ArrayList<>();
        progess.add(progesslambda);
        progess.add(progessSingle);
        FDFA U = new FDFA(leading,progess);

        return U;
    };

    public static DFA example_M2() {

        DFA A = DFAGen.exampleState(1);
        return  A;
    }
    public static FDFA exmple_FDFA2(){

        DFA leading = example_M2();
        DFA progessdead = exampleProgess(letter_a);

        ArrayList<DFA> progess = new ArrayList<>();
        progess.add(progessdead);
        FDFA U = new FDFA(leading,progess);

        return U;
    };

    public static Word StringtoWord(String s){
        int[] data = new int[s.length()];
        for (int i = 0; i < s.length(); i++) {
            String t = s.substring(i,i+1);
            data[i] = alphabet.getAPs().indexOf(t);
        }
        return alphabet.getArrayWord(data);
    }

    private DFAGen() {

    }

    public static Machine getRandomAutomaton(Alphabet ap, int numState) {

        Machine result = new DFA(ap.getAPs());

        Random r = new Random(System.currentTimeMillis());

        for(int i = 0; i < numState; i ++) {
            result.createState();
        }

        // add self loops for those transitions
        for(int i = 0; i < numState; i ++) {
            State state = result.getState(i);
            for(int k=0 ; k < ap.getAPs().size(); k++){
                state.addTransition(k, i);
            }
        }

        result.setInitial(0);

        // final states
        int numF = r.nextInt(numState-1);
        boolean hasF = false;
        numF = numF > 0 ? numF : 1;
        for(int n = 0; n < numF ; n ++) {
            int f = r.nextInt(numF);
            if(f != 0) {
                result.getAcceptance().setFinal(f);
                hasF = true;
            }
        }

        if(! hasF) {
            result.getAcceptance().setFinal(numF);
        }

        int numTrans = r.nextInt(numState * ap.getAPs().size());

        // transitions
        for(int k=0 ; k < ap.getAPs().size(); k++){
            for(int n = 0; n < numTrans; n++ ){
                int i=r.nextInt(numState);
                int j=r.nextInt(numState);
                result.getState(i).addTransition(k, j);
            }
        }

        return result;
    }

}
