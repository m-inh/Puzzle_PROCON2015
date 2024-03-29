package procon.uet;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.*;

public class SlatePieceSpec {
	private static SlatePiece s1;
	private static SlatePiece s2;
	
	@Before
	public void setUp(){
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
	public void testEqualsTwoSlatePieces(){
		SlatePiece s1e = new SlatePiece(new ArrayList<Point>(){{
			add(new Point(1, 0));
			add(new Point(1, 1));
			add(new Point(1, 2));
			add(new Point(1, 3));
			add(new Point(1, 4));
			add(new Point(1, 5));
			add(new Point(1, 6));
			add(new Point(2, 4));
			add(new Point(2, 6));
			add(new Point(3, 6));
		}});
		
		assertTrue(s1.equals(s1e));
	}
	
	@Test
	public void testRotateSlatePiece(){
		String[] s1string = new String[]{				
				"00000000",
				"01111111",
				"01010000",
				"01000000",
				"00000000",
				"00000000",
				"00000000",
				"00000000"
		};
		
		SlatePiece next = new SlatePiece(s1string);
		assertTrue(s1.computeNextRotation().equals(next));
		assertTrue(s1.fastRotation().fastFlipOver().equals(new SlatePiece(new String[]{
				"00000000",
				"11111110",
				"00001010",
				"00000010",
				"00000000",
				"00000000",
				"00000000",
				"00000000"
		})));
		assertTrue(s1.fastRotation().fastRotation().equals(new SlatePiece(new String[]{
				"00000000",
				"00001110",
				"00000010",
				"00000110",
				"00000010",
				"00000010",
				"00000010",
				"00000010"
		})));
	}
	
	@Test
	public void testAngleWhenRotating(){
		assertEquals(90, s1.fastRotation().getAngle());
		assertEquals(180, s1.fastRotation().fastRotation().getAngle());
		assertEquals(270, s1.fastRotation().fastRotation().fastRotation().getAngle());
	}
	
	@Test
	public void testFlipOverSlatePiece(){
		String[] s1string = new String[]{
				"00000010",
				"00000010",
				"00000010",
				"00000010",
				"00000110",
				"00000010",
				"00001110",
				"00000000"
		};
		
		SlatePiece flip = new SlatePiece(s1string);
		assertTrue(s1.computeFlippingOver().equals(flip));
		assertTrue(s1.fastRotation().fastRotation().fastFlipOver().equals(new SlatePiece(new String[]{
				"00000000",
				"01110000",
				"01000000",
				"01100000",
				"01000000",
				"01000000",
				"01000000",
				"01000000"
		})));
		assertFalse(s1.fastFlipOver().isFrontSide());
	}
	
	@Test 
	public void testRotatedFourTime(){
		assertFalse(s1.equals(s1.fastRotation()));
		assertTrue(s1.equals(s1.fastRotation().fastRotation().fastRotation().fastRotation()));
	}
	
	@Test
	public void testMinMaxWidthHeight(){
		assertEquals(1, s1.getMinX());
		assertEquals(3, s1.getMaxX());
		
		assertEquals(0, s1.getMinY());
		assertEquals(6, s1.getMaxY());
		
		SlatePiece s2Rotated = s2.fastRotation();
		assertEquals(3, s2Rotated.getMinX());
		assertEquals(6, s2Rotated.getMaxX());
	}
	
	@Test
	public void testEdges(){
		assertEquals(22, s1.getEdges());
		assertEquals(12, s2.getEdges());
	}
}



