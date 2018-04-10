
public class Vector3 {

	private double x;
	private double y;
	private double z;

	Vector3(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vector3() {
		this.x = 0;
		this.y = 0;
		this.z = 0;
	}

	public Vector3(Vector4 other) {
		this.x = other.getX();
		this.y = other.getY();
		this.z = other.getZ();
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

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void setZ(double z) {
		this.z = z;
	}

	public Vector3 get() {
		return this;
	}

	public void set(double i, double j, double k) {
		this.x = i;
		this.y = j;
		this.z = k;

	}

	public Vector3 subFrom(Vector3 other) {
		this.x -= other.getX();
		this.y -= other.getY();
		this.z -= other.getZ();
		return this;
	}

	public Vector3 sub(Vector3 other) {
		return new Vector3(this.x - other.getX(), this.y - other.getY(), this.z - other.getZ());
	}

	public Vector3 add(Vector3 other) {
		this.x += other.getX();
		this.y += other.getY();
		this.z += other.getZ();
		return this;
	}

	public double dot(Vector3 other) {
		return (this.x * other.getX()) + (this.y * other.getY()) + (this.z * other.getZ());
	}

	public Vector3 mul(double other) {
		this.x *= other;
		this.y *= other;
		this.z *= other;
		return this;
	}

	@Override
	public String toString() {
		return "[" + this.x + "," + this.y + "," + this.z + "]";
	}

}
