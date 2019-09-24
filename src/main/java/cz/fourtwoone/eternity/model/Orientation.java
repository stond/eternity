package cz.fourtwoone.eternity.model;

import java.io.Serializable;

public enum Orientation implements Serializable {

    N(0),
    E(1),
    S(2),
    W(3);

    private int intOrientation;

    public int getIntOrientation() {
        return intOrientation;
    }

    Orientation(int intOrientation) {
        this.intOrientation = intOrientation;
    }

    public static Orientation valueOf(int intOrientation) {
        int baseOrientation = intOrientation % 4;
        switch (baseOrientation) {
            case 0: return N;
            case 1: return E;
            case 2: return S;
            case 3: return W;
            default: throw new IllegalArgumentException("intOrientation");
        }
    }
}
