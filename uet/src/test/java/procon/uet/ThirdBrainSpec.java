package procon.uet;

import java.util.ArrayList;

import org.junit.*;

public class ThirdBrainSpec {
	private static FileManager fileMgr;
	private static TargetArea target11;
	private static SlatePiece[] pieces;
	private static ThirdBrain brain;
	
	@Before
	public void setup(){
		fileMgr = new FileManager("11.txt");
		fileMgr.readFile();
		target11 = new TargetArea(fileMgr.getAreaString());
		pieces = fileMgr.getPieceArr();
		brain = new ThirdBrain();
		target11.commit();
	}
	
	@Test
	public void testArrayOfEqualAdjacentPieces(){
		ArrayList<EqualAdjacentPiece> equalPieceArr = brain.arrayOfEqualAjacentPieces(target11, pieces[0]);
		for (EqualAdjacentPiece equalAdPiece : equalPieceArr){
			System.out.println(equalAdPiece.getEqualPieceArr());
		}
	}
}
