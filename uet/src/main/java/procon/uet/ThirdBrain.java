package procon.uet;

import java.util.ArrayList;
import java.util.Collections;
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
		Collections.sort(equalPieceArr);
		// test sort()
//		System.out.println("------------------------");
//		for (int i = 0; i < equalPieceArr.size(); i++) {
//			System.out.println(equalPieceArr.get(i).getMark()+"");
//		}
		return equalPieceArr;
	}
	
	protected EqualAdjacentPiece chooseEqualAdjacentPiece(ArrayList<EqualAdjacentPiece> equalAdPieceArr){
		if (equalAdPieceArr.size() == 0){
//			System.out.println("equalAdPieceArr null");
			return null;
		}
		
		// random skip this piece
		Random rand = new Random();
//		if (rand.nextInt(100)%100 > 90){
//			return null;
//		}
		
		
		int markSum = 0;
		for (int i = 0; i < equalAdPieceArr.size(); i++) {
			markSum += equalAdPieceArr.get(i).getMark();
		}
		
//		int count = 0;
		// luu vi tri cua equalAdPiece khi random
		// diem cang cao thi xuat hien cang nhieu
		ArrayList<Integer> intRandArr = new ArrayList<Integer>();
		EqualAdjacentPiece tempEqualAdpiece;
		int tempMark = 0;
		int gravity = 1;
		markSum *= gravity;
		System.out.println("---------------------");
		// i is iterator of total size intRandom
		// j is the current mark
		// count is the iterator of each equalAdPieceArr
		for (int i = 0, j = 0, count = 0; i < markSum; i++) {
			tempEqualAdpiece = equalAdPieceArr.get(j);
			tempMark = tempEqualAdpiece.getMark();
			intRandArr.add(j);
			count++;
			System.out.println(j);
			if (tempMark * gravity == count){
				count = 0;
				j++;
			}
		}
		
		return equalAdPieceArr.get((int)intRandArr.get(rand.nextInt(intRandArr.size())));
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








