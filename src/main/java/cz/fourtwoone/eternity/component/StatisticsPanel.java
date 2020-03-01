package cz.fourtwoone.eternity.component;

import cz.fourtwoone.eternity.model.Orientation;
import cz.fourtwoone.eternity.model.Piece;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.model.util.ListModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class StatisticsPanel extends GenericPanel<List<Piece>> {

	public StatisticsPanel(String id, IModel<List<Piece>> model) {
		super(id, model);
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		add(new Label("histogramLabel", Model.of("Histogram")));

		ListView listview = new ListView<ColorFrequency>("histogramItem", new ListModel(new ArrayList(getHistogram().values()))) {
			protected void populateItem(ListItem<ColorFrequency> item) {
				item.add(new Label("histColor", Model.of(item.getModelObject().id)));
				item.add(new Label("histNumbers", Model.of(item.getModelObject().colorCount + "/" + item.getModelObject().pieceCount)));
			}
		};
		add(listview);
	}

	Map<Integer, ColorFrequency> getHistogram() {
		Map<Integer, ColorFrequency> histogram = new HashMap<>();
		getModelObject().forEach(piece -> {
			List<Integer> colors = new ArrayList<>();
			Set<Integer> pieceColors = new HashSet<>();
			Arrays.stream(Orientation.values()).forEach(orientation -> {
				colors.add(piece.getColor(orientation));
				pieceColors.add(piece.getColor(orientation));
			});
			colors.forEach(color -> {
				if (histogram.containsKey(color)) {
					histogram.put(color, histogram.get(color).addColor());
				} else {
					histogram.put(color, new ColorFrequency(color, 1, 0));
				}
			});
			pieceColors.forEach(color -> {
				histogram.put(color, histogram.get(color).addPieceColor());
			});
		});
		return histogram;
	}

	private class ColorFrequency {
		int id;
		int colorCount;
		int pieceCount;

		public ColorFrequency(int id, int colorCount, int pieceCount) {
			this.id = id;
			this.colorCount = colorCount;
			this.pieceCount = pieceCount;
		}


		public ColorFrequency addColor() {
			colorCount++;
			return this;
		}

		public ColorFrequency addPieceColor() {
			pieceCount++;
			return this;
		}
	}
}
