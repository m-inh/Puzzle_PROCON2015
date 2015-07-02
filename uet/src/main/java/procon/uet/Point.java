package procon.uet;

public class Point {
	public int x, y;
	
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public boolean equals(Object obj){
		if (obj == this)
			return true;
		if (!(obj instanceof Point))
			return false;
		Point other = (Point)obj;
		
		return (x == other.x && y == other.y);
	}
	
	public void print(){
		System.out.println(x+" "+y);
	}
	
}