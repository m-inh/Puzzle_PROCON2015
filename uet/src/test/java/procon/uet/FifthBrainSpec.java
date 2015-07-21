package procon.uet;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class FifthBrainSpec {
	private static FileManager fileMgr;
	private static TargetArea target11;
	private static SlatePiece[] pieces;
	private static FifthBrain brain;
	
	@Before
	public void setup(){
		fileMgr = new FileManager("11.txt");
		fileMgr.readFile();
		target11 = new TargetArea(fileMgr.getAreaString());
		pieces = fileMgr.getPieceArr();
		brain = new FifthBrain();
		target11.commit();
	}
}
