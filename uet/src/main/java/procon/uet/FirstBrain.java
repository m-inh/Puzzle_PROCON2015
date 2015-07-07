package procon.uet;

import java.util.ArrayList;
import java.util.Random;

public class FirstBrain implements Brain {
	public Brain.Place bestPlace(TargetArea area, SlatePiece piece) {
		int maxMark = 0;
		int currentMark = 0;
		ArrayList<SlatePiece> pieceArr = new ArrayList<SlatePiece>();
		SlatePiece currPiece = piece;
		SlatePiece tempPiece = piece;
		
		// Xoay 90 (tat ca trang thai xoay cua 1 manh)
		for (int k = 0; k < 4; k++) {
			for (int i = - tempPiece.getMinX();
					i < CommonVL.SIZE_TARGET_AREA - tempPiece.getMaxX();
					i++)
			{
				for (int j = - tempPiece.getMinY();
						j < CommonVL.SIZE_TARGET_AREA - tempPiece.getMaxY();
						j++)
				{
//					System.out.println(i + " " + j);
					if (area.place(tempPiece, i, j) == TargetArea.PLACE_OK)
					{
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
					}
					else
						area.commit();
				}
			}
			tempPiece = tempPiece.fastRotation();
		}
		
		// Flip the piece
		currPiece = piece.fastFlipOver();
		tempPiece = currPiece;
		
		for (int k = 0; k < 4; k++) {
			for (int i = - tempPiece.getMinX();
					i < CommonVL.SIZE_TARGET_AREA - tempPiece.getMaxX();
					i++)
			{
				for (int j = - tempPiece.getMinY();
						j < CommonVL.SIZE_TARGET_AREA - tempPiece.getMaxY();
						j++)
				{
//					System.out.println(i + " " + j);
					if (area.place(tempPiece, i, j) == TargetArea.PLACE_OK)
					{
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
					}
					else
						area.commit();
				}
			}
			tempPiece = tempPiece.fastRotation();
		}
		
		SlatePiece bestPiece = (SlatePiece) randomChoosePiece(pieceArr);
		return new Brain.Place(bestPiece.getReferenceCell().getX(), bestPiece.getReferenceCell().getY(), bestPiece);
	}

	public int ratePiece(TargetArea area, SlatePiece tempPiece, int rX, int rY) {
		// Danh gia so doan ke cua 1 Piece
		int count = 0;
		//Location of each Point of core
		int pX = 0;
		int pY = 0;
		ArrayList<Point> _core = tempPiece.getCore();
		
//		System.out.println(tempPiece.toString());
		
		for (int i = 0; i < _core.size(); i++) {
			pX = _core.get(i).getX() + rX;
			pY = _core.get(i).getY() + rY;
			
			System.out.println("px: " + pX + " py: " + pY);
			
			if (pX == 0 || pX == CommonVL.SIZE_TARGET_AREA - 1)
				count ++;
			if (pY == 0 || pY == CommonVL.SIZE_TARGET_AREA - 1)
				count ++;
			if(pX > 0 && area.getValue(pX-1, pY) != CommonVL.SPACE)
				count++;

			if (pY > 0 && area.getValue(pX, pY-1) != CommonVL.SPACE)
				count++;

			if (pX < CommonVL.SIZE_TARGET_AREA - 1 && area.getValue(pX+1, pY) != CommonVL.SPACE)
				count++;

			if (pY < CommonVL.SIZE_TARGET_AREA - 1 && area.getValue(pX, pY+1) != CommonVL.SPACE)
				count++;
		}
		return count;
	}

	private SlatePiece randomChoosePiece(ArrayList<SlatePiece> pieceArr) {
		Random rand = new Random();
		return pieceArr.get(pieceArr.size() > 0 ? rand.nextInt(pieceArr.size() - 1) : 0);
	}
}
