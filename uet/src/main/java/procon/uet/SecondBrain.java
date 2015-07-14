package procon.uet;

import java.lang.annotation.Target;
import java.util.ArrayList;

public class SecondBrain extends FirstBrain{
	public class BestMark{
		public int best;
		
		public BestMark(){}
	};
	
	public int bestPlace(TargetArea area, SlatePiece[] pieces, int i, int blocks, ArrayList<SlatePiece> chosenPiece, int emptyCells, BestMark bestMark){
		if (i >= 2)
			return emptyCells - blocks;
		ArrayList<SlatePiece> pieceArr = mostAdjacentPieces(area, pieces[i]);
//		area.print();
		System.out.println(pieceArr);
		
		System.out.println("i = " + i + " pieces size: " + pieceArr.size() + " chosen piece size: " + chosenPiece.size() + " first");
		if (blocks + pieces[i].getSize() <= emptyCells){
			blocks += pieces[i].getSize();
//			System.out.println("blocks: " + blocks);
			for (int j = 0; j < pieceArr.size(); j++){
				ArrayList<SlatePiece> chosenPieceClone = (ArrayList<SlatePiece>) chosenPiece.clone();
				TargetArea areaClone = area.clone();
				
				areaClone.place(pieceArr.get(j), pieceArr.get(j).getLocation().x, pieceArr.get(j).getLocation().y);
				areaClone.commit();
				chosenPieceClone.add(pieceArr.get(j));
				
				int currentMark = bestPlace(areaClone, pieces, i+1, blocks, chosenPieceClone, emptyCells, bestMark);
				System.out.println("i = " + i + " j = " + j + " currentMark: " + currentMark + " bestMark: " + bestMark.best);
				if (currentMark < bestMark.best && currentMark >= 0){
//					System.out.println("i = " + i + " j = " + j + " blocks: " + blocks);
					area.copy(areaClone);
					chosenPiece.clear();
					for (int k = 0; k < chosenPieceClone.size(); k++){
						chosenPiece.add(chosenPieceClone.get(k));
					}
					System.out.println("i = " + i + " chosen piece size: " + chosenPiece.size());
//					System.out.println("x: " + pieceArr.get(j).getLocation().x + " y: " + pieceArr.get(j).getLocation().y);
//					area.place(pieceArr.get(j), pieceArr.get(j).getLocation().x, pieceArr.get(j).getLocation().y);
//					area.commit();
					bestMark.best = currentMark;
					System.out.println("i = " + i + " bestMark: " + bestMark.best + " last");
				}
				area.undo();
			}
		}
//		area.print();
		System.out.println("blocks: " + blocks);
		
		return bestMark.best;
	}
	
//	public Brain.Place bestPlace(TargetArea area, SlatePiece piece){
//		return null;
//	}
	
	public ArrayList<SlatePiece> bestResult(TargetArea area, SlatePiece[] pieces){		
		ArrayList<SlatePiece> res = new ArrayList<SlatePiece>();
		BestMark bestMark = new BestMark();
		bestMark.best = area.countEmptyCells();
		area.commit();
		
		bestMark.best = bestPlace(area, pieces, 0, 0, res, area.countEmptyCells(), bestMark);

		return res;
	}
	
	public static void main(String[] args) {
		SecondBrain secondBrain = new SecondBrain();
		FileManager fileMgr = new FileManager("11.txt");
		fileMgr.readFile();
		
		TargetArea area = new TargetArea(fileMgr.getAreaString());
		SlatePiece[] pieceArr = fileMgr.getPieceArr();
		
		ArrayList<SlatePiece> bestAnswer = secondBrain.bestResult(area, pieceArr);
		System.out.println(bestAnswer.size());
//		area.print();
//		System.out.println("Mark: " + area.countEmptyCells());
	}
}
