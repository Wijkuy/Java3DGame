
public class Vector4 {
	private double x;
	private double y;
	private double z;
	private double w;

	Vector4(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = 0.0;
	}

	Vector4(double x, double y, double z, double w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}

	Vector4(Vector3 v) {
		this.x = v.getX();
		this.y = v.getY();
		this.z = v.getZ();
		this.w = 1.0;
	}

	public Vector4() {
		this.x = 0.0;
		this.y = 0.0;
		this.z = 0.0;
		this.w = 0.0;
	}

	public double getX() {
		return this.x;

	}

	public double getY() {
		return this.y;

	}

	public double getZ() {
		return this.z;
	}

	public double getW() {
		return this.w;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void setZ(double z) {
		this.z = z;
	}

	public void setW(double w) {
		this.w = w;
	}

	public Vector4 get() {
		return this;
	}

	public double[] toArray() {
		return new double[] { this.x, this.y, this.z, this.w };
	}

	public Vector3 toVector3() {
		return new Vector3(this.getX(), this.getY(), this.getZ());
	}

	@Override
	public String toString() {
		return "[" + this.x + "\t" + this.y + "\t" + this.z + "\t" + this.w + "]";
	}
}
