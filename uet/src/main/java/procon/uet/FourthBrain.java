package procon.uet;

import java.util.ArrayList;
import java.util.Collections;

public class FourthBrain extends ThirdBrain{
	
	public Place bestPlace(TargetArea area, SlatePiece piece) {
		ArrayList<EqualAdjacentPiece> equalPieceArr = arrayOfEqualAjacentPieces(area, piece);
		
		SlatePiece bestPiece = null;
		if (equalPieceArr.size() > 0){
			EqualAdjacentPiece equalAdPiece = equalPieceArr.get(0);
			
			if (area.getNoPieces() == 0){
//				while (equalPieceArr.size() > 1 && equalAdPiece.getMark() >= piece.getEdges() - 1){
//					equalAdPiece = chooseEqualAdjacentPiece(equalPieceArr);
//				}
				if (equalPieceArr.size() > 1 && equalAdPiece.getMark() >= piece.getEdges() - 1){
					equalAdPiece = equalPieceArr.get(1);
				}
			}
			
			bestPiece = equalAdPiece.chooseRandomPiece();
			if (bestPiece != null) {
				return new Brain.Place(bestPiece.getReferenceCell().getX(), bestPiece.getReferenceCell().getY(), bestPiece);
			}
		}
		
		return new Brain.Place();
	}
	
	public ArrayList<EqualAdjacentPiece> arrayOfEqualAjacentPieces(TargetArea area, SlatePiece piece){
		ArrayList<EqualAdjacentPiece> equalPieceArr = super.arrayOfEqualAjacentPieces(area, piece);
		Collections.sort(equalPieceArr);	
		return equalPieceArr;
	}
}
