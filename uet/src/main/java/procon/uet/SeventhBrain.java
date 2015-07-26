package procon.uet;

import java.util.ArrayList;
import java.util.Random;

import procon.uet.Brain.Place;

public class SeventhBrain extends ThirdBrain{
	public Place bestPlace(TargetArea area, SlatePiece piece) {
		SlatePiece bestPiece = null;
		ArrayList<SlatePiece> selectedPieces = new ArrayList<SlatePiece>();
		if (area.getNoPieces() == 0){
			ArrayList<EqualAdjacentPiece> equalPieceArr = arrayOfEqualAjacentPieces(area, piece);
			if (equalPieceArr.size() > 0){
				EqualAdjacentPiece equalAdPiece = equalPieceArr.get(0);
				selectedPieces = equalPieceArr.get(0).getEqualPieceArr();
//				while (equalPieceArr.size() > 1 && equalAdPiece.getMark() >= piece.getEdges() - 1){
//					equalAdPiece = chooseEqualAdjacentPiece(equalPieceArr);
//				}
				if (equalPieceArr.size() > 1 && equalAdPiece.getMark() >= piece.getEdges() - 1){
					selectedPieces.addAll(equalPieceArr.get(1).getEqualPieceArr());
				}
			}
		}
		else{
			selectedPieces = mostAdjacentPieces(area, piece);
		}
		
		bestPiece = randomChoosePiece(selectedPieces);
		if (bestPiece != null) {
			return new Brain.Place(bestPiece.getReferenceCell().getX(), bestPiece.getReferenceCell().getY(), bestPiece);
		}
		
		return new Brain.Place();
	}
	
	protected SlatePiece randomChoosePiece(ArrayList<SlatePiece> pieceArr) {
		Random rand = new Random();
		if (pieceArr.size() == 0) {
			return null;
		}
		// random choose the this piece
		if (rand.nextInt(100)%100 > 80){
			return null;
		}
		return pieceArr.get(rand.nextInt(pieceArr.size()));
	}
}
