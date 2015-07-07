package procon.uet;

import procon.uet.SlatePiece;
import procon.uet.FileManager;

public class Main 
{
	private static TargetArea area;
	private static SlatePiece[] pieceArr;
    public static void main( String[] args )
    {
    	FileManager fileMgr = new FileManager();
    	FirstBrain brain = new FirstBrain();
    	
		fileMgr.readFile();
		area = fileMgr.getArea();
		pieceArr = fileMgr.getPieceArr();
		
		area.commit();
		for (int i = 0; i < pieceArr.length; i++){
			Brain.Place best = brain.bestPlace(area, pieceArr[i]);
//			while (best.rX == CommonVL.BELOW_LIMIT_OF_LOCATION 
//					&& best.rY == CommonVL.BELOW_LIMIT_OF_LOCATION){
//				i++;
//				System.out.println(best.piece.toString());
//			}
			
//			i--;
			if (best.rX != CommonVL.BELOW_LIMIT_OF_LOCATION 
					&& best.rY != CommonVL.BELOW_LIMIT_OF_LOCATION) 
			{
				area.place(best.piece, best.rX, best.rY);
				area.commit();
				System.out.println(best.piece.toString());
			}
		}
		
		area.print();
    }
}
