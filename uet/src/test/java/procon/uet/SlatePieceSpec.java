package procon.uet;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.*;

public class SlatePieceSpec {
	private static SlatePiece s1;
	private static SlatePiece s2;
	private SlatePiece s3;
	private SlatePiece s4;
	private SlatePiece s5;
	private SlatePiece s6;
	
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
}
