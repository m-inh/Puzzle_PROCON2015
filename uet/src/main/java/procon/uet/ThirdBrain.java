package procon.uet;

import java.util.ArrayList;
import java.util.Random;

public class ThirdBrain extends FirstBrain{
	@Override
	public Place bestPlace(TargetArea area, SlatePiece piece) {
		ArrayList<EqualAdjacentPiece> equalPieceArr = arrayOfEqualAjacentPieces(area, piece);
		
		EqualAdjacentPiece equalAdPieceChosen = chooseEqualAdjacentPiece(equalPieceArr);
		SlatePiece bestPiece;
		if (equalAdPieceChosen != null){
			bestPiece = equalAdPieceChosen.chooseRandomPiece();
			if (bestPiece != null) {
				return new Brain.Place(bestPiece.getReferenceCell().getX(), bestPiece.getReferenceCell().getY(), bestPiece);
			}
		}
		return new Brain.Place();
	}
	
	protected ArrayList<EqualAdjacentPiece> arrayOfEqualAjacentPieces(TargetArea area, SlatePiece piece){
		ArrayList<EqualAdjacentPiece> equalPieceArr = new ArrayList<EqualAdjacentPiece>();
		EqualAdjacentPiece equalAdPiece = new EqualAdjacentPiece(0);
		SlatePiece tempPiece = piece;
		
		int currentMark = 0;
//		int maxMark = 0;
		for (int k = 0; k < 4; k++) {
			for (int i = 0-tempPiece.getMinX(); i < CommonVL.WIDTH_TARGET_AREA-tempPiece.getMaxX(); i++) {
				for (int j = 0-tempPiece.getMinY(); j < CommonVL.HEIGHT_TARGET_AREA-tempPiece.getMaxY(); j++) {
					
					if (area.place(tempPiece, i, j) == TargetArea.PLACE_OK){
						area.undo();
						
						currentMark = ratePiece(area, tempPiece, i, j);
						
						if (isExist(currentMark, equalPieceArr)){
							equalPieceArr.get(getPosition(currentMark, equalPieceArr)).pushPiece(tempPiece.clone());
						} else {
							equalAdPiece = new EqualAdjacentPiece(currentMark);
							equalAdPiece.pushPiece(tempPiece.clone());
							equalPieceArr.add(equalAdPiece);
						}
					} else {
						area.commit();
					}
				}
			}
			tempPiece = tempPiece.fastRotation();
		}
		
		tempPiece.fastFlipOver();
		for (int k = 0; k < 4; k++) {
			for (int i = 0-tempPiece.getMinX(); i < CommonVL.WIDTH_TARGET_AREA-tempPiece.getMaxX(); i++) {
				for (int j = 0-tempPiece.getMinY(); j < CommonVL.HEIGHT_TARGET_AREA-tempPiece.getMaxY(); j++) {
					if (area.place(tempPiece, i, j) == TargetArea.PLACE_OK){
						area.undo();
						
						currentMark = ratePiece(area, tempPiece, i, j);
						
						if (isExist(currentMark, equalPieceArr)){
							equalPieceArr.get(getPosition(currentMark, equalPieceArr)).pushPiece(tempPiece.clone());
						} else {
							equalAdPiece = new EqualAdjacentPiece(currentMark);
							equalAdPiece.pushPiece(tempPiece.clone());
							equalPieceArr.add(equalAdPiece);
						}
					} else {
						area.commit();
					}
				}
			}
			tempPiece = tempPiece.fastRotation();
		}
		
		return equalPieceArr;
	}
	
	protected EqualAdjacentPiece chooseEqualAdjacentPiece(ArrayList<EqualAdjacentPiece> equaladPieceArr){
		if (equaladPieceArr.size() == 0){
//			System.out.println("equalAdPieceArr null");
			return null;
		}
		
		// random skip this piece
		Random rand = new Random();
//		if (rand.nextInt(100)%100 > 90){
//			return null;
//		}
		
		
		int markSum = 0;
		for (int i = 0; i < equaladPieceArr.size(); i++) {
			markSum += equaladPieceArr.get(i).getMark();
		}
		
//		int count = 0;
		// luu vi tri cua equalAdPiece khi random
		// diem cang cao thi xuat hien cang nhieu
		ArrayList<Integer> intRandArr = new ArrayList<Integer>();
		EqualAdjacentPiece tempEqualAdpiece;
		int tempMark = 0;
		int gravity = 20;
		markSum *= gravity;
		for (int i = 0, j = 0, count = 0; i < markSum; i++) {
			tempEqualAdpiece = equaladPieceArr.get(j);
			tempMark = tempEqualAdpiece.getMark();
			intRandArr.add(j);
			count++;
			if (tempMark * gravity == count){
				count = 0;
				j++;
			}
		}
		
		return equaladPieceArr.get((int)intRandArr.get(rand.nextInt(intRandArr.size())));
	}
	
	private boolean isExist(int mark, ArrayList<EqualAdjacentPiece> equalAdPieceArr){
		if (mark == 0){
			return false;
		}
		for (int i = 0; i < equalAdPieceArr.size(); i++) {
			if (equalAdPieceArr.get(i).getMark() == mark){
				return true;
			}
		}
		return false;
	}
	
	private int getPosition(int mark, ArrayList<EqualAdjacentPiece> equalAdPieceArr){
		for (int i = 0; i < equalAdPieceArr.size(); i++) {
			if (equalAdPieceArr.get(i).getMark() == mark){
				return i;
			}
		}
		return -1;
	}
}








