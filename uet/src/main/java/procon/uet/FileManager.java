package procon.uet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import javax.swing.JOptionPane;

import procon.uet.TargetArea;

import procon.uet.SlatePiece;


public class FileManager{
	private File questInputFile;
	private File answerOutputFile;
	private RandomAccessFile rdf;
	
	private String inputPath;
	private String outputPath;
	
	public FileManager() {
		inputPath = getClass().getResource("/uet/file/quest.txt").toString();
		outputPath = getClass().getResource("/uet/file/answer.txt").toString();
		
//		System.out.println(inputPath);
//		System.out.println(outputPath);
		answerOutputFile = new File(outputPath.substring(6));
		try {
			answerOutputFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		questInputFile = new File(inputPath.substring(6));
	}
	
// --------------------INPUT--------------------------	
	public boolean openFileInput(){
		if (checkExistFileInput() == true){
			try {
				rdf = new RandomAccessFile(questInputFile, "rw");
				return true;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				JOptionPane.showConfirmDialog(null, "Cannot open file quest!" + e.toString());
			}
		}
		return false;
	}
	
	public boolean checkExistFileInput(){
		if(questInputFile.exists() == true){
//			System.out.println("file quest ton tai");
			return true;
		} else {
			System.out.println("File quest does not exist");
			return false;
		}
	}
	
 // --------------------OUTPUT--------------------------
	public boolean openFileOutput(){
		if (checkExistFileOutput() == true){
			try {
				rdf = new RandomAccessFile(answerOutputFile, "rw");
				return true;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				JOptionPane.showConfirmDialog(null, "Cannot open file answer!" + e.toString());
			}
		}
		return false;
	}
	
	public boolean checkExistFileOutput(){
		if(answerOutputFile.exists() == true){
			System.out.println("File answer exists");
			return true;
		} else {
			System.out.println("file answer doesnt exist");
			return false;
		}
	}
	
//-----------------------RandomAccessFile------------------------------
	public void readFile(TargetArea area, SlatePiece[] pieceArr){
		if(openFileInput() == true){
			try {
				long len = questInputFile.length();
				
				String line = "";
				line = rdf.readLine();
				int sizeArea = line.length();
				String [] areaString = new String[sizeArea];
				
				System.out.println("Size target area: "+sizeArea);
				
				int rowAreaNo = 0;
				//read TargetArea
				while(line.length() == sizeArea){
					areaString[rowAreaNo] = line;
					rowAreaNo++;
					line = rdf.readLine();
				}
				area = new TargetArea(areaString);
				
				//read Piece
				int numberOfPiece = 0;
				int pieceNo = 0;
				while(numberOfPiece <= 0){
					numberOfPiece = rdf.read() - 48;
				}
				pieceArr = new SlatePiece[numberOfPiece];
//				System.out.println("number of piece: " + numberOfPiece);
				String pieceString[] = new String[numberOfPiece];
				
				while(line != null){
					pieceString = new String[CommonVL.SLATE_PIECE_SIZE];
					for (int i = 0; i < CommonVL.SLATE_PIECE_SIZE; i++) {
						if (line != null){
							pieceString[i] = line;
//							System.out.println(pieceString[i]);
							line = rdf.readLine();
						}
					}
					pieceArr[pieceNo] = new SlatePiece(pieceString);
					pieceNo++;
				}
				
				close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Cannot read file! (readFile)");
			return;
		}
	}
	
	public void writeLine(String content){
		if (openFileOutput() == true){
			try {
//				rdf.writeUTF(content);
				rdf.writeBytes(content);
//				System.out.println(content);
				close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Cannot writeLine!");
		}
	}
	
	public void close(){
		try {
			rdf.close();
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showConfirmDialog(null, "Cannot close file!" + e.toString());
		}
	}

}

