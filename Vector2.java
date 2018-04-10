
public class Vector2 {

	public double x;
	public double y;

	public Vector2(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return this.x;
	}

	public double getY() {
		return this.y;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	@Override
	public String toString() {
		return "[" + this.x + "\t" + this.y + "]";
	}
	
}
