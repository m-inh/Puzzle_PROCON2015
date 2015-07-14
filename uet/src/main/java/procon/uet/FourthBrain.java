package procon.uet;

import java.util.ArrayList;

public class FourthBrain extends ThirdBrain{
	public Place bestPlace(TargetArea area, SlatePiece piece) {
		
		return null;
	}
	
	protected ArrayList<EqualAdjacentPiece> arrayOfEqualAjacentPieces(TargetArea area, SlatePiece piece){
		ArrayList<EqualAdjacentPiece> equalPieceArr = super.arrayOfEqualAjacentPieces(area, piece);
		
		
		
		return equalPieceArr;
	}
}
