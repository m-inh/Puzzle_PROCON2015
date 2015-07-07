package procon.uet;

public class TargetArea {
	//Content inside targetarea
	private int[][] grid = new int[CommonVL.SIZE_TARGET_AREA][CommonVL.SIZE_TARGET_AREA];
	//Backup of content
	private int[][] gridBackup = new int[CommonVL.SIZE_TARGET_AREA][CommonVL.SIZE_TARGET_AREA];
	private boolean committed;
//	private int emptyCells;
//	private int emptyCellsBackup;
	//Number of pieces have placed on the target area
	private int noPieces;
	private int noPiecesBackup;
	
//	private int minX, maxX, minY, maxY;
//	private int minXBackup, maxXBackup, minYBackup, maxYBackup;
	
	//If no problem happens, it will have status PLACE_OK
	public static final int PLACE_OK = 0;
	public static final int PLACE_OUT_BOUNDS = 1;
	public static final int PLACE_BAD = 2;
	public static final int PLACE_NONADJACENT = 3;
	
	public TargetArea(String[] areaString) {
		for (int i = 0; i < CommonVL.SIZE_TARGET_AREA; i++) {
			for (int j = 0; j < CommonVL.SIZE_TARGET_AREA; j++) {
				int numb = areaString[j].charAt(i) - 48;
				if (numb == 1){
					grid[i][j] = CommonVL.OBSTACLE;
				} else{
					grid[i][j] = CommonVL.SPACE;
//					emptyCells++;
				}
			}
		}
	}
	
	//x, y is coordinate of reference cell
	public int place(SlatePiece slatepiece, int x, int y){
		if (!committed)
			throw new RuntimeException("Place commit problem!");
		committed = false;
		backup();
		int count = 0;
		for (int i = 0; i < slatepiece.getSize(); i++){
			int newX = x + slatepiece.getCore().get(i).x;
            int newY = y + slatepiece.getCore().get(i).y;

            if(newX < 0 || newY < 0 || newX >= CommonVL.SIZE_TARGET_AREA || newY >= CommonVL.SIZE_TARGET_AREA){
                return  PLACE_OUT_BOUNDS;
            }
            
            if(grid[newX][newY] > 0){
                return PLACE_BAD;
            }
            
          //Check if around area of each block in slate piece has no element of other slate pieces
            if (noPieces > 0){
            	boolean a = newX > 0, b = newY > 0;
            	boolean c = newX < CommonVL.SIZE_TARGET_AREA - 1, d = newY < CommonVL.SIZE_TARGET_AREA - 1;
            	if (a && c){
            		if (b && d){
            			if (grid[newX-1][newY] <= 1 && grid[newX+1][newY] <= 1 && grid[newX][newY-1] <= 1 && grid[newX][newY+1] <= 1)
            				count++;
            		}
            		else {
            			if (!b){
            				if (grid[newX-1][newY] <= 1 && grid[newX+1][newY] <= 1 && grid[newX][newY+1] <= 1)
            					count++;
            			}
            			else{
            				if (grid[newX-1][newY] <= 1 && grid[newX+1][newY] <= 1 && grid[newX][newY-1] <= 1)
            					count++;
            			}
            		}
            	}
            	else{
            		if (!a){
            			if (b && d){
                			if (grid[newX+1][newY] <= 1 && grid[newX][newY-1] <= 1 && grid[newX][newY+1] <= 1)
                				count++;
                		}
                		else {
                			if (!b){
                				if (grid[newX+1][newY] <= 1 && grid[newX][newY+1] <= 1)
                					count++;
                			}
                			else{
                				if (grid[newX+1][newY] <= 1 && grid[newX][newY-1] <= 1)
                					count++;
                			}
                		}
            		}
            		else{
            			if (b && d){
                			if (grid[newX-1][newY] <= 1 && grid[newX][newY-1] <= 1 && grid[newX][newY+1] <= 1)
                				count++;
                		}
                		else {
                			if (!b){
                				if (grid[newX-1][newY] <= 1 && grid[newX][newY+1] <= 1)
                					count++;
                			}
                			else{
                				if (grid[newX-1][newY] <= 1 && grid[newX][newY-1] <= 1)
                					count++;
                			}
                		}
            		}
            	}
			}
		}

		if (count < slatepiece.getSize()){
			for (int i = 0; i < slatepiece.getSize(); i++){
				int newX = x + slatepiece.getCore().get(i).x;
	            int newY = y + slatepiece.getCore().get(i).y;
	            
	            grid[newX][newY] = CommonVL.BLOCK;
			}
			
			slatepiece.setLocation(x, y);
//			emptyCells -= slatepiece.getCore().size();
//			emptyCellsBackup = emptyCells;
			noPieces ++;
			return PLACE_OK;
		}
		else{
			return PLACE_NONADJACENT;
		}
	}
	
	public int countEmptyCells(){
//		return emptyCells;
		int count = 0;
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				if (grid[i][j] == 0){
					count++;
				}
			}
		}
		return count;
	}
	
	//Remove specified slate piece
	public void remove(SlatePiece slatepiece){
		int x = slatepiece.getLocation().x;
		int y = slatepiece.getLocation().y;
		for (int i = 0; i < slatepiece.getSize(); i++){
			int newX = x + slatepiece.getCore().get(i).x;
            int newY = y + slatepiece.getCore().get(i).y;
            
			grid[newX][newY] = CommonVL.SPACE;
		}
		
		noPieces --;
	}
	//Update gridbackup
	public void backup(){
		for (int i=0; i < CommonVL.SIZE_TARGET_AREA; i++)
			System.arraycopy(grid[i], 0, gridBackup[i], 0, grid[i].length);
		noPiecesBackup = noPieces;
//		emptyCellsBackup = emptyCells;
		
//		minXBackup = minX;
//		maxXBackup = maxX;
//		minYBackup = minY;
//		maxXBackup = maxY;
	}
	//Turn back the previous state
	public void undo(){
		if (!committed){
			committed = true;
			int[][] tmpGrid = grid;
			grid = gridBackup;
			gridBackup = tmpGrid;
			noPieces = noPiecesBackup;
			
//			emptyCells = emptyCellsBackup;
			
//			minX = minXBackup;
//			maxX = maxXBackup;
//			minY = minYBackup;
//			maxY = maxYBackup;
		}
	}
	
	public void commit(){
		committed = true;
	}
	
	//print area in the console
	public void print(){
		for (int i = 0; i < CommonVL.SIZE_TARGET_AREA; i++) {
			System.out.print(i%10+". ");
			for (int j = 0; j < CommonVL.SIZE_TARGET_AREA; j++) {
				System.out.print(grid[j][i] + " ");
			}
			System.out.println();
		}
	}
	// Return the value of grid
	public int getValue(int x, int y){
		return grid[x][y];
	}
	public int getValue(Point p){
		return grid[p.getX()][p.getY()];
	}
	//return the number of pieces
	public int getNoPieces() {
		return noPieces;
	}
	
//	private void calculateMinMaxXY(){
//		for (int i = 0; i < CommonVL.SIZE_TARGET_AREA; i++){
//			for (int j = 0; j < CommonVL.SIZE_TARGET_AREA; j++){
//				if (grid[i][j] > 0)
//					
//			}
//		}
//	}
}
