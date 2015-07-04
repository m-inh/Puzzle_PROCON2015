package procon.uet;

import java.lang.annotation.Target;

import javax.swing.text.html.parser.TagElement;

import org.junit.*;

import static org.junit.Assert.*;

public class TargetAreaSpec {
	private static TargetArea targetArea;
	private static SlatePiece s1;
	private static SlatePiece s2;
	
	@Before
	public void setup(){
		String[] str = new String[]{
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
		
		targetArea = new TargetArea(str);
		targetArea.commit();
		
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
		assertEquals(targetArea.getEmptyCells(), 189);
	}
	
	@Test
	public void testPlaceOverlappingObstacles(){
		assertEquals(targetArea.place(s1, 0, 0), TargetArea.PLACE_BAD);
	}
	
	@Test
	public void testOverlappingExistingPiece(){
		targetArea.place(s1, 2, 0);
		targetArea.commit();
		assertEquals(TargetArea.PLACE_BAD, targetArea.place(s2, 2, 2));
	}
	
	@Test
	public void testPlaceOutBounds(){
		assertEquals(targetArea.place(s1, -2, 0), TargetArea.PLACE_OUT_BOUNDS);
	}
	
	@Test
	public void testPlaceOK(){
		assertEquals(targetArea.place(s1, 1, 0), TargetArea.PLACE_OK);
		targetArea.commit();
		assertEquals(targetArea.place(s2, 3, 1), TargetArea.PLACE_OK);
	}
	
	@Test
	public void testPlaceNonadjacent(){
		targetArea.place(s1, 2, 0);
		targetArea.commit();
		assertEquals(targetArea.place(s2, 5, 1), TargetArea.PLACE_NONADJACENT);
	}
	
	@Test
	public void testRemoval(){
		
	}
	
//	public static void main(String[] args){
//		TargetAreaSpec tA = new TargetAreaSpec();
//		tA.setup();
<<<<<<< HEAD
//		targetArea.place(s2, 2, 2);
//		targetArea.commit();
//		targetArea.print();
//		targetArea.remove(s2);
=======
>>>>>>> 7c90870d96174251a524843afd2588622eef54d4
//		targetArea.place(s1, 2, 0);
//		targetArea.commit();
//		targetArea.print();
//		System.out.println(targetArea.place(s2, 2, 2) + " " + TargetArea.PLACE_BAD);
//	}
}
