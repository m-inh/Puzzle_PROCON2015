package procon.uet;

import java.awt.Point;
//import java.awt.Point;
import java.util.ArrayList;

public class SlatePiece {
	public static final int edge = 8;
	
	private ArrayList<Point> core;
	private int heightCore, widthCore;
	//Each point in the slate pice hava a absolute location to the reference cell
	private Point referenceCell;
	//Location of the reference cell in the targer area
	private SlatePiece nextRotation;
	//Return a slate pice is the next rotation
	private SlatePiece flipOver;
	//Intialize a instance slate piece with known core
	public SlatePiece(ArrayList<Point> _core){
		setCore(_core);
	}
	
	//Intialize a instance slate piece with String[]
	public SlatePiece(String[] pointStrArr) {
		int number = 0;
		ArrayList<Point> _core = new ArrayList<Point>();
		
		for (int i = 0; i < edge; i++) {
			for (int j = 0; j < edge; j++) {
				number = pointStrArr[i].charAt(j) - 48;
				if (number == 1){
					Point p = new Point(i,j);
					_core.add(p);
				}
			}
		}
		setCore(_core);
	}
	
	public void setCore(ArrayList<Point> _core){
		core = _core;
		int minHeight = core.get(0).y, maxHeight = core.get(0).y, minWidth = core.get(0).x,  maxWidth = core.get(0).x;
		for (int i = 1; i < core.size(); i++){
			if (minHeight > core.get(i).y)
				minHeight = core.get(i).y;
			if (maxHeight < core.get(i).y)
				maxHeight = core.get(i).y;
			if (minWidth > core.get(i).x)
				minWidth = core.get(i).x;
			if (maxWidth < core.get(i).x)
				maxWidth = core.get(i).x;
		}
		heightCore = maxHeight - minHeight + 1;
		widthCore = maxWidth - minWidth + 1;
	}
	
	public void setLocation(int x, int y){
		referenceCell = new Point(x, y);
	}
	
	public void setLocation(Point p){
		referenceCell = p;
	}
	
	public ArrayList<Point> getCore(){
		return core;
	}
	
	public int getHeightCore(){
		return heightCore;
	}
	
	public int getWidthCore(){
		return widthCore;
	}
	
	public SlatePiece fastRotation(){
		return nextRotation;
	}
	
	public SlatePiece fastFlipOver(){
		return flipOver;
	}
	
	public SlatePiece computeNextRotation(){
		ArrayList<Point> _core = new ArrayList<Point>();
		
		for (int i = 0; i < core.size(); i++){
			_core.add(new Point(edge - 1 - core.get(i).y, core.get(i).x));
		}
		
		return new SlatePiece(_core);
	}
	
	public SlatePiece computeFlippingOver(){
		ArrayList<Point> _core = new ArrayList<Point>();
		
		for (int i = 0; i < core.size(); i++){
			_core.add(new Point(core.get(i).x, edge - 1 - core.get(i).y));
		}
		
		return new SlatePiece(_core);
	}
	
	private static SlatePiece makeFastRotations(SlatePiece root) {
		SlatePiece pieceIsRotated = root;
	    while(!root.equals(pieceIsRotated.computeNextRotation())){
	    	pieceIsRotated.nextRotation = pieceIsRotated.computeNextRotation();
	    	pieceIsRotated = pieceIsRotated.nextRotation;
		}
		pieceIsRotated.nextRotation = root;
		return root;
	}
	
	private static SlatePiece makeFastFlipingOver(SlatePiece root){
		SlatePiece pieceIsFlipped = root.computeFlippingOver();
		pieceIsFlipped.flipOver = root;
		return root;
	}
	
	public boolean equals(Object obj){
		// standard equals() technique 1
		if (obj == this)
			return true;
		
		// standard equals() technique 2
		// (null will be false)
		if (!(obj instanceof SlatePiece))
			return false;
		
		SlatePiece other = (SlatePiece)obj;
		
		if (other.core.size() != core.size())
			return false;
		
		int count = 0;
		for(int i = 0; i< other.core.size(); i++){
			for(int j = 0; j< core.size(); j++){
				if(core.get(i).equals((Point)other.core.get(i))){
					count++;
				}
			}
		}
		
		if (count == core.size())
			return true;

		return false;
	}
	
	// print the real Points of SlatePiece
	public String toString(){
		String s = "";
		for (int i = 0; i < core.size(); i++) {
			s += core.get(i).x + " " + core.get(i).y + " ";
		}
		s = s.substring(0, s.length() - 2);
		return s;
	}
}
