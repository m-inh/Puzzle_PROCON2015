package procon.uet;

import java.awt.Point;
import java.util.ArrayList;

public class SlatePiece {
	private static int edge = 8;
	
	private ArrayList<Point> core;
	private int heightCore, widthCore;
	//Each point in the slate pice hava a absolute location to the reference cell
	private Point referenceCell;
	//Location of the reference cell in the targer area
	private SlatePiece nextRotation;
	//Return a slate pice is the next rotation
	private SlatePiece flipOver;
	
	public SlatePiece(ArrayList<Point> _core){
		core = _core;
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
		//lap cho den khi nao manh sau khi duoc quay trung voi manh dang duyet
		//gan manh do voi manh ban dau la root
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
}
