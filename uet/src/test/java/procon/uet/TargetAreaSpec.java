package procon.uet;

import java.lang.annotation.Target;

import javax.swing.text.html.parser.TagElement;

import org.junit.*;

import static org.junit.Assert.*;

public class TargetAreaSpec {
	private static TargetArea targetArea1;
	private static TargetArea targetArea2;
	private static SlatePiece s1;
	private static SlatePiece s2;
	private static TargetArea target11;
	private static SlatePiece[] slatePieces;
	private static FileManager fileMgr;
	@Before
	public void setup(){
		String[] str1 = new String[]{
						"00000000000000001111111111111111",
						"00000000000000001111111111111111",
						"01000000000000001111111111111111",
						"00000000000000001111111111111111",
						"00000000000000001111111111111111",
						"00000000000000001111111111111111",
						"00000000000000001111111111111111",
						"00000100000000001111111111111111",
						"00000000000000001111111111111111",
						"00000000000000001111111111111111",
						"00000000000000001111111111111111",
						"00000000010000001111111111111111",
						"11111111111111111111111111111111",
						"11111111111111111111111111111111",
						"11111111111111111111111111111111",
						"11111111111111111111111111111111",
						"11111111111111111111111111111111",
						"11111111111111111111111111111111",
						"11111111111111111111111111111111",
						"11111111111111111111111111111111",
						"11111111111111111111111111111111",
						"11111111111111111111111111111111",
						"11111111111111111111111111111111",
						"11111111111111111111111111111111",
						"11111111111111111111111111111111",
						"11111111111111111111111111111111",
						"11111111111111111111111111111111",
						"11111111111111111111111111111111",
						"11111111111111111111111111111111",
						"11111111111111111111111111111111",
						"11111111111111111111111111111111",
						"11111111111111111111111111111111"
		};
		String[] str2 = new String[]{
				"00000000000000000000000000000000",
				"00000000000000000000000000000000",
				"00000000000000000000000000000000",
				"00000000000000000000000000000000",
				"00000000000000000000000000000000",
				"00000000000000000000000000000000",
				"00000000000000000000000000000000",
				"00000000000000000000000000000000",
				"00000000000000000000000000000000",
				"00000000000000000000000000000000",
				"00000000000000000000000000000000",
				"00000000000000000000000000000000",
				"00000000000000000000000000000000",
				"00000000000000000000000000000000",
				"00000000000000000000000000000000",
				"00000000000000000000000000000000",
				"00000000000000000000000000000000",
				"00000000000000000000000000000000",
				"00000000000000000000000000000000",
				"00000000000000000000000000000000",
				"00000000000000000000000000000000",
				"00000000000000000000000000000000",
				"00000000000000000000000000000000",
				"00000000000000000000000000000000",
				"00000000000000000000000000000000",
				"00000000000000000000000000000000",
				"00000000000000000000000000000000",
				"00000000000000000000000000000000",
				"00000000000000000000000000000000",
				"00000000000000000000000000000000",
				"00000000000000000000000000000000",
				"00000000000000000000000000000000"
		};
		targetArea1 = new TargetArea(str1);
		targetArea1.commit();
		targetArea2 = new TargetArea(str2);
		targetArea2.commit();
		String[] s1string = new String[]{
				"01000000",
				"01000000",
				"01000000",
				"01000000",
				"01000000",
				"01000000",
				"01110000",
				"00000000"
		};
		String[] s2string = new String[]{
				"00000000",
				"01100000",
				"01100000",
				"01100000",
				"01100000",
				"00000000",
				"00000000",
				"00000000"
		};
		s1 = new SlatePiece(s1string);
		s2 = new SlatePiece(s2string);
		fileMgr = new FileManager("11.txt");
		fileMgr.readFile();
		target11 = new TargetArea(fileMgr.getAreaString());
		slatePieces = fileMgr.getPieceArr();
	}
	
	@Test
	public void testEmptyCells(){
		assertEquals(targetArea1.countEmptyCells(), 189);
	}
	
