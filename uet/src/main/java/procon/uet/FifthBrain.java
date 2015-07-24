package procon.uet;

import java.util.ArrayList;
import java.util.Collections;

public class FifthBrain extends ThirdBrain{	
	public int bestPlaceInAllCases(TargetArea area, SlatePiece pieces[], int i, ArrayList<Integer> index, ArrayList<SlatePiece> selectedPiece){
		if (i < index.size() - 1){
			int best = 1024;
			ArrayList<SlatePiece> goodPieces = mostAdjacentPieces(area, pieces[index.get(i)]);

			if (goodPieces.size() == 0){
				selectedPiece.add(null);
				return bestPlaceInAllCases(area, pieces, i + 1, index, selectedPiece);
			}
			ArrayList<SlatePiece> primeSelectedPiece = (ArrayList<SlatePiece>) selectedPiece.clone();
			
			for (int j = 0; j < goodPieces.size(); j++){
				TargetArea otherArea = area.clone();
				ArrayList<SlatePiece> otherSelectedPieces = (ArrayList<SlatePiece>) primeSelectedPiece.clone();
				otherArea.placeWithoutChecking(goodPieces.get(j));
				otherArea.commit();
				otherSelectedPieces.add(goodPieces.get(j));
				int current = bestPlaceInAllCases(otherArea, pieces, i+1, index, otherSelectedPieces);
				
				if (best > current){
					best = current;
					selectedPiece.clear();
					for (int k = 0; k < otherSelectedPieces.size(); k++)
						selectedPiece.add(otherSelectedPieces.get(k));
				}
			}
			
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
	
	public int bestPlaceInAllCases(TargetArea area, SlatePiece[] pieces, ArrayList<Integer> index, ArrayList<SlatePiece> selectedPiece) {
		int bestMark = 1024;
		ArrayList<SlatePiece> nextPieces[] = new ArrayList[index.size()];
		ArrayList<EqualAdjacentPiece> theFirst = arrayOfEqualAjacentPieces(area, pieces[index.get(0)]);
		
		for (int i = 0; i < 2; i++){
			nextPieces[0] = theFirst.get(i).getEqualPieceArr();
			for (int j = 0; j < nextPieces[0].size(); j++){
				ArrayList<SlatePiece> otherSelectedPieces = new ArrayList<SlatePiece>();
				TargetArea otherArea = area.clone();
				int up = 1;
				int down = index.size() - 1;
				otherSelectedPieces.add(nextPieces[0].get(j));
				otherArea.placeWithoutChecking(nextPieces[0].get(j));

				int[] cases = new int[index.size()];
				int emptyCells;
				
				while (up < index.size()){
					nextPieces[up] = mostAdjacentPieces(otherArea, pieces[index.get(up)]);
					if (nextPieces[up].size() > 0){
						if (up < down){ //up < index.size() - 1
							otherSelectedPieces.add(nextPieces[up].get(cases[up]));
							otherArea.placeWithoutChecking(nextPieces[up].get(cases[up]));
						}
					}
					else{
						otherSelectedPieces.add(null);
					}
					up++;
				}

				while (down > 0){
					while (up < index.size()){
						if (cases[down] < nextPieces[down].size()){
							if (cases[down] > 0){
								otherArea.remove(nextPieces[down].get(cases[down] - 1));
								otherSelectedPieces.remove(otherSelectedPieces.size() - 1);
							}
							
							otherSelectedPieces.add(nextPieces[down].get(cases[down]));
							otherArea.placeWithoutChecking(nextPieces[down].get(cases[down]));
						}
						else
							break;
						nextPieces[up] = mostAdjacentPieces(otherArea, pieces[index.get(up)]);

						up++;
						down++;
					}
					
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

					while (cases[down] >= nextPieces[down].size()){
						if (nextPieces[down].size() > 0)
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
	
	public int bestPlace(TargetArea area, SlatePiece pieces[], ArrayList<Integer> index, ArrayList<SlatePiece> selectedPieces){
		int bestMark = 1024;
		int begin = 0;
		ArrayList<EqualAdjacentPiece> theFirst = arrayOfEqualAjacentPieces(area, pieces[index.get(0)]);
		while (theFirst.isEmpty()){
			begin++;
			theFirst = arrayOfEqualAjacentPieces(area, pieces[index.get(begin)]);
		}
		TargetArea otherArea = area.clone();
		ArrayList<SlatePiece> otherSelectedPieces = new ArrayList<SlatePiece>();
		int length = (theFirst.size() > 2) ? 2 : theFirst.size();
		
		for (int i = 0; i < length; i++) {
			for (int l = 0; l < theFirst.get(i).size(); l++) {
				SlatePiece theFirstPiece = theFirst.get(i).getEqualPieceArr().get(l);
				otherSelectedPieces.add(theFirstPiece);
				otherArea.placeWithoutChecking(theFirstPiece);
				for (int j = begin + 1; j < index.size(); j++) {
					ArrayList<SlatePiece> pieceArr = mostAdjacentPieces(otherArea, pieces[index.get(j)]);
					SlatePiece nextPiece = randomChoosePiece(pieceArr);
					otherSelectedPieces.add(nextPiece);
					if (nextPiece != null)
						otherArea.placeWithoutChecking(nextPiece);
				}
				if (bestMark > otherArea.countEmptyCells()){
					bestMark = otherArea.countEmptyCells();
					selectedPieces.clear();
					for (int k = 0; k < otherSelectedPieces.size(); k++){
						selectedPieces.add(otherSelectedPieces.get(k));
					}
				}
				otherSelectedPieces = new ArrayList<SlatePiece>();
				otherArea = area.clone();
			}
		}
		
		return bestMark;
	}

	public ArrayList<ArrayList<Integer> > allSetsOfPiecesHaveNumberOfBlocksNotGetOverEmptyCells(TargetArea area, SlatePiece pieces[]) {
		ArrayList<ArrayList<Integer> > indexArr = new ArrayList<ArrayList<Integer> >();
		int totalEmptyBlock = area.countEmptyCells(); 
		int length = pieces.length;
		
		for (int i = 0; i < length; i++) {
			int j = 0;
			int countBlock = 0;
			int increase = 0;
			ArrayList<Integer> indexSelected = new ArrayList<Integer>();
			while (i + increase < length){
				int up = i + increase + 1;
				countBlock = pieces[i].getSize();
				indexSelected.add(i);
				while (up < length && countBlock + pieces[up].getSize() < totalEmptyBlock){
					countBlock += pieces[up].getSize();
					indexSelected.add(up);
					up++;
				}
				increase ++;
			
				Collections.sort(indexSelected);
				for (j = 0; j < indexArr.size(); j++){
					if (indexArr.get(j).containsAll(indexSelected))
						break;
				}
				if (j >= indexArr.size())
					indexArr.add(indexSelected);
				indexSelected = new ArrayList<Integer>();
			}
		}
		
		return indexArr;
	}
}
