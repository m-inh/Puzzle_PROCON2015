package procon.uet;

public interface Brain {
	public static class Place{
		// coordinate of reference cell
		public int rX;
		public int rY;
		
		public SlatePiece piece;
//		public TargetArea area;
		public Place(int rX, int rY, SlatePiece piece) {
			this.rX = rX;
			this.rY = rY;
			this.piece = piece;
		}
		public Place() {
		}
	}
	public Brain.Place bestPlace(TargetArea area, SlatePiece piece);
	public void prepare(SlatePiece[] pieceArr, int index);
}
