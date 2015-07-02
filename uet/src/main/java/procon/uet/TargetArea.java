package procon.uet;

public class TargetArea {
	//Content inside targetarea
	private int[][] grid;
	//Backup of content
	private int[][] gridBackup;
	private boolean committed;
	private int emptyCells = 0;
	//If no problem happens, it will have status PLACE_OK
	public static final int PLACE_OK = 0;
	public static final int PLACE_OUT_BOUNDS = 1;
	public static final int PLACE_BAD = 2;
	public static final int PLACE_NONADJACENT = 3;
	
	public TargetArea(String[] areaString) {
		grid = new int[CommonVL.SIZE_TARGET_AREA][CommonVL.SIZE_TARGET_AREA];
		for (int i = 0; i < CommonVL.SIZE_TARGET_AREA; i++) {
			for (int j = 0; j < CommonVL.SIZE_TARGET_AREA; j++) {
				int numb = areaString[j].charAt(i) - 48;
				if (numb == 1){
					grid[i][j] = CommonVL.OBSTACLE;
				} else{
					grid[i][j] = CommonVL.SPACE;
					emptyCells++;
				}
			}
		}
	}
	
	public int place(SlatePiece slatepiece, int x, int y){
		if (!committed)
			throw new RuntimeException("Place commit problem!");
		committed = false;
		backup();
		int count = 0;
		for (int i = 0; i < slatepiece.getCore().size(); i++){
			int newX = x + slatepiece.getCore().get(i).x;
            int newY = y + slatepiece.getCore().get(i).y;

            if(newX < 0 || newY < 0 || newX >= CommonVL.SIZE_TARGET_AREA || newY >= CommonVL.SIZE_TARGET_AREA){
                return  PLACE_OUT_BOUNDS;
            }
            if(grid[newX][newY] > 0){                        
                return PLACE_BAD;
            }
            //Check if around area of each block in slate piece has no element of other slate pieces
            if (grid[newX-1][newY] <= 1 && grid[newX][newY-1] <= 1 && grid[newX+1][newY] <= 1 && grid[newX][newY+1] <= 1)
            	count ++;
		}
		if (count < slatepiece.getCore().size()){
			for (int i = 0; i < slatepiece.getCore().size(); i++){
				int newX = x + slatepiece.getCore().get(i).x;
	            int newY = y + slatepiece.getCore().get(i).y;
	            
	            grid[newX][newY] = CommonVL.BLOCK;
			}
			
			slatepiece.setLocation(x, y);
			emptyCells -= slatepiece.getCore().size();
			return PLACE_OK;
		}
		else{
			return PLACE_NONADJACENT;
		}
	}
	
	public int getEmptyCells(){
		return emptyCells;
	}
	
	//Remove specified slate piece
	public void remove(SlatePiece slatepiece){
		int x = slatepiece.getLocation().x;
		int y = slatepiece.getLocation().y;
		for (int i = 0; i < slatepiece.getCore().size(); i++){
			int newX = x + slatepiece.getCore().get(i).x;
            int newY = y + slatepiece.getCore().get(i).y;
            
			grid[newX][newY] = CommonVL.SPACE;
		}
	}
	//Update gridbackup
	public void backup(){
		for (int i=0; i < CommonVL.SIZE_TARGET_AREA; i++)
			System.arraycopy(grid[i], 0, gridBackup[i], 0, grid[i].length);
	}
	//Turn back the previous state
	public void undo(){
		if (!committed){
			commit();
			int[][] tmpGrid = grid;
			grid = gridBackup;
			gridBackup = tmpGrid;
		}
	}
	
	public void commit(){
		committed = true;
	}
	
	public void print(){
		for (int i = 0; i < CommonVL.SIZE_TARGET_AREA; i++) {
			for (int j = 0; j < CommonVL.SIZE_TARGET_AREA; j++) {
				System.out.print(grid[j][i] + " ");
			}
			System.out.println();
		}
	}
}
