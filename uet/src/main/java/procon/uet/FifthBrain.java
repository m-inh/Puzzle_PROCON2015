package procon.uet;

import java.util.ArrayList;

public class FifthBrain extends ThirdBrain{	
	public int bestPlace(TargetArea area, SlatePiece pieces[], int i, ArrayList<Integer> index, ArrayList<SlatePiece> selectedPiece){
		if (i < index.size() - 1){
			int best = 1024;
			ArrayList<SlatePiece> goodPieces = mostAdjacentPieces(area, pieces[index.get(i)]);
//			System.out.println("i = " + i + " good piece: " + goodPieces);
			if (goodPieces.size() == 0){
				selectedPiece.add(null);
				return bestPlace(area, pieces, i+1, index, selectedPiece);
			}
			ArrayList<SlatePiece> primeSelectedPiece = (ArrayList<SlatePiece>) selectedPiece.clone();
//			System.out.println("before:");
//			area.print();
			for (int j = 0; j < goodPieces.size(); j++){
				TargetArea otherArea = area.clone();
				ArrayList<SlatePiece> otherSelectedPieces = (ArrayList<SlatePiece>) primeSelectedPiece.clone();
				otherArea.placeWithoutChecking(goodPieces.get(j));
				otherArea.commit();
				otherSelectedPieces.add(goodPieces.get(j));
				int current = bestPlace(otherArea, pieces, i+1, index, otherSelectedPieces);
				
//				System.out.println("i = " + i + " j = " + j + " current = " + current + " other selected: " + otherSelectedPieces);
				if (best > current){
					best = current;
					selectedPiece.clear();
					for (int k = 0; k < otherSelectedPieces.size(); k++)
						selectedPiece.add(otherSelectedPieces.get(k));
				}
			}
//			System.out.println("after:");
//			area.print();
//			System.out.println("selected: " + selectedPiece);
			
			return best;
		}
		else{
			ArrayList<SlatePiece> goodPieces = mostAdjacentPieces(area, pieces[index.get(i)]);
			if (goodPieces.size() > 0)
				return area.countEmptyCells() - pieces[index.get(i)].getSize();
			else
				return area.countEmptyCells();
		}
	}
}
