package cz.fourtwoone.eternity.provider;

import cz.fourtwoone.eternity.model.OrientedPiece;
import org.apache.wicket.request.resource.PackageResourceReference;

public interface ImageProvider {

	PackageResourceReference getImageRef(int pieceCount, OrientedPiece piece);
}
