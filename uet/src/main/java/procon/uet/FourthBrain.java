package procon.uet;

import java.util.ArrayList;
import java.util.Collections;

public class FourthBrain extends ThirdBrain{
	public Place bestPlace(TargetArea area, SlatePiece piece) {
		ArrayList<EqualAdjacentPiece> equalPieceArr = arrayOfEqualAjacentPieces(area, piece);
		
		SlatePiece bestPiece = null;
		EqualAdjacentPiece equalAdPiece = chooseEqualAdjacentPiece(equalPieceArr);
		
		while (equalAdPiece == null || equalAdPiece.getMark() >= piece.getEdges() - 1){
			equalAdPiece = chooseEqualAdjacentPiece(equalPieceArr);
		}
		
		if (equalAdPiece != null){
			bestPiece = equalAdPiece.chooseRandomPiece();
			if (bestPiece != null) {
				return new Brain.Place(bestPiece.getReferenceCell().getX(), bestPiece.getReferenceCell().getY(), bestPiece);
			}
		}
		return new Brain.Place();
	}
	
	protected ArrayList<EqualAdjacentPiece> arrayOfEqualAjacentPieces(TargetArea area, SlatePiece piece){
		ArrayList<EqualAdjacentPiece> equalPieceArr = super.arrayOfEqualAjacentPieces(area, piece);
		Collections.sort(equalPieceArr);		
		return equalPieceArr;
	}
	
	public static void main(String[] args) {
		TargetArea area;
		SlatePiece[] pieceArr;
		Brain.Place bestPlace;
		int bestMark = 10000;
		String answer = "";
		
		FileManager fileMgr = new FileManager("11.txt");
    	FourthBrain brain = new FourthBrain();
    	
    	int currentMark = 0;
		fileMgr.readFile();
//		area = fileMgr.getArea();
		
		area = new TargetArea(fileMgr.getAreaString());
		TargetArea bestAreaResult = area;
		pieceArr = fileMgr.getPieceArr();
		//execute 10.000 times and choose the best time
		for (int k = 0; k < 10; k++) {
			area = new TargetArea(fileMgr.getAreaString());
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
		fileMgr.createNewOutputFile(3);
		fileMgr.writeLine(answer);
	}
}
