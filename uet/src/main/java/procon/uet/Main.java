package procon.uet;

import procon.uet.SlatePiece;
import procon.uet.FileManager;

public class Main 
{
	private static TargetArea area;
	private static SlatePiece[] pieceArr;
    public static void main( String[] args )
    {
    	FileManager fileMgr = new FileManager();
    	
		fileMgr.readFile(area, pieceArr);
		for (int i = 0; i < pieceArr.length; i++) {
			System.out.println(pieceArr[i].toString());
			System.out.println("-------------");
		}
//		fileMgr.writeLine("Ok ghi duoc roi");
    }
}
