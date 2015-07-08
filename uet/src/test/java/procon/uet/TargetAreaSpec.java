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
				"01100000",
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
		assertEquals(targetArea1.place(s1, 1, 0), TargetArea.PLACE_OK);
		targetArea1.commit();
		assertEquals(targetArea1.place(s2, 3, 1), TargetArea.PLACE_OK);
	}
	
	@Test
	public void testPlaceNonadjacent(){
		targetArea1.place(s1, 2, 0);
		targetArea1.commit();
		assertEquals(targetArea1.place(s2, 5, 1), TargetArea.PLACE_NONADJACENT);
	}
	
	@Test
	public void testUndo(){
		int beforeMark = targetArea1.countEmptyCells();
		targetArea1.place(s1, 2, 0);
		targetArea1.undo();
		int afterMark = targetArea1.countEmptyCells();
		assertEquals(beforeMark, afterMark);
	}
	
	public static void main(String[] args) {
		TargetAreaSpec temp = new TargetAreaSpec();
		temp.setup();
//		targetArea1.place(s1, 2, 0);
//		targetArea1.commit();
		targetArea2.place(s2, 3, 24);
		targetArea2.commit();
		System.out.println(targetArea2.place(s2, 2, 24));
	}
}
