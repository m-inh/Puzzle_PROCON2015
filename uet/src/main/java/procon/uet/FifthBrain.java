package procon.uet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Queue;

public class FifthBrain extends ThirdBrain{	
	public int bestPlace(TargetArea area, SlatePiece pieces[], int i, ArrayList<Integer> index, ArrayList<SlatePiece> selectedPiece){
		if (i < index.size()){
			int best = 1024;
			ArrayList<SlatePiece> goodPieces = mostAdjacentPieces(area, pieces[index.get(i)]);
			System.out.println("i = " + i + " good piece: " + goodPieces);
			if (goodPieces.size() == 0){
				selectedPiece.add(null);
				return bestPlace(area, pieces, i+1, index, selectedPiece);
			}
			ArrayList<SlatePiece> primeSelectedPiece = (ArrayList<SlatePiece>) selectedPiece.clone();
			TargetArea primeArea = area.clone();
			
			for (int j = 0; j < goodPieces.size(); j++){
				TargetArea otherArea = primeArea.clone();
				ArrayList<SlatePiece> otherSelectedPieces = (ArrayList<SlatePiece>) primeSelectedPiece.clone();
				otherArea.place(goodPieces.get(j), goodPieces.get(j).getLocation().x, goodPieces.get(j).getLocation().y);
				otherArea.commit();
				otherSelectedPieces.add(goodPieces.get(j));
				int current = bestPlace(otherArea, pieces, i+1, index, otherSelectedPieces);
				
				System.out.println("i = " + i + " j = " + j + " current = " + current + " other selected: " + otherSelectedPieces);
				if (best > current){
					best = current;
					area.copy(otherArea);
					selectedPiece.removeAll(selectedPiece);
					for (int k = 0; k < otherSelectedPieces.size(); k++)
						selectedPiece.add(otherSelectedPieces.get(k));
				}
			}
//			area.print();
			System.out.println("selected: " + selectedPiece);
			
			return best;
		}
		else
			return area.countEmptyCells();
	}
}
