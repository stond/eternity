package cz.fourtwoone.eternity.component;

import cz.fourtwoone.eternity.model.OrientedPiece;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.event.IEventSource;

public class PieceClickEvent implements IEvent {

	final OrientedPiece piece;
	final AjaxRequestTarget target;

	public PieceClickEvent(OrientedPiece piece, AjaxRequestTarget target) {
		this.piece = piece;
		this.target = target;
	}

	public AjaxRequestTarget getTarget() {
		return target;
	}

	public OrientedPiece getPiece() {
		return piece;
	}

	@Override
	public void stop() {

	}

	@Override
	public void dontBroadcastDeeper() {

	}

	@Override
	public Broadcast getType() {
		return null;
	}

	@Override
	public IEventSource getSource() {
		return null;
	}

	@Override
	public Object getPayload() {
		return null;
	}
}
