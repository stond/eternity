package cz.fourtwoone.eternity.component;

import cz.fourtwoone.HomePage;
import cz.fourtwoone.eternity.model.OrientedPiece;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.GridView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.resource.PackageResourceReference;

import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

public class PossiblePanel extends GenericPanel<List<OrientedPiece>> {

    IDataProvider<OrientedPiece> provider;

    Consumer<PlacePieceAction> onPlacePiece;

    public PossiblePanel(String id, IModel<List<OrientedPiece>> model) {
        super(id, model);
    }

    public void setOnPlacePiece(Consumer<PlacePieceAction> onPlacePiece) {
        this.onPlacePiece = onPlacePiece;
    }

    @Override
    protected void onConfigure() {
        super.onConfigure();
        provider.detach();
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        provider = new IDataProvider<OrientedPiece>() {
            @Override
            public Iterator<? extends OrientedPiece> iterator(long l, long l1) {
                return getModelObject().iterator();
            }

            @Override
            public long size() {
                return getModelObject().size();
            }

            @Override
            public IModel<OrientedPiece> model(OrientedPiece orientedPiece) {
                return Model.of(orientedPiece);
            }
        };
        GridView<OrientedPiece> possibleView = new GridView<OrientedPiece>("possibleRows", provider) {
            @Override
            protected void populateItem(Item<OrientedPiece> item) {
                final OrientedPiece piece = item.getModelObject();
                int id = piece.getId();

                AjaxLink link = new AjaxLink<OrientedPiece>("placeLink") {
                    @Override
                    public void onClick(AjaxRequestTarget target) {
                        PossiblePanel.this.onPlacePiece.accept(new PlacePieceAction(piece, target));
                    }
                };
                item.add(link);
                String imgFile = id > 0 ? String.format("piece_%03d_%d.jpg", id, piece.getOrientation().getIntOrientation()) : "piece_empty.jpg";
                link.add(new Image("possibleImg", HomePage.getImage(imgFile)));
            }

            @Override
            protected void populateEmptyItem(Item<OrientedPiece> item) {
                AjaxLink link = new AjaxLink<OrientedPiece>("placeLink") {
                    @Override
                    public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                    }
                };

                item.add(link);
                link.add(new Image("possibleImg", HomePage.getImage("piece_empty.jpg")));
            }
        };
        possibleView.setRows(9);
        possibleView.setColumns(3);
        possibleView.setOutputMarkupId(true);
        WebMarkupContainer possiblePiecesPanel = new WebMarkupContainer("possiblePieces");
        possiblePiecesPanel.setOutputMarkupId(true);
        possiblePiecesPanel.add(possibleView);
        Label label = new Label("possibleLabel", provider.size());
        possiblePiecesPanel.add(label);
        add(possiblePiecesPanel);
    }

    public class PlacePieceAction {
        public final OrientedPiece piece;
        public final AjaxRequestTarget target;

        public PlacePieceAction(OrientedPiece piece, AjaxRequestTarget target) {
            this.piece = piece;
            this.target = target;
        }
    }
}