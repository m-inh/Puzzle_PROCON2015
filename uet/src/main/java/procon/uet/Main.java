package procon.uet;

import procon.uet.SlatePiece;
import procon.uet.FileManager;

public class Main 
{
	private static TargetArea area;
	private static SlatePiece[] pieceArr;
	private static Brain.Place bestPlace;
	private static int bestMark = 10000;
	private static String answer = "";
	
    public static void main( String[] args )
    {
    	FileManager fileMgr = new FileManager();
    	FirstBrain brain = new FirstBrain();
    	
    	int currentMark = 0;
		fileMgr.readFile();
//		area = fileMgr.getArea();
		
		TargetArea bestAreaResult = area;
		//execute 10.000 times and choose the best time
		for (int k = 0; k < 1000; k++) {
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
