package cz.fourtwoone;

import cz.fourtwoone.eternity.component.BoardPanel;
import cz.fourtwoone.eternity.component.PieceClickEvent;
import cz.fourtwoone.eternity.component.PlaceSelectedEvent;
import cz.fourtwoone.eternity.component.PossiblePanel;
import cz.fourtwoone.eternity.component.StatisticsPanel;
import cz.fourtwoone.eternity.model.Game;
import cz.fourtwoone.eternity.model.OrientedPiece;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import java.awt.*;
import java.util.List;

public class HomePage extends WebPage {
	private static final long serialVersionUID = 1L;
	private final IModel<Point> selectedPlaceModel;
	private Point selectedPlace = new Point(0, 0);
	private Game game;
	private BoardPanel boardPanel;
	private PossiblePanel possiblePanel;
	private Label selectionLabel;
	private IModel<List<OrientedPiece>> possibleModel;

	public HomePage(final PageParameters parameters) {
		super(parameters);

//		game = getGame().createGame(36);
//		game = getGame().createSmallGame();
/*		getGame().place(getGame().getPieces()[0], Orientation.W, new Point(0, 2));
		getGame().place(getGame().getPieces()[1], Orientation.N, new Point(2, 2));
//		getGame().place(getGame().getPieces()[2], Orientation.E, new Point(2, 0));
//		getGame().place(getGame().getPieces()[3], Orientation.S, new Point(0, 0));
		getGame().place(getGame().getPieces()[4], Orientation.N, new Point(1, 2));
		getGame().place(getGame().getPieces()[5], Orientation.E, new Point(2, 1));
//		getGame().place(getGame().getPieces()[6], Orientation.S, new Point(1, 0));
		getGame().place(getGame().getPieces()[7], Orientation.W, new Point(0, 1));
		getGame().place(getGame().getPieces()[8], Orientation.N, new Point(1, 1));*/

		this.selectedPlaceModel = new LoadableDetachableModel<>() {
			@Override
			protected Point load() {
				return HomePage.this.selectedPlace;
			}
		};
	}

/*	public PackageResourceReference getImageRef(OrientedPiece piece) {
		return imageProvider.getImageRef(36, piece);
	}*/

	@Override
	protected void onInitialize() {
		super.onInitialize();

		add(new Label("version", "Eternity " + getGame().getBoardSize().getX() + ", " + getGame().getBoardSize()));
		selectionLabel = new Label("selection", LoadableDetachableModel.of(() -> {
					Point place = selectedPlaceModel.getObject();
					return place == null ? "selection [-]" : "selection [" + place.x + ", " + place.y + "]";
				}));
		selectionLabel.setOutputMarkupPlaceholderTag(true);
		add(selectionLabel);

		possibleModel = new LoadableDetachableModel<List<OrientedPiece>>() {
			@Override
			protected List<OrientedPiece> load() {
				return getGame().getPossiblePieces(selectedPlaceModel.getObject());
			}
		};
		possiblePanel = createPossiblePanel(possibleModel);

		boardPanel = new BoardPanel("boardPanel", new PropertyModel<>(getGame(), "board"),
				selectedPlaceModel);
		boardPanel.setOutputMarkupId(true);
		add(boardPanel);

		add(new StatisticsPanel("histogramPanel", new PropertyModel<>(getGame(), "freePieces")));

		add(possiblePanel);
	}

	@Override
	public void onEvent(IEvent<?> event) {
		super.onEvent(event);

		if (event.getPayload() instanceof PlaceSelectedEvent) {
			this.getGame().remove(this.selectedPlaceModel.getObject());
			selectionLabel.detach();
			possibleModel.detach();
			AjaxRequestTarget target = ((PlaceSelectedEvent)event.getPayload()).getTarget();
			target.add(this.selectionLabel);
			target.add(this.possiblePanel);
			target.add(this.boardPanel);
		} else if (event.getPayload() instanceof PieceClickEvent) {
			PieceClickEvent myEvent = ((PieceClickEvent)event.getPayload());
			OrientedPiece op = myEvent.getPiece();
			AjaxRequestTarget target = myEvent.getTarget();
			this.getGame().place(op, this.selectedPlaceModel.getObject());
			possibleModel.detach();
			boardPanel.detach();
			target.add(this.possiblePanel);
			target.add(this.boardPanel);
		}

	}

	private PossiblePanel createPossiblePanel(IModel<List<OrientedPiece>> possibleModel) {
		PossiblePanel possiblePanel = new PossiblePanel("possiblePanel", possibleModel);
		possiblePanel.setOutputMarkupId(true);
/*
		possiblePanel.setOnPlacePiece((action) -> {
			OrientedPiece pieceToPlace = action.piece;
			getGame().place(pieceToPlace.getPiece(), pieceToPlace.getOrientation(), selectedPlace);
			action.target.add(this.boardPanel);
			action.target.add(this.possiblePanel);
		});
*/
		return possiblePanel;
	}

	private Game getGame() {
		return ((WicketApplication)getApplication()).game;
	}

/*	private GridView<Piece> createFreePiecesPanel(Game g) {
		GridView<Piece> freeView = new GridView<Piece>("freeRows", new ListDataProvider<>(g.getFreePieces())) {
			@Override
			protected void populateItem(Item<Piece> item) {
				final Piece piece = item.getModelObject();
				int id = piece.getId();
				String imgFile = id == 0 ? "piece_empty.jpg" : String.format("piece_%03d_0.jpg", id);
				item.add(new Image("freeImg", getImageRef(new OrientedPiece(piece, Orientation.N))));
			}

			@Override
			protected void populateEmptyItem(Item<Piece> item) {
				item.add(new Image("freeImg", getImage("piece_empty.jpg")));
			}
		};
		freeView.setRows(9);
		freeView.setColumns(1);
		return freeView;
	}*/



}
