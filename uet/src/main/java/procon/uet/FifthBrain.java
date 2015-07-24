package procon.uet;

import java.util.ArrayList;
import java.util.Stack;

public class FifthBrain extends ThirdBrain{	
	public int bestPlace(TargetArea area, SlatePiece pieces[], int i, ArrayList<Integer> index, ArrayList<SlatePiece> selectedPiece){
		if (i < index.size() - 1){
			int best = 1024;
			ArrayList<SlatePiece> goodPieces = mostAdjacentPieces(area, pieces[index.get(i)]);
//			System.out.println("i = " + i + " good piece: " + goodPieces);
			if (goodPieces.size() == 0){
				selectedPiece.add(null);
				return bestPlace(area, pieces, i + 1, index, selectedPiece);
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
			if (goodPieces.size() > 0){
				selectedPiece.add(goodPieces.get(0));
				return area.countEmptyCells() - pieces[index.get(i)].getSize();
			}
			else
				return area.countEmptyCells();
		}
	}
	
	public int bestPlace(TargetArea area, SlatePiece[] pieces, ArrayList<Integer> index, ArrayList<SlatePiece> selectedPiece){
		int bestMark = 1024;
		ArrayList<SlatePiece> nextPieces[] = new ArrayList[index.size()];
		ArrayList<EqualAdjacentPiece> theFirst = arrayOfEqualAjacentPieces(area, pieces[index.get(0)]);
		
		int up = 1;
		int down = index.size() - 1;
		
		for (int i = 0; i < 2; i++){
			nextPieces[0] = theFirst.get(i).getEqualPieceArr();
			ArrayList<SlatePiece> otherSelectedPieces = new ArrayList<SlatePiece>();
			TargetArea otherArea = area.clone();
			for (int j = 0; j < nextPieces[0].size(); j++){
				otherSelectedPieces.add(nextPieces[0].get(j));
				otherArea.placeWithoutChecking(nextPieces[0].get(j));

				int[] cases = new int[index.size()];
				int emptyCells = 1024;
				while (up < index.size()){
					nextPieces[up] = mostAdjacentPieces(otherArea, pieces[index.get(up)]);
					if (up < down){ //up < index.size() - 1
						otherSelectedPieces.add(nextPieces[up].get(cases[up]));
						otherArea.placeWithoutChecking(nextPieces[up].get(cases[up]));
					}
					
					System.out.println("nextPieces[" + up +"].size = " + nextPieces[up].size());
					
					up++;
				}
				System.out.println();
				while (down > 0){
					while (up < index.size()){
						if (cases[down] > 0)
							otherArea.remove(nextPieces[down].get(cases[down] - 1));
						otherSelectedPieces.add(nextPieces[down].get(cases[down]));
						otherArea.placeWithoutChecking(nextPieces[down].get(cases[down]));
						
						nextPieces[up] = mostAdjacentPieces(otherArea, pieces[index.get(up)]);
						
						System.out.println("nextPieces[" + up +"].size = " + nextPieces[up].size());
						
						up++;
						down++;
					}
					
					System.out.print("cases: ");
					for (int k = 0; k < cases.length; k++) {
						System.out.print(cases[k] + " ");
					}
					System.out.println();
					
					if (nextPieces[down].size() > 0){
						otherSelectedPieces.add(nextPieces[down].get(0));
						emptyCells = otherArea.countEmptyCells() - pieces[index.get(down)].getSize();
					}
					else{
						otherSelectedPieces.add(null);
						emptyCells = otherArea.countEmptyCells();
					}
					
					if (bestMark > emptyCells){
						bestMark = emptyCells;
						selectedPiece.clear();
						for (int k = 0; k < otherSelectedPieces.size(); k++){
							selectedPiece.add(otherSelectedPieces.get(k));
						}
					}
					otherSelectedPieces.remove(otherSelectedPieces.size() - 1);
					down--;
					
					cases[down]++;
					up--;
					
					if (cases[down] >= nextPieces[down].size()){
						otherArea.remove(nextPieces[down].get(cases[down] - 1));
						otherSelectedPieces.remove(otherSelectedPieces.size() - 1);
						cases[down] = 0;
						down--;
						cases[down]++;
						up--;
					}
				}
			}
		}
		
		return bestMark;
	}

	public class IndexSlatePieceSelected {
		private ArrayList<Integer> intArr;

		public IndexSlatePieceSelected() {
			intArr = new ArrayList<Integer>();
		}
		public void add(int index) {
			intArr.add(index);
		}
		public ArrayList<Integer> getIntArr() {
			return intArr;
		}
	}

	public ArrayList<FifthBrain.IndexSlatePieceSelected> getIndexSlatePieceSelectedArr(TargetArea noPieceArea, SlatePiece SlatePieceArr[]) {
		ArrayList<IndexSlatePieceSelected> indexArr = new ArrayList<FifthBrain.IndexSlatePieceSelected>();
		IndexSlatePieceSelected indexSelected = new IndexSlatePieceSelected();
		int totalEmptyBlock = noPieceArea.countEmptyCells(); 
		int countBlock = 0;
		for (int i = 0; i < SlatePieceArr.length; i++) {
			if (countBlock+SlatePieceArr[i].getSize() <= totalEmptyBlock){
				indexSelected.add(i);
				countBlock += SlatePieceArr[i].getSize();
			}
		}
		
		indexArr.add(indexSelected);
		return indexArr;
	}
}
