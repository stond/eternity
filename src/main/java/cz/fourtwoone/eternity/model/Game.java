package cz.fourtwoone.eternity.model;

import cz.fourtwoone.HomePage;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Game implements Serializable {

	private Point boardSize;
	private Piece[] pieces;
	private Set<Piece> freePieces;
	private Board board;

	public Game() {
		this.boardSize = new Point(6,6);
		this.pieces = loadPieces(36);
		this.freePieces = new HashSet<>();
		this.freePieces.addAll(List.of(pieces));
		this.board = new Board(boardSize);
	}

	public Game(Point boardSize, Piece[] pieces) {

		this.boardSize = boardSize;
		this.pieces = pieces;
		this.freePieces = new HashSet<>();
		this.freePieces.addAll(List.of(pieces));

		this.board = new Board(boardSize);
	}

	public static Game createSmallGame() {
		Point boardSize = new Point(3, 3);
		Piece[] pieces = new Piece[]{
				new Piece(1, 0, 0, 1, 1),
				new Piece(2, 0, 0, 2, 2),
				new Piece(3, 0, 0, 3, 3),
				new Piece(4, 0, 0, 1, 3),
				new Piece(5, 0, 2, 1, 1),
				new Piece(6, 0, 3, 2, 2),
				new Piece(7, 0, 3, 2, 3),
				new Piece(8, 0, 1, 2, 1),
				new Piece(9, 1, 2, 2, 2),
		};
//        generatePieces(pieces);
		return new Game(boardSize, pieces);
	}

	public static Game createGame(int pieceCount) {
		Point boardSize;
		switch (pieceCount) {
			case 9:
				boardSize = new Point(3, 3);
				break;
			case 36:
				boardSize = new Point(6, 6);
				break;
			case 256:
				boardSize = new Point(16, 16);
				break;
			default:
				return null;
		}
		Piece[] pieces = loadPieces(pieceCount);
		return new Game(boardSize, pieces);
	}

	public static Piece[] loadPieces(int pieceCount) {
		File file = new File("" + pieceCount + ".csv");
		List<Piece> pieces = new LinkedList<>();

		ClassLoader classLoader = HomePage.class.getClassLoader();

		try (InputStream inputStream = classLoader.getResourceAsStream("" + pieceCount + "/" + pieceCount + ".xml")) {
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

			String st;
			Integer line = 0;
			while ((st = br.readLine()) != null) {
				line++;
				String[] strParts = st.split(",");
				if (strParts.length == 5) {
					int[] parts = Arrays.stream(strParts).mapToInt(Integer::valueOf).toArray();
					pieces.add(new Piece(parts[0], parts[1], parts[2], parts[3], parts[4]));
				}
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return pieces.toArray(new Piece[]{});
	}


	public static void main(String[] args) {
		Game g = createSmallGame();
	}

	public static void generatePieces(Piece[] pieces) {
		try {
			PieceGen.generatePieces(pieces);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	public Point getBoardSize() {
		return boardSize;
	}

	public Piece[] getPieces() {
		return pieces;
	}

	public Board getBoard() {
		return board;
	}

	public boolean remove(Point place) {
		if (this.board.getBoardPiece(place) != null) {
			return this.freePieces.add(this.board.remove(place));
		}
		return true;
	}

	public boolean place(Piece piece, Orientation orientation, Point place) {
		if (!freePieces.contains(piece)) {
			return false;
		}
		if (this.board.place(new OrientedPiece(piece, orientation), place)) {
			freePieces.remove(piece);
			return true;
		} else {
			return false;
		}
	}

	public List<Piece> getFreePieces() {
		return new LinkedList<>(this.freePieces);
	}

	public List<OrientedPiece> getPossiblePieces(Point place) {
		List<OrientedPiece> pieces = new LinkedList<>();
		if (place == null) {
			return pieces;
		}
		this.getFreePieces().stream().forEach(p -> {
			this.addToLiIfPossible(p, Orientation.N, place, pieces);
			this.addToLiIfPossible(p, Orientation.E, place, pieces);
			this.addToLiIfPossible(p, Orientation.S, place, pieces);
			this.addToLiIfPossible(p, Orientation.W, place, pieces);
		});
		return pieces;
	}

	private void addToLiIfPossible(Piece piece, Orientation orientation, Point place, List<OrientedPiece> possiblePieces) {
		OrientedPiece op = new OrientedPiece(piece, orientation);
		if (this.board.isPossiblePlacePiece(op, place)) {
			possiblePieces.add(op);
		}
	}
}
