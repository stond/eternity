package cz.fourtwoone.eternity.model;

import java.io.Serializable;

public class Piece implements Serializable {

    public static final Piece EMPTY_PIECE = new Piece(0, -1, -1, -1, -1);

    int id;

    int colors[];

    public Piece(int id, int colorN, int colorE, int colorS, int colorW) {
        this.id = id;
        this.colors = new int[] {colorN, colorE, colorS, colorW};
    }

    public int getId() {
        return id;
    }

    public int getColor(Orientation orientation) {
        return colors[orientation.getIntOrientation()];
    }
}
