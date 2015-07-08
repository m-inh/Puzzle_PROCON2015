package procon.uet;
import static org.junit.Assert.*;

import org.junit.*;

import procon.uet.Brain.Place;

public class FirstBrainSpec {
	private static TargetArea targetArea1;
	private static TargetArea targetArea2;
	private static TargetArea targetArea3;
	private static SlatePiece s1;
	private static SlatePiece s2;
	private static SlatePiece s3;
	
	private static FirstBrain firstBrain = new FirstBrain();
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
		String[] str3 = new String[]{
				"00000000000000001111111111111111",
				"00000000000000001111111111111111",
				"00000000000000001111111111111111",
				"00000000000000001111111111111111",
				"00000000000000001111111111111111",
				"00000000000000001111111111111111",
				"00000000000000001111111111111111",
				"00000000000000001111111111111111",
				"00000000000000001111111111111111",
				"00000000000000001111111111111111",
				"00000000000000001111111111111111",
				"00000000000000001111111111111111",
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
		targetArea1 = new TargetArea(str1);
		targetArea1.commit();
		targetArea2 = new TargetArea(str2);
		targetArea2.commit();
		targetArea3 = new TargetArea(str3);
		targetArea3.commit();
		String[] s1string = new String[]{
				"01000000",
				"01000000",
				"01000000",
				"01000000",
				"01000000",
				"01000000",
				"01000000",
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
		String[] s3string = new String[]{
				"01000000",
				"01000000",
				"01000000",
				"01000000",
				"01000000",
				"01000000",
				"01110000",
				"00000000"
		};
		s1 = new SlatePiece(s1string);
		s2 = new SlatePiece(s2string);
		s3 = new SlatePiece(s3string);
	}
	
	@Test
	public void testRateBoard(){
		assertEquals(9,firstBrain.ratePiece(targetArea1, s1, -1, 0));
		assertEquals(0,firstBrain.ratePiece(targetArea2, s2, 0, 0));
	}
	
	@Test
	public void testRateBoardWhenRotated(){
		SlatePiece s1Rotated = s1.fastRotation();
		assertEquals(1,firstBrain.ratePiece(targetArea1, s1Rotated, 0, 0));
		assertEquals(2,firstBrain.ratePiece(targetArea1, s3.fastRotation(), 8, 7));
		assertEquals(4, firstBrain.ratePiece(targetArea1, s3.fastRotation(), -1, 5));
		assertEquals(3, firstBrain.ratePiece(targetArea1, s3.fastRotation(), -1, 4));
		assertEquals(1, firstBrain.ratePiece(targetArea1, s3.fastFlipOver().fastRotation(), 6, -4));
		assertEquals(4, firstBrain.ratePiece(targetArea1, s3.fastRotation(), -1, 5));
		assertEquals(11, firstBrain.ratePiece(targetArea1, s3.fastRotation(), -1, -1));
		assertEquals(2, firstBrain.ratePiece(targetArea1, s3.fastFlipOver().fastRotation().fastRotation(), 12, 4));
	}
}


