package procon.uet;

import procon.uet.SlatePiece;
import procon.uet.FileManager;

/**
 * Hello world!
 *
 */
public class Main 
{
    public static void main( String[] args )
    {
    	SlatePiece[] pieceArr = new SlatePiece[100];
    	FileManager fileMgr = new FileManager();
    	
		fileMgr.readFile(pieceArr);
		for (int i = 0; i < pieceArr.length; i++) {
			System.out.println(pieceArr[i].toString());
			System.out.println("-------------");
		}
//		fileMgr.writeLine("Ok ghi duoc roi");
    }
}
