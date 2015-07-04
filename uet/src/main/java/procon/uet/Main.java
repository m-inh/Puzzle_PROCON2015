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
    	
		fileMgr.readFile();
		area = fileMgr.getArea();
		pieceArr = fileMgr.getPieceArr();
		for (int i = 0; i < pieceArr.length; i++) {
			pieceArr[i].print();
			System.out.println("-------------");
		}
		
		area.print();
		fileMgr.writeLine("Ok ghi duoc roi");
		fileMgr.writeLine("Ok ghi duoc roi");
		fileMgr.writeLine("Ok ghi duoc roi");
    }
}
