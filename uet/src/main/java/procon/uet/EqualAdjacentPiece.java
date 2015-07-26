package procon.uet;

import java.util.ArrayList;
import java.util.Random;

public class EqualAdjacentPiece implements Comparable{
	private ArrayList<SlatePiece> equalPieceArr;
	private int mark;
	
	public EqualAdjacentPiece(){}
	
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
	
	public EqualAdjacentPiece clone(){
		EqualAdjacentPiece res = new EqualAdjacentPiece();
		res.equalPieceArr = (ArrayList)equalPieceArr.clone();
		res.mark = mark;
		
		return res;
	}

	public int compareTo(Object obj) {	
		EqualAdjacentPiece other = (EqualAdjacentPiece)obj;
		
		if (mark < other.mark)
			return 1;
		else
			if (mark > other.mark)
				return -1;
			else
				return 0;
	}
	
	public void simplify(TargetArea area){
		for (int i = 0; i < equalPieceArr.size(); i++){
			TargetArea areaI = area.clone();
			areaI.placeWithoutChecking(equalPieceArr.get(i));
			for (int j = i + 1; j < equalPieceArr.size();){
				TargetArea areaJ = area.clone();
				areaJ.placeWithoutChecking(equalPieceArr.get(j));
				if (areaI.equals(areaJ)){
					equalPieceArr.remove(j);
				}
				else
					j++;
			}
		}
	}
}
