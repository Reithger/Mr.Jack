package game;

public class DrawnTile {

	double x;
	double y;
	
	String type;
	int index;
	
	boolean state;
	
	public DrawnTile(double inX, double inY, String label, int loc) {
		x = inX;
		y = inY;
		type = label;
		index = loc;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public String getType() {
		return type;
	}
	
	public int getIndex() {
		return index;
	}
	
	public boolean getState() {
		return state;
	}
	
	public void setX(double in) {
		x = in;
	}
	
	public void setY(double in) {
		y = in;
	}
	
	public void setType(String in) {
		type = in;
	}
	
	public void setState(boolean in) {
		state = in;
	}
	
}
