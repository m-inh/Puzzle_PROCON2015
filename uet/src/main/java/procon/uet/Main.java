package procon.uet;

import procon.uet.SlatePiece;
import procon.uet.FileManager;

public class Main 
{
	private static TargetArea area;
	private static SlatePiece[] pieceArr;
	private static int bestMark = 10000;
    public static void main( String[] args )
    {
    	FileManager fileMgr = new FileManager();
    	FirstBrain brain = new FirstBrain();
    	
    	int currentMark = 0;
		fileMgr.readFile();
//		area = fileMgr.getArea();
		
		//execute 10.000 times and choose the best time
		for (int k = 0; k < 10000; k++) {
			area = new TargetArea(fileMgr.getAreaString());
			pieceArr = fileMgr.getPieceArr();
			
			area.commit();
			for (int i = 0; i < pieceArr.length; i++){
				Brain.Place best = brain.bestPlace(area, pieceArr[i]);
				if (best.piece != null) 
				{
					area.place(best.piece, best.rX, best.rY);
					area.commit();
					System.out.println(best.piece.toString());
				} else{
					System.out.println("Skip this slate piece");
				}
			}
			currentMark = area.countEmptyCells();
			System.out.println("Current mark: "+currentMark);
			System.out.println("Current best mark: "+bestMark);
			if (bestMark > currentMark){
				bestMark = currentMark;
			}
		}
//		area.print();
		System.out.println("Best mark: "+bestMark);
    }
}
