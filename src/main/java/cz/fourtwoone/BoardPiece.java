package cz.fourtwoone;

import cz.fourtwoone.eternity.model.OrientedPiece;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;

import java.util.Iterator;

public class BoardPiece implements IDataProvider<OrientedPiece> {
    @Override
    public Iterator<? extends OrientedPiece> iterator(long l, long l1) {
        return null;
    }

    @Override
    public long size() {
        return 0;
    }

    @Override
    public IModel<OrientedPiece> model(OrientedPiece orientedPiece) {
        return null;
    }
}
