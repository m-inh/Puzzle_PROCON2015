package procon.uet;

import java.util.ArrayList;

public class SlatePiece {	
	private ArrayList<Point> core;
	//the location of the block nearest reference cell
	private int minX, minY;
	//the location of the block farest reference cell
	private int maxX, maxY;
	//side of each slate piece (Back or Front)
	private boolean frontSide = true;
	//angle of the slate piece (0, 90, 180, 270)
	private int angle;
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
		
		for (int i = 0; i < CommonVL.SLATE_PIECE_SIZE; i++) {
			if (pointStrArr[i].length() == CommonVL.SLATE_PIECE_SIZE){
				for (int j = 0; j < CommonVL.SLATE_PIECE_SIZE; j++) {
					number = pointStrArr[i].charAt(j) - 48;
					if (number == 1){
						Point p = new Point(j,i);
						_core.add(p);
					}
				}
			} else {
				System.out.println("String's piece is invalid!");
			}
		}
		setCore(_core);
	}
	
	public void setCore(ArrayList<Point> _core){
		core = _core;
//		side = CommonVL.FRONT_SIZE_SLATE_PIECE;
//		angle = 0;
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
		minX = minWidth;
		minY = minHeight;
		maxX = maxWidth;
		maxY = maxHeight;
	}
	
	public SlatePiece fastRotation(){
//		nextRotation = makeFastRotations(this);
		if (nextRotation == null)
			makeFastRotations(this);
		return nextRotation;
	}
	
	public SlatePiece fastFlipOver(){
//		flipOver = makeFastFlipingOver(this);
		if (flipOver == null)
			makeFastFlipingOver(this);
		return flipOver;
	}
	
	public SlatePiece computeNextRotation(){
		ArrayList<Point> _core = new ArrayList<Point>();
		
		for (int i = 0; i < core.size(); i++){
			_core.add(new Point(CommonVL.SLATE_PIECE_SIZE - 1 - core.get(i).y, core.get(i).x));
		}
		
		SlatePiece res = new SlatePiece(_core);
		res.angle = angle + 90;
		res.angle = res.angle % 360;
		res.frontSide = frontSide;
		
		return res;
	}
	
	public SlatePiece computeFlippingOver(){
		ArrayList<Point> _core = new ArrayList<Point>();
		
		for (int i = 0; i < core.size(); i++){
			_core.add(new Point(CommonVL.SLATE_PIECE_SIZE - 1 - core.get(i).x, core.get(i).y));
		}
		
		SlatePiece res = new SlatePiece(_core);
		res.frontSide = !frontSide;
		res.angle = angle;
		
		return res;
	}
	
	private static void makeFastRotations(SlatePiece root) {
		SlatePiece pieceIsRotated = root;
	    while(!root.equals(pieceIsRotated.computeNextRotation())){
	    	pieceIsRotated.nextRotation = pieceIsRotated.computeNextRotation();
	    	pieceIsRotated = pieceIsRotated.nextRotation;
		}
		pieceIsRotated.nextRotation = root;
//		return root;
	}
	
	private static void makeFastFlipingOver(SlatePiece root){
		SlatePiece pieceIsFlipped = root.computeFlippingOver();
		pieceIsFlipped.flipOver = root;
		root.flipOver = pieceIsFlipped;
//		return root;
	}
	
	public SlatePiece clone(){
		SlatePiece duplicate = new SlatePiece(core);
		duplicate.referenceCell = new Point(referenceCell.x, referenceCell.y);
		duplicate.angle = angle;
		duplicate.frontSide = frontSide;
		
		return duplicate;
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
		for(int i = 0; i< core.size(); i++){
			for(int j = 0; j< core.size(); j++){
				if(core.get(i).equals((Point)other.core.get(j))){
					count++;
				}
			}
		}
		
		return (count == core.size());
	}
	
	// print the real Points of SlatePiece
	public void print(){
		for (int i = 0; i < core.size(); i++) {
			core.get(i).print();
		}
	}
	//return number of core's block
	public int getSize() {
		return core.size();
	}
	public boolean isFrontSide() {
		return frontSide;
	}
	public int getAngle() {
		return angle;
	}
	public Point getReferenceCell() {
		return referenceCell;
	}
	public int getMinX() {
		return minX;
	}
	public int getMinY() {
		return minY;
	}
	public void setLocation(int x, int y){
		referenceCell = new Point(x, y);
	}
	public void setLocation(Point p){
		referenceCell = p;
	}
	public Point getLocation(){
		return referenceCell;
	}
	public ArrayList<Point> getCore(){
		return core;
	}
	public int getMaxX() {
		return maxX;
	}
	public int getMaxY() {
		return maxY;
	}
	public int getEdges(){
		int size = core.size();
		int count = 4*size;
		
		for (int i = 0; i < size; i++){
			for (int j = 0; j < size; j++){
				Point cur = core.get(i);
				if (cur.x > 0 && core.get(j).equals(new Point(cur.x - 1, cur.y)))
					count--;
				if (core.get(j).equals(new Point(cur.x + 1, cur.y)))
					count--;
				if (cur.y > 0 && core.get(j).equals(new Point(cur.x, cur.y - 1)))
					count--;
				if (core.get(j).equals(new Point(cur.x, cur.y + 1)))
					count--;
			}
		}
		
		return count;
	}
	public String toString(){
		String answer = referenceCell != null ? referenceCell.toString() + " " 
				+ (frontSide ? "H" : "T") + " " + Integer.toString(angle) : "";
//		CommonVL.STRING_ANSWER_LENGTH = answer.length();
		return answer;
	}
	
	public static int blocksOfAllPieces(SlatePiece[] pieces, ArrayList<Integer> index){
		int sum = 0;
		for (int i = 0; i < index.size(); i++){
			sum += pieces[index.get(i)].core.size();
		}
		
		return sum;
	}
}
