package cn.ac.ios.machine;

public enum AcceptanceType {
    DFA,
    NFA,
    FDFA,
    BUECHI,
    RABIN,
    STREET,
    WEIGHT;

    public boolean isDFA() {
        return this == DFA;
    }

    public boolean isNFA() {
        return this == NFA;
    }

    public boolean isFDFA() {
        return this == FDFA;
    }

    public boolean isBuechi() {
        return this == BUECHI;
    }

    public boolean isRabin() {
        return this == RABIN;
    }

    public boolean isStreet() {
        return this == STREET;
    }

    public boolean isWeight() {
        return this == WEIGHT;
    }


    public String toString() {
        if(this == DFA) {
            return "DFA";
        }else if(this == NFA) {
            return "NFA";
        }else if(this == FDFA) {
            return "FDFA";
        }if(this == BUECHI) {
            return "BUECHI";
        }else if(this == RABIN) {
            return "RABIN";
        }else if(this == STREET) {
            return "STREET";
        }else {
            return "WEIGHT";
        }
    }
}
