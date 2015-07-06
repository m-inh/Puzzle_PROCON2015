package procon.uet;

import java.util.ArrayList;
import java.util.Random;

public class FirstBrain implements Brain {
	public Brain.Place bestPlace(TargetArea area, SlatePiece piece) {
		int maxMark = 0;
//		int currentMark = rateTargetArea(area, piece);
		int currentMark = 0;
		ArrayList<SlatePiece> pieceArr = new ArrayList<SlatePiece>();
		SlatePiece tempPiece = piece;
		// Xoay 90 (tat ca trang thai xoay cua 1 manh)
		for (int k = 0; k < 4; k++) {
			for (int i = 0; i < CommonVL.SIZE_TARGET_AREA - piece.getMaxY(); i++) {
				for (int j = 0; j < CommonVL.SIZE_TARGET_AREA - piece.getMaxX(); j++) {
//					if (j - tempPiece.getMinX() >= 0 && j - tempPiece.getMaxX() <= CommonVL.SIZE_TARGET_AREA
//							&& i - tempPiece.getMinY() >= 0 && i - tempPiece.getMaxY() <= CommonVL.SIZE_TARGET_AREA)
					int rX = j - tempPiece.getMinX();
					int rY = i - tempPiece.getMinY();
					if (area.place(tempPiece, rX, rY) == TargetArea.PLACE_OK)
					{
						currentMark = rateTargetArea(area, piece, rX, rY);
						if (maxMark == currentMark) {
							pieceArr.add(tempPiece);
						}
						if (maxMark < currentMark) {
							pieceArr = new ArrayList<SlatePiece>();
							pieceArr.add(tempPiece);
							maxMark = currentMark;
						}
						tempPiece = tempPiece.fastRotation();
					}
					area.commit();
				}
			}
		}
		// Flip the piece
		tempPiece = tempPiece.fastFlipOver();
//		for (int k = 0; k < 4; k++) {
//			for (int i = 0; i < CommonVL.SIZE_TARGET_AREA; i++) {
//				for (int j = 0; j < CommonVL.SIZE_TARGET_AREA; j++) {
//					if (j - tempPiece.getMinX() >= 0 && j - tempPiece.getMaxX() <= CommonVL.SIZE_TARGET_AREA
//							&& i - tempPiece.getMinY() >= 0 && i - tempPiece.getMaxY() <= CommonVL.SIZE_TARGET_AREA) 
//					{
//						currentMark = rateTargetArea(area, piece, j, i);
//						if (maxMark < currentMark) {
//							pieceArr = new ArrayList<SlatePiece>();
//							pieceArr.add(tempPiece);
//							maxMark = currentMark;
//						}
//						if (maxMark == currentMark) {
//							pieceArr.add(tempPiece);
//						}
//						tempPiece = tempPiece.fastRotation();
//					}
//				}
//			}
//		}
		for (int k = 0; k < 4; k++) {
			for (int i = 0; i < CommonVL.SIZE_TARGET_AREA - piece.getMaxY(); i++) {
				for (int j = 0; j < CommonVL.SIZE_TARGET_AREA - piece.getMaxX(); j++) {
//					if (j - tempPiece.getMinX() >= 0 && j - tempPiece.getMaxX() <= CommonVL.SIZE_TARGET_AREA
//							&& i - tempPiece.getMinY() >= 0 && i - tempPiece.getMaxY() <= CommonVL.SIZE_TARGET_AREA)
					int rX = j - tempPiece.getMinX();
					int rY = i - tempPiece.getMinY();
					if (area.place(tempPiece, rX, rY) == TargetArea.PLACE_OK)
					{
						currentMark = rateTargetArea(area, piece, rX, rY);
						if (maxMark == currentMark) {
							pieceArr.add(tempPiece);
						}
						if (maxMark < currentMark) {
							pieceArr = new ArrayList<SlatePiece>();
							pieceArr.add(tempPiece);
							maxMark = currentMark;
						}
						tempPiece = tempPiece.fastRotation();
					}
					area.commit();
				}
			}
		}
		
		SlatePiece bestPiece = (SlatePiece) randomChoosePiece(pieceArr);
		return new Brain.Place(bestPiece.getReferenceCell().getX(), bestPiece.getReferenceCell().getY(), bestPiece);
	}

	public int rateTargetArea(TargetArea area, SlatePiece tempPiece, int rX, int rY) {
		// Danh gia so doan ke cua 1 Piece
		int count = 0;
		//Location of each Point of core
		int pX = 0;
		int pY = 0;
		ArrayList<Point> _core = tempPiece.getCore();
		for (int i = 0; i < _core.size(); i++) {
			pX = _core.get(i).getX() + rX;
			pY = _core.get(i).getY() + rY;
			System.out.println(pX + " " + pY);
			for (int j = 0; j < 4; j++) {
				//wrong code, rewrite
//				
//				if(area.getValue(pX-1, pY) != CommonVL.SPACE)
//					count++;
//
//				if (area.getValue(pX, pY-1) != CommonVL.SPACE)
//					count++;
//
//				if (area.getValue(pX+1, pY) != CommonVL.SPACE)
//					count++;
//
//				if (area.getValue(pX, pY+1) != CommonVL.SPACE)
//					count++;
			}
		}
		return count;
	}

	public SlatePiece randomChoosePiece(ArrayList<SlatePiece> pieceArr) {
		Random rand = new Random();
		return pieceArr.get(rand.nextInt(pieceArr.size() - 1));
	}
}
