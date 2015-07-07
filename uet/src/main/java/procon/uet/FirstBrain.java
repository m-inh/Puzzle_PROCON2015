package procon.uet;

import java.util.ArrayList;
import java.util.Random;

public class FirstBrain implements Brain {
	public Brain.Place bestPlace(TargetArea area, SlatePiece piece) {
		int maxMark = 0;
		int currentMark = 0;
		ArrayList<SlatePiece> pieceArr = new ArrayList<SlatePiece>();
		SlatePiece tempPiece = piece;

		// Xoay 90 (tat ca trang thai xoay cua 1 manh)
		for (int k = 0; k < 4; k++) {
			for (int i = -tempPiece.getMinX(); i < CommonVL.SIZE_TARGET_AREA - tempPiece.getMaxX(); i++) {
				for (int j = -tempPiece.getMinY(); j < CommonVL.SIZE_TARGET_AREA - tempPiece.getMaxY(); j++) {
					if (area.place(tempPiece, i, j) == TargetArea.PLACE_OK) {
						area.undo();

						currentMark = ratePiece(area, piece, i, j);

						if (maxMark == currentMark) {
							pieceArr.add(tempPiece);
						}
						if (maxMark < currentMark) {
							pieceArr = new ArrayList<SlatePiece>();
							pieceArr.add(tempPiece);
							maxMark = currentMark;
						}
					} else
						area.commit();
				}
			}
			tempPiece = tempPiece.fastRotation();
		}

		// Flip the piece
		tempPiece = piece.fastFlipOver();

		for (int k = 0; k < 4; k++) {
			for (int i = -tempPiece.getMinX(); i < CommonVL.SIZE_TARGET_AREA - tempPiece.getMaxX(); i++) {
				for (int j = -tempPiece.getMinY(); j < CommonVL.SIZE_TARGET_AREA - tempPiece.getMaxY(); j++) {
					if (area.place(tempPiece, i, j) == TargetArea.PLACE_OK) {
						area.undo();

						currentMark = ratePiece(area, piece, i, j);

						if (maxMark == currentMark) {
							pieceArr.add(tempPiece);
						}
						if (maxMark < currentMark) {
							pieceArr = new ArrayList<SlatePiece>();
							pieceArr.add(tempPiece);
							maxMark = currentMark;
						}
					} else
						area.commit();
				}
			}
			tempPiece = tempPiece.fastRotation();
		}

		SlatePiece bestPiece = (SlatePiece) randomChoosePiece(pieceArr);

		if (bestPiece != null) {
			return new Brain.Place(bestPiece.getReferenceCell().getX(), bestPiece.getReferenceCell().getY(), bestPiece);
		}
		return new Brain.Place();
	}

	public int ratePiece(TargetArea area, SlatePiece tempPiece, int rX, int rY) {
		// Danh gia so doan ke cua 1 Piece
		int count = 0;
		// Location of each Point of core
		int pX = 0;
		int pY = 0;
		ArrayList<Point> _core = tempPiece.getCore();

		for (int i = 0; i < _core.size(); i++) {
			pX = _core.get(i).getX() + rX;
			pY = _core.get(i).getY() + rY;

			if (pX == 0 || pX == CommonVL.SIZE_TARGET_AREA - 1)
				count++;
			if (pY == 0 || pY == CommonVL.SIZE_TARGET_AREA - 1)
				count++;
			if (pY >= 0 && pX > 0 && area.getValue(pX - 1, pY) != CommonVL.SPACE)
				count++;

			if (pX >= 0 && pY > 0 && area.getValue(pX, pY - 1) != CommonVL.SPACE)
				count++;

			if (pY >= 0 && pX >= 0 && pX < CommonVL.SIZE_TARGET_AREA - 1 && area.getValue(pX + 1, pY) != CommonVL.SPACE)
				count++;

			if (pX >= 0 && pY >= 0 && pY < CommonVL.SIZE_TARGET_AREA - 1 && area.getValue(pX, pY + 1) != CommonVL.SPACE)
				count++;
		}
		return count;
	}

	private SlatePiece randomChoosePiece(ArrayList<SlatePiece> pieceArr) {
		Random rand = new Random();
		if (pieceArr.size() == 0) {
			return null;
		}
		return pieceArr.get(rand.nextInt(pieceArr.size()));
	}
}
