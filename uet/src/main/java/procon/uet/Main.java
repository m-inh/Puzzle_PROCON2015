package procon.uet;

import procon.uet.FifthBrain.IndexSlatePieceSelected;
import procon.uet.SlatePiece;

import java.util.ArrayList;

import procon.uet.FileManager;

public class Main 
{
	private static TargetArea area;
	private static SlatePiece[] pieceArr;
	private static Brain.Place bestPlace;
	private static int bestMark = 10000;
	private static int noSlatePiecesUsedMin = 10000;
	private static String answer = "";
	private static TargetArea bestAreaResult;
	private static FileManager fileMgr;
	
    public static void main( String[] args )
    {
    	fileMgr = new FileManager("9.txt");
		fileMgr.readFile();
		System.out.println("-------------------------");
		
//		execute(1,10);
//		execute(3, 10);
//		execute(4,20);
//		execute(5,10);
    }
    
    // Execute nhan 2 tham so truyen vao: ten brain thuc thi va so lan thuc thi
    // tuong ung
    private static void execute(int noBrain, int NoExecute){
    	Brain brain = new FirstBrain();
    	bestMark = 10000;
		noSlatePiecesUsedMin = 10000;
		bestAreaResult = new TargetArea(fileMgr.getAreaString());
		pieceArr = fileMgr.getPieceArr();
    	switch (noBrain) {
		case 1:
			brain = new FirstBrain();
			break;
		case 2:
			// use for 2nd Brain :v
			brain = new SecondBrain();
			break;
		case 3:
			brain = new ThirdBrain();
			break;
		case 4:
			brain = new FourthBrain();
			break;
		case 5:
			brain = new FifthBrain();
			break;
		default:
			break;
		}
		//execute few times and choose the best time
		for (int k = 0; k < NoExecute; k++) {
			area = new TargetArea(fileMgr.getAreaString());
//			System.out.println("width area: "+CommonVL.WIDTH_TARGET_AREA);
//			System.out.println("height area: "+CommonVL.HEIGHT_TARGET_AREA);
			area.commit();
			int noSlatePiecesUsedCurrent = 0;
			String tempAnswer = "";
			if (noBrain != 5)
				for (int i = 0; i < pieceArr.length; i++){
					bestPlace = brain.bestPlace(area, pieceArr[i]);
					if (bestPlace.piece != null) 
					{
						area.place(bestPlace.piece, bestPlace.rX, bestPlace.rY);
						area.commit();
						tempAnswer += bestPlace.piece.toString();
						noSlatePiecesUsedCurrent++;
	//					System.out.println(bestPlace.piece.toString());
	//					area.print();
					} else{
	//					System.out.println("Skip this slate piece");
					}
					tempAnswer += ";";
				}
			else {
				ArrayList<IndexSlatePieceSelected> indexArr = new ArrayList<IndexSlatePieceSelected>();
				indexArr = ((FifthBrain) brain).getIndexSlatePieceSelectedArr(area,pieceArr);
				ArrayList<SlatePiece> piecesSelected = new ArrayList<SlatePiece>();
				for (int i = 0; i < indexArr.size(); i++) {
//					FifthBrain.IndexSlatePieceSelected tempIndexSelected = indexArr.get(i);
					ArrayList<Integer> tempIntArr = indexArr.get(i).getIntArr();
//					for (int j = 0; j < tempIntArr.size(); j++) {
//						bestPlace = ((FifthBrain) brain).bestPlace(area, pieceArr, 0, tempIntArr, piecesSelected);
//						if (bestPlace.piece != null){
//							
//						}
//					}
				}
			}
			int currentMark = 0;
			currentMark = area.countEmptyCells();
			System.out.println("Current mark: "+currentMark);
			System.out.println("Current best mark: "+bestMark);
			System.out.println("Current Number of SlatePieces: "+noSlatePiecesUsedCurrent);
			System.out.println("Current Number of SlatePieces Min: "+noSlatePiecesUsedMin);
			if (bestMark > currentMark || (bestMark == currentMark && noSlatePiecesUsedMin > noSlatePiecesUsedCurrent)){
				bestMark = currentMark;
				noSlatePiecesUsedMin = noSlatePiecesUsedCurrent;
				bestAreaResult = area;
				answer = tempAnswer;
			}
		}
		// print the best area result
		bestAreaResult.print();
//		System.out.println("Best mark: "+bestMark);
//		System.out.println("Number of SlatePieces min: "+noSlatePiecesUsedMin);
		fileMgr.createNewOutputFile(noBrain,bestMark,noSlatePiecesUsedMin);
		fileMgr.writeLine(answer); 
    }
}
