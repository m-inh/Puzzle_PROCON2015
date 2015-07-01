package procon.uet;

import procon.uet.SlatePiece;
import FileManager;

/**
 * Hello world!
 *
 */
public class Main 
{
    public static void main( String[] args )
    {
    	SlatePiece[] pieceArr;
    	FileManager fileMgr = new FileManager();
    	
		fileMgr.readFile(pieceArr);
		for (int i = 0; i < pieceArr.length; i++) {
			pieceArr[i].print();
			System.out.println("-------------");
		}
//		fileMgr.writeLine("Ok ghi duoc roi");
    }
}
