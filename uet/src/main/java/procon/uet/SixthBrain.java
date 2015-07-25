package procon.uet;

import java.util.ArrayList;
import java.util.Random;

public class SixthBrain extends ThirdBrain{
	private int minSizeOfPieces = 16;
	private int maxSizeOfPieces = 1;
	
	public Place bestPlace(TargetArea area, SlatePiece piece){
		SlatePiece bestPiece = null;
		ArrayList<SlatePiece> goodPieces = new ArrayList<SlatePiece>();
		ArrayList<SlatePiece> select = new ArrayList<SlatePiece>();
//		int length = (equalAdPieces.size() > 2) ? 2 : equalAdPieces.size();
		
		if (area.getNoPieces() == 0){
			ArrayList<EqualAdjacentPiece> equalAdPieces = arrayOfEqualAjacentPieces(area, piece);
			if (!equalAdPieces.isEmpty()){
				EqualAdjacentPiece equalPieceArr = equalAdPieces.get(0);
				if (equalAdPieces.size() > 1){
					if (equalPieceArr.getMark() >= piece.getEdges() - 1){
						equalPieceArr = equalAdPieces.get(1);
					}
				}
				select = equalPieceArr.getEqualPieceArr();
			}
		}
		else{
			select = mostAdjacentPieces(area, piece);
		}
		
		for (int j = 0; j < select.size(); j++) {
			int hole = rateHole(area.clone(), select.get(j));
			if (hole == 0){
				bestPiece = select.get(j);
				break;
			}
			else{
				if ((double)hole/(hole + select.get(j).getSize()) > (double)0.7)
					if (hole >= minSizeOfPieces)
						goodPieces.add(select.get(j));
			}
		}
		
		if (bestPiece == null){
			bestPiece = randomChoosePiece(goodPieces);
		}
		
		if (bestPiece != null){
			return new Place(bestPiece.getLocation().x, bestPiece.getLocation().y, bestPiece);
		}
		else
			return new Place();
	}
	
	public void prepare(SlatePiece[] pieces, int index){
		for (int i = index; i < pieces.length; i++){
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
//		boolean upperBound = minY == 0;
//		boolean lowerBound = maxY == CommonVL.HEIGHT_TARGET_AREA - 1;
//		boolean leftBound = minX == 0;
//		boolean rightBound = maxX == CommonVL.WIDTH_TARGET_AREA - 1;
		for (int i = minY; i <= maxY; i++){
			for (int j = minX; j <= maxX; j++){
				if  (area.getGrid(i, j) == CommonVL.SPACE)
					count++;
			}
		}
		
		return count;
	}
	
	protected SlatePiece randomChoosePiece(ArrayList<SlatePiece> pieceArr) {
		Random rand = new Random();
		if (pieceArr.size() == 0) {
			return null;
		}
		// random choose the this piece
		if (rand.nextInt(100)%100 > 70){
			return null;
		}
		return pieceArr.get(rand.nextInt(pieceArr.size()));
	}
}
