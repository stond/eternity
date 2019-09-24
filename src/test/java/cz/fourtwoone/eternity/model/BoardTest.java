package cz.fourtwoone.eternity.model;

import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

public class BoardTest {
    Point boardSize;
    Board board;

    @Before
    public void setUp() throws Exception {
        boardSize = new Point(3, 3);
        board = new Board(boardSize);
    }

    @Test
    public void placeSuccess() {
        Piece cornerPiece = new Piece(1, 1, 1, 0, 0);
        Point place = new Point(0, 0);
        OrientedPiece orientedPiece = new OrientedPiece(cornerPiece, Orientation.N);
        boolean placed = board.place(orientedPiece, place);
        assertTrue(placed);
        OrientedPiece placedPiece = board.getBoardPiece(place);
        assertNotNull(placedPiece);
        assertEquals(orientedPiece, placedPiece);
    }

    @Test
    public void placeFailed() {
        Piece cornerPiece = new Piece(1, 1, 1, 0, 0);
        Point place = new Point(0, 0);
        OrientedPiece orientedPiece = new OrientedPiece(cornerPiece, Orientation.E);
        assertFalse(board.place(orientedPiece, place));
        OrientedPiece placedPiece = board.getBoardPiece(place);
        assertNotNull(placedPiece);
        assertEquals(OrientedPiece.EMPTY_PIECE, placedPiece);
    }

    @Test
    public void isPossiblePlacePiece() {
        Piece cornerPiece = new Piece(1, 1, 1, 0, 0);
        OrientedPiece opN = new OrientedPiece(cornerPiece, Orientation.N);
        OrientedPiece opE = new OrientedPiece(cornerPiece, Orientation.E);
        OrientedPiece opS = new OrientedPiece(cornerPiece, Orientation.S);
        OrientedPiece opW = new OrientedPiece(cornerPiece, Orientation.W);
        Point leftBottomCorner = new Point(0, 0);
        assertTrue(board.isPossiblePlacePiece(opN, leftBottomCorner));
        assertFalse(board.isPossiblePlacePiece(opE, leftBottomCorner));
        assertFalse(board.isPossiblePlacePiece(opS, leftBottomCorner));
        assertFalse(board.isPossiblePlacePiece(opW, leftBottomCorner));

        Point leftTopCorner = new Point(0, boardSize.y - 1);
        assertFalse(board.isPossiblePlacePiece(opN, leftTopCorner));
        assertTrue(board.isPossiblePlacePiece(opE, leftTopCorner));
        assertFalse(board.isPossiblePlacePiece(opS, leftTopCorner));
        assertFalse(board.isPossiblePlacePiece(opW, leftTopCorner));

        Point rightTopCorner = new Point(boardSize.x - 1, boardSize.y - 1);
        assertFalse(board.isPossiblePlacePiece(opN, rightTopCorner));
        assertFalse(board.isPossiblePlacePiece(opE, rightTopCorner));
        assertTrue(board.isPossiblePlacePiece(opS, rightTopCorner));
        assertFalse(board.isPossiblePlacePiece(opW, rightTopCorner));

        Point rightBottomCorner = new Point(boardSize.x - 1, 0);
        assertFalse(board.isPossiblePlacePiece(opN, rightBottomCorner));
        assertFalse(board.isPossiblePlacePiece(opE, rightBottomCorner));
        assertFalse(board.isPossiblePlacePiece(opS, rightBottomCorner));
        assertTrue(board.isPossiblePlacePiece(opW, rightBottomCorner));

        Point leftMiddle = new Point(0, 1);
        assertTrue(board.isPossiblePlacePiece(opN, leftMiddle));
        assertTrue(board.isPossiblePlacePiece(opE, leftMiddle));
        assertFalse(board.isPossiblePlacePiece(opS, leftMiddle));
        assertFalse(board.isPossiblePlacePiece(opW, leftMiddle));

        Point topMiddle = new Point( 1, boardSize.y - 1);
        assertFalse(board.isPossiblePlacePiece(opN, topMiddle));
        assertTrue(board.isPossiblePlacePiece(opE, topMiddle));
        assertTrue(board.isPossiblePlacePiece(opS, topMiddle));
        assertFalse(board.isPossiblePlacePiece(opW, topMiddle));

        Point rightMiddle = new Point(boardSize.x - 1, 1);
        assertFalse(board.isPossiblePlacePiece(opN, rightMiddle));
        assertFalse(board.isPossiblePlacePiece(opE, rightMiddle));
        assertTrue(board.isPossiblePlacePiece(opS, rightMiddle));
        assertTrue(board.isPossiblePlacePiece(opW, rightMiddle));

        Point bottomMiddle = new Point(1, 0);
        assertTrue(board.isPossiblePlacePiece(opN, bottomMiddle));
        assertFalse(board.isPossiblePlacePiece(opE, bottomMiddle));
        assertFalse(board.isPossiblePlacePiece(opS, bottomMiddle));
        assertTrue(board.isPossiblePlacePiece(opW, bottomMiddle));

        Point center = new Point(1, 1);
        assertTrue(board.isPossiblePlacePiece(opN, center));
        assertTrue(board.isPossiblePlacePiece(opE, center));
        assertTrue(board.isPossiblePlacePiece(opS, center));
        assertTrue(board.isPossiblePlacePiece(opW, center));

    }

    @Test
    public void getBoardPiece() {
        // empty board
        for (int x = 0; x < boardSize.x; x++) {
            for (int y = 0; y < boardSize.y; y++) {
                assertEquals(OrientedPiece.EMPTY_PIECE, board.getBoardPiece(new Point(x, y)));
            }
        }
    }
}