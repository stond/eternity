package cz.fourtwoone.eternity.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class OrientedPieceTest {

    @Test
    public void getOrientedColor() {
        Piece p = new Piece(5,0, 1, 2, 3);
        OrientedPiece op = new OrientedPiece(p, Orientation.N);
        OrientedPiece opCW = new OrientedPiece(p, Orientation.E);
        OrientedPiece op2CW = new OrientedPiece(p, Orientation.S);
        OrientedPiece opCCW = new OrientedPiece(p, Orientation.W);

        // original orientation, expected:
        //  N
        // W E
        //  S
        assertEquals(p.getColor(Orientation.N), op.getOrientedColor(Orientation.N));
        assertEquals(p.getColor(Orientation.E), op.getOrientedColor(Orientation.E));
        assertEquals(p.getColor(Orientation.S), op.getOrientedColor(Orientation.S));
        assertEquals(p.getColor(Orientation.W), op.getOrientedColor(Orientation.W));

        // CW (clock wise) orientation, expected:
        //  W
        // S N
        //  E
        assertEquals(p.getColor(Orientation.W), opCW.getOrientedColor(Orientation.N));
        assertEquals(p.getColor(Orientation.N), opCW.getOrientedColor(Orientation.E));
        assertEquals(p.getColor(Orientation.E), opCW.getOrientedColor(Orientation.S));
        assertEquals(p.getColor(Orientation.S), opCW.getOrientedColor(Orientation.W));

        // 2CW (2x clock wise) orientation, expected:
        //  S
        // E W
        //  N
        assertEquals(p.getColor(Orientation.S), op2CW.getOrientedColor(Orientation.N));
        assertEquals(p.getColor(Orientation.W), op2CW.getOrientedColor(Orientation.E));
        assertEquals(p.getColor(Orientation.N), op2CW.getOrientedColor(Orientation.S));
        assertEquals(p.getColor(Orientation.E), op2CW.getOrientedColor(Orientation.W));

        // CCW (counter-clock wise) orientation, expected:
        //  E
        // N S
        //  W
        assertEquals(p.getColor(Orientation.E), opCCW.getOrientedColor(Orientation.N));
        assertEquals(p.getColor(Orientation.S), opCCW.getOrientedColor(Orientation.E));
        assertEquals(p.getColor(Orientation.W), opCCW.getOrientedColor(Orientation.S));
        assertEquals(p.getColor(Orientation.N), opCCW.getOrientedColor(Orientation.W));


    }
}