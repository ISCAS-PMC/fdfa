package cn.ac.ios.machine.fdfa;

import cn.ac.ios.machine.Machine;
import cn.ac.ios.machine.State;

public class ProudctState implements State {
    @Override
    public Machine getMachine() {
        return null;
    }

    @Override
    public int getIndex() {
        return 0;
    }

    @Override
    public int getSuccessor(int letter) {
        return 0;
    }

    @Override
    public int getOutput(int letter) {
        return 0;
    }

    @Override
    public void addTransition(int letter, int state) {

    }

    @Override
    public void addTransition(int letter, int state, int out) {

    }

    @Override
    public void addOutput(int letter, int output) {

    }
}
