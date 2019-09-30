package cz.fourtwoone.eternity.component;

import org.apache.wicket.ajax.AjaxRequestTarget;

import java.awt.*;
import java.io.Serializable;

public class PieceSelectAction implements Serializable {

	public final Point place;
//	public final AjaxRequestTarget target;

	public PieceSelectAction(Point place, AjaxRequestTarget target) {
		this.place = place;
//		this.target = target;
	}
}
