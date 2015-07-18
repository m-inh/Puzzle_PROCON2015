package procon.uet;

import java.util.ArrayList;
import java.util.Collections;

public class FifthBrain extends ThirdBrain{
	public Place bestPlace(TargetArea area, int i, SlatePiece[] pieces){
		ArrayList<EqualAdjacentPiece> equalPieceArr = arrayOfEqualAjacentPieces(area, pieces[i]);
		SlatePiece bestPiece = null;
		
		if ( i >= pieces.length - 1 ){
			if (equalPieceArr.size() > 0)
				bestPiece = equalPieceArr.get(0).chooseRandomPiece();
		}
		else{
			if (i == 0 && equalPieceArr.get(0).getMark() >= pieces[i].getEdges() - 1)
				bestPiece = equalPieceArr.get(1).chooseRandomPiece();
			SlatePiece curPiece = null;
			TargetArea areaClone = new TargetArea();
			areaClone.copy(area);
			
			if (equalPieceArr.size() > 0){
				curPiece = equalPieceArr.get(0).chooseRandomPiece();
				areaClone.place(curPiece, curPiece.getLocation().x, curPiece.getLocation().y);
				areaClone.commit();
			}
			ArrayList<EqualAdjacentPiece> nextEqualPieceArr = arrayOfEqualAjacentPieces(areaClone, pieces[i+1]);
			if (nextEqualPieceArr.size() > 0 && nextEqualPieceArr.get(0).getMark() >= pieces[i+1].getEdges() - 1)
				bestPiece = curPiece;
			else
				if (nextEqualPieceArr.size() > 1)
					bestPiece = equalPieceArr.get(0).chooseRandomPiece();
		}
//		bestPiece = equalAdPiece.chooseRandomPiece();
		if (bestPiece != null) {
			return new Brain.Place(bestPiece.getReferenceCell().getX(), bestPiece.getReferenceCell().getY(), bestPiece);
		}
		
		return new Place();
	}
	
	protected ArrayList<EqualAdjacentPiece> arrayOfEqualAjacentPieces(TargetArea area, SlatePiece piece){
		ArrayList<EqualAdjacentPiece> equalPieceArr = super.arrayOfEqualAjacentPieces(area, piece);
		Collections.sort(equalPieceArr);
		return equalPieceArr;
	}
}
