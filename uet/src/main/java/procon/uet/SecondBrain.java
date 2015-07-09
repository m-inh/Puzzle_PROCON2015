package procon.uet;

import java.util.ArrayList;

import procon.uet.Brain.Place;

public class SecondBrain extends FirstBrain implements Brain{
	@Override
	public Brain.Place bestPlace(TargetArea area, SlatePiece piece){
		ArrayList<SlatePiece> pieceArr = mostAdjacentPieces(area, piece);
		
		SlatePiece bestPiece = null;
		//Algorithm to select the best piece
		
		if (bestPiece != null)
			return new Brain.Place(bestPiece.getLocation().x, bestPiece.getLocation().y, bestPiece);
		else
			return new Brain.Place();
	}
}
