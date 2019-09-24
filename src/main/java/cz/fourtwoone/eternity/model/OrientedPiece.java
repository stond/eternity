package cz.fourtwoone.eternity.model;

import java.io.Serializable;

public class OrientedPiece implements Serializable {

    public static final OrientedPiece EMPTY_PIECE = new OrientedPiece(Piece.EMPTY_PIECE, Orientation.N);

    private Piece piece;
    private Orientation orientation;

    public OrientedPiece(final Piece piece, final Orientation orientation) {
        this.piece = piece;
        this.orientation = orientation;
    }

    public Piece getPiece() {
        return piece;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    int getOrientedColor(final Orientation colorOrientation) {
        int colorIndex = colorOrientation.getIntOrientation() - orientation.getIntOrientation();
        if (colorIndex < 0) {
            colorIndex += 4;
        }
        return this.piece.colors[colorIndex];
    }

    public static Orientation sumOrientation(Orientation boardOrientation, Orientation pieceOrientation) {
        return Orientation.valueOf(boardOrientation.getIntOrientation() + pieceOrientation.getIntOrientation());
    }

    public int getId() {
        return piece.getId();
    }
}
