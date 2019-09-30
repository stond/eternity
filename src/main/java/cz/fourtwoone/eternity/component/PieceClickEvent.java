package cz.fourtwoone.eternity.component;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.event.IEventSource;

public class PieceClickEvent implements IEvent {

	final AjaxRequestTarget target;

	public PieceClickEvent(AjaxRequestTarget target) {
		this.target = target;
	}

	public AjaxRequestTarget getTarget() {
		return target;
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