	@Test
	public void testPlaceOverlappingObstacles(){
		assertEquals(targetArea1.place(s1, 0, 0), TargetArea.PLACE_BAD);
	}
	
	@Test
	public void testOverlappingExistingPiece(){
		targetArea1.place(s1, 2, 0);
		targetArea1.commit();
		targetArea2.place(s2, 3, 24);
		targetArea2.commit();

		assertEquals(TargetArea.PLACE_BAD, targetArea2.place(s2, 2, 24));
		assertEquals(TargetArea.PLACE_BAD, targetArea1.place(s2, 2, 2));
	}
	
	@Test
	public void testPlaceOutBounds(){
		assertEquals(TargetArea.PLACE_OUT_BOUNDS, targetArea1.place(s1, -2, 0));
		assertEquals(TargetArea.PLACE_OUT_BOUNDS, targetArea2.place(s1, 2, 28));
		targetArea2.commit();
		assertEquals(TargetArea.PLACE_OUT_BOUNDS, targetArea2.place(s2, 30, 28));
		targetArea2.commit();
		assertEquals(TargetArea.PLACE_OUT_BOUNDS, targetArea2.place(s2, 30, 0));
		targetArea2.commit();
		assertEquals(TargetArea.PLACE_OUT_BOUNDS, targetArea2.place(s2, 15, 28));
		targetArea2.commit();
		assertEquals(TargetArea.PLACE_OUT_BOUNDS, targetArea2.place(s2, 30, 20));
	}
	
	@Test
	public void testPlaceOK(){
		assertEquals(targetArea1.place(s1.fastRotation(), -1, 5), TargetArea.PLACE_OK);
		targetArea1.undo();
		assertEquals(targetArea1.place(s1, 1, 0), TargetArea.PLACE_OK);
		targetArea1.commit();
		assertEquals(targetArea1.place(s2, 3, 1), TargetArea.PLACE_OK);
		targetArea1.commit();
	}
	
	@Test
	public void testPlaceNonadjacent(){
		targetArea1.place(s1, 2, 0);
		targetArea1.commit();
		assertEquals(targetArea1.place(s2, 5, 1), TargetArea.PLACE_NONADJACENT);
	}
	
	@Test
	public void testPlaceNonadjacentOfManyPieces(){
		target11.commit();
		assertEquals(TargetArea.PLACE_OK, target11.place(slatePieces[0].fastRotation().fastRotation(), 0, -1));
		target11.commit();
		assertEquals(TargetArea.PLACE_OK, target11.place(slatePieces[1].fastRotation().fastRotation(), -1, -6));
		target11.commit();
		assertEquals(TargetArea.PLACE_OK, target11.place(slatePieces[2].fastRotation(), 3, -6));
		target11.commit();
		assertEquals(TargetArea.PLACE_OK, target11.place(slatePieces[3].fastRotation(), 1, -6));
		target11.commit();
		assertEquals(TargetArea.PLACE_OK, target11.place(slatePieces[4], 4, 1));
		target11.commit();
		assertEquals(TargetArea.PLACE_OK, target11.place(slatePieces[5].fastRotation().fastRotation().fastRotation(), 11, -6));
		target11.commit();
		assertEquals(TargetArea.PLACE_OK, target11.place(slatePieces[6].fastRotation().fastRotation().fastRotation(), 12, 0));
		target11.commit();
		assertEquals(TargetArea.PLACE_OK, target11.place(slatePieces[7].fastRotation(), 15, 1));
		target11.commit();
		assertEquals(TargetArea.PLACE_NONADJACENT, target11.place(slatePieces[9], 29, 23));
//		target11.commit();
//		assertEquals(TargetArea.PLACE_OK, target11.place(slatePieces[9].fastRotation(), 15, 1));
//		target11.commit();
	}
//	public static void main(String[] args) {
//		TargetAreaSpec ta = new TargetAreaSpec();
//		ta.setup();
//		System.out.println(targetArea1.place(s1.fastFlipOver().fastRotation().fastRotation(), -1, -1));
//		targetArea1.commit();
//		targetArea1.print();
//	}
}
