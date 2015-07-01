package procon.uet;

public class TargetArea {
	private int area[][];
	
	public TargetArea(String[] areaString) {
		area = new int[CommonVL.SIZE_TARGET_AREA][CommonVL.SIZE_TARGET_AREA];
		for (int i = 0; i < CommonVL.SIZE_TARGET_AREA; i++) {
			for (int j = 0; j < CommonVL.SIZE_TARGET_AREA; j++) {
				int numb = areaString[j].charAt(i) - 48;
				if (numb == 1){
					area[i][j] = CommonVL.OBSTACLE;
				} else{
					area[i][j] = CommonVL.SPACE;
				}
			}
		}
	}
	
	public void placeSlatePice(SlatePiece slatepiece){
		
	}
	
	public void print(){
		for (int i = 0; i < CommonVL.SIZE_TARGET_AREA; i++) {
			for (int j = 0; j < CommonVL.SIZE_TARGET_AREA; j++) {
				System.out.print(area[j][i] + " ");
			}
			System.out.println();
		}
	}
}
