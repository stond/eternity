package cz.fourtwoone.eternity.component;

import cz.fourtwoone.WicketApplication;
import cz.fourtwoone.eternity.model.Board;
import cz.fourtwoone.eternity.model.OrientedPiece;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.GridView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.resource.PackageResourceReference;

import java.awt.*;
import java.util.Iterator;
import java.util.function.Consumer;

public class BoardPanel extends GenericPanel<Board> {

	IModel<Point> selectedPlaceModel;

	public BoardPanel(String id, IModel<Board> model, IModel<Point> place) {
		super(id, model);
		selectedPlaceModel = place;
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
				AjaxLink link = new AjaxLink<Point>("boardPieceLink") {
					@Override
					public void onClick(AjaxRequestTarget target) {
						Board board = BoardPanel.this.getModelObject();
						Point place = new Point(item.getIndex() % (board.getBoardSize().x),
								board.getBoardSize().y - 1 - item.getIndex() / board.getBoardSize().x);
						selectedPlaceModel.setObject(place);
						BoardPanel.this.modelChanged();
						BoardPanel.this.send(getPage(), Broadcast.EXACT, new PlaceSelectedEvent(target));
					}
				};
				item.add(link);
				link.add(new org.apache.wicket.markup.html.image.Image("img", getImageRef(item.getModelObject())));
			}

			@Override
			protected void populateEmptyItem(Item<OrientedPiece> item) {
				item.add(new Image("img", getImageRef(OrientedPiece.EMPTY_PIECE)));
			}
		};
		boardView.setRows(getModelObject().getBoardSize().y);
		boardView.setColumns(getModelObject().getBoardSize().x);
		boardPanel.add(boardView);
		boardPanel.setOutputMarkupId(true);
		add(boardPanel);

	}

	private PackageResourceReference getImageRef(OrientedPiece piece) {
		return ((WicketApplication) getApplication()).getImageProvider().getImageRef(256, piece);
	}
}
