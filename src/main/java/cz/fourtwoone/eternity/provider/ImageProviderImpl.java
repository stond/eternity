package cz.fourtwoone.eternity.provider;

import cz.fourtwoone.WicketApplication;
import cz.fourtwoone.eternity.model.OrientedPiece;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.springframework.stereotype.Service;

@Service
public class ImageProviderImpl implements ImageProvider {

	public PackageResourceReference getImageRef(int pieceCount, OrientedPiece piece) {
		int id = piece.getId();
		int orientation = piece.getOrientation().getIntOrientation();
		String imgFile = id > 0 ? String.format("piece_%03d_%d.jpg", id, orientation) : "piece_empty.jpg";

		return new PackageResourceReference(WicketApplication.class, pieceCount + "/" + imgFile);
	}

}
