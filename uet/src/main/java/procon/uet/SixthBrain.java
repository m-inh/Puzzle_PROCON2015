package procon.uet;

import java.util.ArrayList;
import java.util.Random;

public class SixthBrain extends ThirdBrain{
	private int minSizeOfPieces = 16;
	private int maxSizeOfPieces = 1;
	
	public Place bestPlace(TargetArea area, SlatePiece piece){
		ArrayList<EqualAdjacentPiece> equalAdPieces = arrayOfEqualAjacentPieces(area, piece);
		SlatePiece bestPiece = null;
		for (int i = 0; i < equalAdPieces.size(); i++){
			ArrayList<SlatePiece> equalPieceArr = equalAdPieces.get(i).getEqualPieceArr();
			int j = 0;
			for (j = 0; j < equalPieceArr.size(); j++) {
				int hole = rateHole(area.clone(), equalPieceArr.get(j));
				if (hole == 0 || (hole < maxSizeOfPieces && hole > minSizeOfPieces)){
					if (hole == 0)
						bestPiece = equalPieceArr.get(j);
					else
						bestPiece = decideChooseRandomize(equalPieceArr.get(j));
					break;
				}
			}
			if (j < equalAdPieces.size())
				break;
		}
		
		if (bestPiece != null){
			return new Place(bestPiece.getLocation().x, bestPiece.getLocation().y, bestPiece);
		}
		else
			return new Place();
	}
	
	public void prepare(SlatePiece[] pieces){
		for (int i = 0; i < pieces.length; i++){
			if (minSizeOfPieces > pieces[i].getSize())
				minSizeOfPieces = pieces[i].getSize();
			if (maxSizeOfPieces < pieces[i].getSize())
				maxSizeOfPieces = pieces[i].getSize();
		}
	}
	
	protected int rateHole(TargetArea area, SlatePiece piece){
		int count = 0;
		int minX = piece.getLocation().x + piece.getMinX();
		int minY = piece.getLocation().y + piece.getMinY();
		int maxX = piece.getLocation().x + piece.getMaxX();
		int maxY = piece.getLocation().y + piece.getMaxY();
		area.placeWithoutChecking(piece);
		for (int i = minY; i <= maxY; i++){
			for (int j = minX; j <= maxX; j++){
				
			}
		}
		
		return count;
	}
	
	private SlatePiece decideChooseRandomize(SlatePiece piece){
		Random rand = new Random();
		
		if (rand.nextInt(100)%100 > 90)
			return piece;
		else
			return null;
	}
}
