package cn.ac.ios.machine.fdfa;

import cn.ac.ios.machine.Machine;
import cn.ac.ios.words.Word;

public class WordPair {
    private Word u;
    private Word v;
    private int i = 0;
    private int j = 0;
    private boolean normalized = false;
    private Machine respectMachine = null;

//    public void normalize(Machine Q) {
//        BasicOperations(this.u,this.v,Q);
//    }


    public WordPair(Word u, Word v, int i, int j, boolean normalized, Machine respectMachine) {
        this.u = u;
        this.v = v;
        this.i = i;
        this.j = j;
        this.normalized = normalized;
        this.respectMachine = respectMachine;
    }

    public Word getU() {
        return u;
    }

    public Word getV() {
        return v;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    public boolean isNormalized() {
        return normalized;
    }

    public Machine getRespectMachine() {
        return respectMachine;
    }

    public void setU(Word u) {
        this.u = u;
    }

    public void setV(Word v) {
        this.v = v;
    }

    public void setI(int i) {
        this.i = i;
    }

    public void setJ(int j) {
        this.j = j;
    }

    public void setNormalized(boolean normalized) {
        this.normalized = normalized;
    }

    public void setRespectMachine(Machine respectMachine) {
        this.respectMachine = respectMachine;
    }
}
