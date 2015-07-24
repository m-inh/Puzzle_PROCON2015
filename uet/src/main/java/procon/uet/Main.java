package procon.uet;

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
		execute(5,1);
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
			if (noBrain != 5){
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
				
			}
			else {
				ArrayList<ArrayList<Integer> > indexArr = new ArrayList<ArrayList<Integer> >();
				indexArr = ((FifthBrain) brain).allSetsOfPiecesHaveNumberOfBlocksNotGetOverEmptyCells(area,pieceArr);
				ArrayList<SlatePiece> selectedPieces = new ArrayList<SlatePiece>();
				ArrayList<Integer> selectedIndex = new ArrayList<Integer>();
				System.out.println(indexArr.size());
				for (int i = 0; i < indexArr.size(); i++) {
					ArrayList<Integer> tempIntArr = indexArr.get(i);
					ArrayList<SlatePiece> tempPieces = new ArrayList<SlatePiece>();
					int currentMark = ((FifthBrain) brain).bestPlace(area, pieceArr, tempIntArr, tempPieces);
					if (bestMark < currentMark){
						selectedIndex = tempIntArr;
						selectedPieces = tempPieces;
					}
				}
				int j = 0;
				for (int i = 0; i < selectedIndex.size(); i++){
					while (j < selectedIndex.get(i)){
						j++;
						tempAnswer += ";";
					}
					if (selectedPieces.get(i) != null){
						tempAnswer += selectedPieces.get(i).toString();
						area.placeWithoutChecking(selectedPieces.get(i));
					}
					tempAnswer += ";";
				}
				noSlatePiecesUsedCurrent = selectedIndex.size();
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
