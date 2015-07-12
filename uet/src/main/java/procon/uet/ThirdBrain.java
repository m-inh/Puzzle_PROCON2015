package procon.uet;

import java.util.ArrayList;
import java.util.Random;

public class ThirdBrain extends FirstBrain{
	
	@Override
	public Place bestPlace(TargetArea area, SlatePiece piece) {
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
						
						if (currentMark == equalAdPiece.getMark()){
							equalAdPiece.pushPiece(tempPiece);
						} else {
							if (equalAdPiece.getMark() != 0){
								equalPieceArr.add(equalAdPiece);
							}
							equalAdPiece = new EqualAdjacentPiece(currentMark);
							equalAdPiece.pushPiece(tempPiece);
						}
					}
					area.commit();
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
						
						if (currentMark == equalAdPiece.getMark()){
							equalAdPiece.pushPiece(tempPiece);
						} else {
							if (equalAdPiece.getMark() != 0){
								equalPieceArr.add(equalAdPiece);
							}
							equalAdPiece = new EqualAdjacentPiece(currentMark);
							equalAdPiece.pushPiece(tempPiece);
						}
					}
					area.commit();
				}
			}
			tempPiece = tempPiece.fastRotation();
		}
		
		SlatePiece bestPiece = chooseEqualAdjacentPiece(equalPieceArr).chooseRandomPiece();
		if (bestPiece != null) {
			return new Brain.Place(bestPiece.getReferenceCell().getX(), bestPiece.getReferenceCell().getY(), bestPiece);
		}
		return new Brain.Place();
	}
	
	private EqualAdjacentPiece chooseEqualAdjacentPiece(ArrayList<EqualAdjacentPiece> equaladPiece){
		if (equaladPiece.size() == 0){
			return null;
		}
		Random rand = new Random();
		if (rand.nextInt(100)%100 > 90){
			return null;
		}
		
		
		int markSum = 0;
		for (int i = 0; i < equaladPiece.size(); i++) {
			markSum += equaladPiece.get(i).getMark();
		}
		
//		int count = 0;
		// luu vi tri cua equalAdPiece khi random
		// diem cang cao thi xuat hien cang nhieu
		ArrayList<Integer> intRandArr = new ArrayList<Integer>();
		EqualAdjacentPiece tempEqualAdpiece;
		int tempMark = 0;
		for (int i = 0, j = 0, count = 0; i < markSum; i++) {
			tempEqualAdpiece = equaladPiece.get(j);
			tempMark = tempEqualAdpiece.getMark();
			intRandArr.add(j);
			count++;
			if (tempMark == count){
				count = 0;
				j++;
			}
		}
		
		return equaladPiece.get(intRandArr.get(rand.nextInt(intRandArr.size())));
	}
	
	public static void main(String[] args) {
		TargetArea area;
		SlatePiece[] pieceArr;
		Brain.Place bestPlace;
		int bestMark = 10000;
		String answer = "";
		
		FileManager fileMgr = new FileManager();
    	ThirdBrain brain = new ThirdBrain();
    	
    	int currentMark = 0;
		fileMgr.readFile();
//		area = fileMgr.getArea();
		
		area = new TargetArea(fileMgr.getAreaString());
		TargetArea bestAreaResult = area;
		//execute 10.000 times and choose the best time
		for (int k = 0; k < 100; k++) {
			area = new TargetArea(fileMgr.getAreaString());
			pieceArr = fileMgr.getPieceArr();
//			System.out.println("width area: "+CommonVL.WIDTH_TARGET_AREA);
//			System.out.println("height area: "+CommonVL.HEIGHT_TARGET_AREA);
			area.commit();
			String tempAnswer = "";
			for (int i = 0; i < pieceArr.length; i++){
				bestPlace = brain.bestPlace(area, pieceArr[i]);
				if (bestPlace.piece != null) 
				{
					area.place(bestPlace.piece, bestPlace.rX, bestPlace.rY);
					area.commit();
					tempAnswer += bestPlace.piece.toString();
//					System.out.println(bestPlace.piece.toString());
//					area.print();
				} else{
//					System.out.println("Skip this slate piece");
				}
				tempAnswer += ";";
			}
			currentMark = area.countEmptyCells();
//			System.out.println("Current mark: "+currentMark);
//			System.out.println("Current best mark: "+bestMark);
			if (bestMark > currentMark){
				bestMark = currentMark;
				bestAreaResult = area;
				answer = tempAnswer;
			}
		}
		// print the best area result
		bestAreaResult.print();
		System.out.println("Best mark: "+bestMark);
		
		fileMgr.writeLine(answer);
	}
}








