package cz.fourtwoone;

import cz.fourtwoone.eternity.component.BoardPanel;
import cz.fourtwoone.eternity.component.PossiblePanel;
import cz.fourtwoone.eternity.model.Game;
import cz.fourtwoone.eternity.model.OrientedPiece;
import cz.fourtwoone.eternity.model.Piece;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.GridView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.PackageResourceReference;

import java.awt.*;
import java.util.List;

public class HomePage extends WebPage {
	private static final long serialVersionUID = 1L;
	private final IModel<Point> selectedPlaceModel;
	private Point selectedPlace = new Point(0, 0);
	private Game game;
	private BoardPanel boardPanel;
	private PossiblePanel possiblePanel;

	public HomePage(final PageParameters parameters) {
		super(parameters);

		game = Game.createGame(36);
//		game = Game.createSmallGame();
/*		game.place(game.getPieces()[0], Orientation.W, new Point(0, 2));
		game.place(game.getPieces()[1], Orientation.N, new Point(2, 2));
//		game.place(game.getPieces()[2], Orientation.E, new Point(2, 0));
//		game.place(game.getPieces()[3], Orientation.S, new Point(0, 0));
		game.place(game.getPieces()[4], Orientation.N, new Point(1, 2));
		game.place(game.getPieces()[5], Orientation.E, new Point(2, 1));
//		game.place(game.getPieces()[6], Orientation.S, new Point(1, 0));
		game.place(game.getPieces()[7], Orientation.W, new Point(0, 1));
		game.place(game.getPieces()[8], Orientation.N, new Point(1, 1));*/

		this.selectedPlaceModel = new LoadableDetachableModel<>() {
			@Override
			protected Point load() {
				return HomePage.this.selectedPlace;
			}
		};
	}

	public static PackageResourceReference getImage(String imgFile) {
		return new PackageResourceReference(HomePage.class, "36/" + imgFile);
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		add(new Label("version", "Eternity " + game.getBoardSize().getX() + ", " + game.getBoardSize()));
		Label selectionLabel = new Label("selection", LoadableDetachableModel.of(() ->
				selectedPlace == null ? "selection [-]" : "selection [" + selectedPlace.x + ", " + selectedPlace.y + "]"));
		selectionLabel.setOutputMarkupId(true);
		add(selectionLabel);

		IModel<List<OrientedPiece>> possibleModel = new LoadableDetachableModel<List<OrientedPiece>>() {
			@Override
			protected List<OrientedPiece> load() {
				return game.getPossiblePieces(selectedPlace);
			}
		};
		possiblePanel = createPossiblePanel(possibleModel);


		boardPanel = new BoardPanel("boardPanel", new PropertyModel<>(game, "board"), Model.of(selectedPlace));
		boardPanel.setOutputMarkupId(true);
		boardPanel.setOnPieceClick((action) -> {
			this.selectedPlace = action.place;
			this.game.remove(this.selectedPlace);
			this.selectedPlaceModel.detach();
			selectionLabel.detach();
			possibleModel.detach();
			action.target.add(selectionLabel);
			action.target.add(possiblePanel);
			action.target.add(boardPanel);
		});
		add(boardPanel);

		add(possiblePanel);

		GridView<Piece> freeView = createFreePiecesPanel(game);
		add(freeView);

		add(possiblePanel);
	}

	private PossiblePanel createPossiblePanel(IModel<List<OrientedPiece>> possibleModel) {
		PossiblePanel possiblePanel = new PossiblePanel("possiblePanel", possibleModel);
		possiblePanel.setOutputMarkupId(true);
		possiblePanel.setOnPlacePiece((action) -> {
			OrientedPiece pieceToPlace = action.piece;
			game.place(pieceToPlace.getPiece(), pieceToPlace.getOrientation(), selectedPlace);
			action.target.add(this.boardPanel);
			action.target.add(this.possiblePanel);
		});
		return possiblePanel;
	}

	@Override
	protected void onConfigure() {
		super.onConfigure();
	}

	private GridView<Piece> createFreePiecesPanel(Game g) {
		GridView<Piece> freeView = new GridView<Piece>("freeRows", new ListDataProvider<>(g.getFreePieces())) {
			@Override
			protected void populateItem(Item<Piece> item) {
				final Piece piece = item.getModelObject();
				int id = piece.getId();
				String imgFile = id == 0 ? "piece_empty.jpg" : String.format("piece_%03d_0.jpg", id);
				item.add(new Image("freeImg", getImage(imgFile)));
			}

			@Override
			protected void populateEmptyItem(Item<Piece> item) {
				item.add(new Image("freeImg", getImage("piece_empty.jpg")));
			}
		};
		freeView.setRows(9);
		freeView.setColumns(1);
		return freeView;
	}

}
