package cz.fourtwoone.eternity.model;

import java.awt.*;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class Board implements Serializable {

	private final Point boardSize;
	private final OrientedPiece[][] placed;

	public Board(Point size) {
		this.boardSize = size;
		this.placed = new OrientedPiece[size.x][size.y];

		for (int x = 0; x < size.x; x++) {
			for (int y = 0; y < size.y; y++) {
				this.placed[x][y] = OrientedPiece.EMPTY_PIECE;
			}
		}
	}

	public Point getBoardSize() {
		return boardSize;
	}

	public List<OrientedPiece> getBoardPieceList() {
		List<OrientedPiece> list = new LinkedList<>();
		for (int y = placed[0].length - 1; y >= 0; y--) {
			for (int x = 0; x < placed.length; x++) {
				list.add(placed[x][y]);
			}
		}
		return list;
	}

	public boolean place(OrientedPiece orientedPiece, Point place) {
		if (isPossiblePlacePiece(orientedPiece, place)) {
			placed[place.x][place.y] = orientedPiece;
			return true;
		}
		return false;
	}

	boolean isPossiblePlacePiece(OrientedPiece orientedPiece, Point place) {
		if (place.x < 0 || place.x >= placed.length ||
				place.y < 0 || place.y > placed[0].length) {
			// position out of board
			return false;
		}
		// northern check
		Integer northern = getNextBoardColor(Orientation.N, place);
		if (northern != null && northern != orientedPiece.getOrientedColor(Orientation.N)) {
			return false;
		}
		// eastern check
		Integer eastern = getNextBoardColor(Orientation.E, place);
		if (eastern != null && eastern != orientedPiece.getOrientedColor(Orientation.E)) {
			return false;
		}
		// southern check
		Integer southern = getNextBoardColor(Orientation.S, place);
		if (southern != null && southern != orientedPiece.getOrientedColor(Orientation.S)) {
			return false;
		}
		// western check
		Integer western = getNextBoardColor(Orientation.W, place);
		if (western != null && western != orientedPiece.getOrientedColor(Orientation.W)) {
			return false;
		}

		return true;
	}

	OrientedPiece getBoardPiece(Point place) {
		return placed[place.x][place.y];
	}

	private Integer getNextBoardColor(Orientation orientation, Point place) {
		switch (orientation) {
			case N:
				if (place.y == placed[0].length - 1) {
					return 0;
				} else {
					OrientedPiece op = placed[place.x][place.y + 1];
					return op == null || op == OrientedPiece.EMPTY_PIECE ? null : op.getOrientedColor(Orientation.S);
				}
			case E:
				if (place.x == placed.length - 1) {
					return 0;
				} else {
					OrientedPiece op = placed[place.x + 1][place.y];
					return op == null || op == OrientedPiece.EMPTY_PIECE ? null : op.getOrientedColor(Orientation.W);
				}
			case S:
				if (place.y == 0) {
					return 0;
				} else {
					OrientedPiece op = placed[place.x][place.y - 1];
					return op == null || op == OrientedPiece.EMPTY_PIECE ? null : op.getOrientedColor(Orientation.N);
				}
			case W:
				if (place.x == 0) {
					return 0;
				} else {
					OrientedPiece op = placed[place.x - 1][place.y];
					return op == null || op == OrientedPiece.EMPTY_PIECE ? null : op.getOrientedColor(Orientation.E);
				}
			default:
				throw new IllegalStateException("Unknown orientation");
		}
	}

	public Piece remove(Point place) {
		Piece removed = this.placed[place.x][place.y].getPiece();
		this.placed[place.x][place.y] = OrientedPiece.EMPTY_PIECE;
		return removed;
	}
}
