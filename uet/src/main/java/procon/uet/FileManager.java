package procon.uet;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import javax.swing.JOptionPane;


public class FileManager {
	private File questInputFile;
	private File answerOutputFile;
	private RandomAccessFile rdf;
	
	private String inputPath;
	private String outputPath;
	
	private BufferedWriter buffWriter;
	
	private SlatePiece[] pieceArr;
//	private TargetArea area;
	
	private String [] areaString;
	
	public FileManager(String inputFileName) {
		try {
			inputPath = getClass().getResource("/file/"+inputFileName).toString();
		} catch (Exception e) {
//			e.printStackTrace();
			System.out.println("Input File Name is not invalid!!");
		}
		System.out.println("input path: "+inputPath);
		questInputFile = new File(inputPath.substring(5));
	}
	
// --------------------INPUT--------------------------	
	public boolean openFileInput(){
		if (checkExistFileInput() == true){
			try {
				rdf = new RandomAccessFile(questInputFile, "rw");
				return true;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				JOptionPane.showConfirmDialog(null, "Khong the mo file quest!" + e.toString());
			}
		}
		return false;
	}
	
	public boolean checkExistFileInput(){
		if(questInputFile.exists() == true){
//			System.out.println("file quest ton tai");
			return true;
		} else {
			System.out.println("file quest ko ton tai");
			return false;
		}
	}
	
 // --------------------OUTPUT--------------------------
	public void createNewOutputFile(int noBrain, int mark, int NoPiece){
		String fileName = inputPath.substring(inputPath.indexOf("/file") + 6, inputPath.indexOf(".txt")) + "ansOf"+ noBrain + "-" + mark + "m" + NoPiece + "no" +".txt";
		outputPath = getClass().getResource("/file").toString() + "/" + fileName;
		System.out.println("output path: "+outputPath);
		answerOutputFile = new File(outputPath.substring(5));
		try {
			if (!answerOutputFile.getParentFile().isDirectory()){
				answerOutputFile.getParentFile().mkdirs();
			}
			if (!answerOutputFile.exists()){
				answerOutputFile.createNewFile();
				System.out.println("Tao file answer thanh cong!");
			}
			System.out.println("answer path: "+answerOutputFile.getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
		}
//		try {
//			System.out.println(answerOutputFile.getAbsoluteFile());
//		} catch (SecurityException e) {
//			e.printStackTrace();
//		}
	}
	
	public boolean openFileOutput(){
		if (checkExistFileOutput() == true){
			try {
//				rdf = new RandomAccessFile(answerOutputFile, "rw");
//				System.out.println("answer path: "+answerOutputFile.getAbsolutePath());
				buffWriter = new BufferedWriter(new FileWriter(answerOutputFile.getAbsolutePath()));
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				JOptionPane.showConfirmDialog(null, "Khong the mo file answer!" + e.toString());
			}
		}
		return false;
	}
	
	public boolean checkExistFileOutput(){
		if(answerOutputFile.exists() == true){
//			System.out.println("file answer ton tai");
			return true;
		} else {
			System.out.println("file answer ko ton tai");
			return false;
		}
	}
	
//-----------------------RandomAccessFile------------------------------
	public void readFile(){
		if(openFileInput() == true){
			try {
				long len = questInputFile.length();
				
				String line = "";
				line = rdf.readLine();
				int sizeArea = line.length();
				CommonVL.SIZE_TARGET_AREA = sizeArea;
				areaString = new String[sizeArea];
				
				System.out.println("Size target area: "+sizeArea);
				
				int rowAreaNo = 0;
				// doc area
				while(line.length() == sizeArea){
					areaString[rowAreaNo] = line;
					rowAreaNo++;
					line = rdf.readLine();
				}
//				area = new TargetArea(areaString);
				
				//doc Piece
				int numberOfPiece = 0;
				int pieceNo = 0;
				line = rdf.readLine();
//				System.out.println("line length: "+line.length());
				while(line.length() != CommonVL.SLATE_PIECE_SIZE){
					if (line.length() != 0){
						numberOfPiece = (int)Integer.parseInt(line);
					}
					line = rdf.readLine();
				}
				
				CommonVL.NUMBER_OF_SLATE_PIECE = numberOfPiece;
				pieceArr = new SlatePiece[numberOfPiece];
				System.out.println("Number of piece: " + numberOfPiece);
				String pieceString[] = new String[numberOfPiece];
				while(rdf.getFilePointer() < len){
					pieceString = new String[8];
					if (line.length() != 0){
						for (int i = 0; i < 8; i++) {
							pieceString[i] = line;
//							System.out.println(pieceString[i]);
							line = rdf.readLine();
						}
						pieceArr[pieceNo] = new SlatePiece(pieceString);
						pieceNo++;
					}
					line = rdf.readLine();
				}
				close();
				
//				return pieceArr;
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Khong the doc file! (readFile)");
		}
//		return null;
	}
	
	public void writeLine(String content){
		if (openFileOutput() == true){
			try {
//				rdf.seek(rdf.length());
//				rdf.writeBytes(content + "\n");
				int tempChar;
				for (int i = 0; i < content.length(); i++) {
					tempChar = (int)content.charAt(i);
					if(tempChar != (int)';'){
						buffWriter.write(tempChar);
					} else{
						buffWriter.newLine();
//						buffWriter.write('\n');
					}
				}
				
				buffWriter.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Khong the writeLine!");
		}
	}
	
	public void close(){
		try {
			rdf.close();
//			buffWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showConfirmDialog(null, "Khong the dong file!" + e.toString());
		}
	}

	public SlatePiece[] getPieceArr() {
		return pieceArr;
	}

	public String[] getAreaString() {
		return areaString;
	}

//	public TargetArea getArea() {
//		return area;
//	}
	
}