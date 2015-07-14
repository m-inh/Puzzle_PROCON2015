package procon.uet;

import java.util.ArrayList;
import java.util.Random;

public class EqualAdjacentPiece {
	private ArrayList<SlatePiece> equalPieceArr;
	private int mark;
	
	public EqualAdjacentPiece(int mark) {
		equalPieceArr = new ArrayList<SlatePiece>();
		this.mark = mark;
	}
	
	public void pushPiece(SlatePiece piece){
		equalPieceArr.add(piece);
	}
	
	public ArrayList<SlatePiece> getEqualPieceArr(){
		return equalPieceArr;
	}
	
	public int size(){
		return equalPieceArr.size();
	}
	
	public int getMark(){
		return mark;
	}
	
	public SlatePiece chooseRandomPiece(){
		Random rand = new Random();
		if (equalPieceArr.size() == 0) {
			return null;
		}
		// random choose the this piece
//		if (rand.nextInt(100)%100 > 90){
//			return null;
//		}
		return equalPieceArr.get(rand.nextInt(equalPieceArr.size()));
	}
}