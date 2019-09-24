package cz.fourtwoone.eternity.model;

import cz.fourtwoone.eternity.model.Orientation;
import cz.fourtwoone.eternity.model.Piece;
import org.junit.Test;

import static org.junit.Assert.*;

public class PieceTest {

    @Test
    public void getColor() {
        int id = 3;
        int colorN = 0;
        int colorE = 1;
        int colorS = 2;
        int colorW = 3;
        Piece p = new Piece(id, colorN, colorE, colorS, colorW);

        assertEquals(id, p.getId());
        assertEquals(colorN, p.getColor(Orientation.N));
        assertEquals(colorE, p.getColor(Orientation.E));
        assertEquals(colorS, p.getColor(Orientation.S));
        assertEquals(colorW, p.getColor(Orientation.W));
    }
}