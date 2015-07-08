package procon.uet;

import java.util.ArrayList;

public class SecondBrain extends FirstBrain implements Brain{
	@Override
	public Brain.Place bestPlace(TargetArea area, SlatePiece piece){
		ArrayList<SlatePiece> pieceArr = mostAdjacentPieces(area, piece);
		
		return null;
	}
}
