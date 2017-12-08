package cn.ac.ios;

import cn.ac.ios.util.UtilMachine;
import cn.ac.ios.words.Alphabet;
import dk.brics.automaton.*;
import cn.ac.ios.machine.dfa.DFA;
public class WTFTest {
    public static void main(String[] argv){
        Alphabet input = new Alphabet(String.class);
        input.addLetter("a");
        input.addLetter("b");
        input.addLetter("c");
        RegExp r = new RegExp("(a|aab)+");
        Automaton a = r.toAutomaton();
        a.minimize();
        System.out.println(a.toDot());
//        String s = "abcccdc";
//        System.out.println("Match: " + a.run(s)); // prints: true

        final int numCase = 0;
        for (int i = 0; i < numCase; i++) {
            System.out.println(i);
            DFA A = (DFA) DFAGen.getRandomAutomaton(input,3);
            Automaton dkAut = UtilMachine.dfaToDkAutomaton(A);
            dkAut.minimize();
            Automaton positiveClosure = dkAut.clone();
            positiveClosure = positiveClosure.repeat(1);
            positiveClosure.minimize();
            if(positiveClosure.getAcceptStates().size()>1){
                System.out.println(dkAut.toDot());
                System.out.println("//--");
                System.out.println(positiveClosure.toDot());
            }
            assert positiveClosure.getAcceptStates().size() <= 1;
        }

    }
}
