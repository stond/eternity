package cz.fourtwoone.eternity.component;

import cz.fourtwoone.HomePage;
import cz.fourtwoone.eternity.model.Board;
import cz.fourtwoone.eternity.model.OrientedPiece;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.GridView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import java.awt.*;
import java.util.Iterator;
import java.util.function.Consumer;

public class BoardPanel extends GenericPanel<Board> {

	IModel<Point> selectedPlaceModel;

	Consumer<PieceSelectAction> onPieceClick;

	public BoardPanel(String id, IModel<Board> model, IModel<Point> place) {
		super(id, model);
		selectedPlaceModel = place;
	}

	public void setOnPieceClick(Consumer<PieceSelectAction> onPieceClick) {
		this.onPieceClick = onPieceClick;
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		WebMarkupContainer boardPanel = new WebMarkupContainer("boardView");


		GridView<OrientedPiece> boardView = new GridView<>("boardRows", new IDataProvider<OrientedPiece>() {
			@Override
			public Iterator<OrientedPiece> iterator(long l, long l1) {
				return getModelObject().getBoardPieceList().iterator();
			}

			@Override
			public long size() {
				return getModelObject().getBoardPieceList().size();
			}

			@Override
			public IModel<OrientedPiece> model(OrientedPiece o) {
				return Model.of(o);
			}
		}) {
			@Override
			protected void populateItem(Item<OrientedPiece> item) {
				final OrientedPiece piece = item.getModelObject();
				int id = piece.getId();
				int orientation = piece.getOrientation().getIntOrientation();
				String imgFile = id > 0 ? String.format("piece_%03d_%d.jpg", id, orientation) : "piece_empty.jpg";
				AjaxLink link = new AjaxLink<Point>("boardPieceLink") {
					@Override
					public void onClick(AjaxRequestTarget target) {
						if (BoardPanel.this.onPieceClick != null) {
							Board board = BoardPanel.this.getModelObject();
							Point place = new Point(item.getIndex() % (board.getBoardSize().x),
									board.getBoardSize().y - 1 - item.getIndex() / board.getBoardSize().x);
							BoardPanel.this.onPieceClick.accept(new PieceSelectAction(place, target));
						}
					}
				};
				item.add(link);
				link.add(new org.apache.wicket.markup.html.image.Image("img", HomePage.getImage(imgFile)));
			}

			@Override
			protected void populateEmptyItem(Item<OrientedPiece> item) {
				item.add(new Image("img", HomePage.getImage("piece_empty.jpg")));
			}
		};
		boardView.setRows(getModelObject().getBoardSize().y);
		boardView.setColumns(getModelObject().getBoardSize().x);
		boardPanel.add(boardView);
		boardPanel.setOutputMarkupId(true);
		add(boardPanel);

	}
}
