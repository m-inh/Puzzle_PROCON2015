package procon.uet;

import java.lang.annotation.Target;
import java.util.ArrayList;

public class SecondBrain extends FirstBrain{
	public int bestPlace(TargetArea area, SlatePiece[] pieces, int i, int blocks, ArrayList<SlatePiece> chosenPiece, int emptyCells, Integer bestMark){
		if (blocks >  emptyCells|| i >= pieces.length - 1)
			return emptyCells - blocks;
		ArrayList<SlatePiece> pieceArr = mostAdjacentPieces(area, pieces[i]);
		area.undo();
		if (chosenPiece.size() > 0)
			chosenPiece.remove(chosenPiece.size() - 1);
		
		System.out.println("i = " + i + " size: " + pieceArr.size());
//		int bestMark = emptyCells - blocks;
		
		for (int j = 0; j < pieceArr.size(); j++){
			chosenPiece.add(pieceArr.get(j));
			blocks += pieceArr.get(j).getSize();
			int currentMark = bestPlace(area, pieces, i+1, blocks, chosenPiece, emptyCells, bestMark);
			if (currentMark < bestMark && currentMark >= 0){
				System.out.println("i = " + i + " j = " + j + " blocks: " + blocks);
//				System.out.println(chosenPiece);
				
				area.place(pieceArr.get(j), pieceArr.get(j).getLocation().x, pieceArr.get(j).getLocation().y);
				area.commit();
				bestMark = currentMark;
			}
			System.out.println("best mark: " + bestMark);
		}
		
		return emptyCells - blocks;
	}
	
	public ArrayList<SlatePiece> bestResult(TargetArea area, SlatePiece[] pieces){		
		ArrayList<SlatePiece> res = new ArrayList<SlatePiece>();
		Integer bestMark = area.countEmptyCells();
		
		area.commit();
		
		bestPlace(area, pieces, 0, 0, res, area.countEmptyCells(), bestMark);
		System.out.println(bestMark);
		for (int i = 0; i < res.size(); i++){
			area.place(res.get(i), res.get(i).getLocation().x, res.get(i).getLocation().y);
			area.commit();
		}
		
		return res;
	}
	
	public static void main(String[] args) {
		SecondBrain secondBrain = new SecondBrain();
		FileManager fileMgr = new FileManager();
		fileMgr.readFile();
		
		TargetArea area = new TargetArea(fileMgr.getAreaString());
		SlatePiece[] pieceArr = fileMgr.getPieceArr();
		
		ArrayList<SlatePiece> bestAnswer = secondBrain.bestResult(area, pieceArr);

//		area.print();
		System.out.println("Mark: " + area.countEmptyCells());
	}
}
