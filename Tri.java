import java.awt.Color;

public class Tri {
	private Vector3 v1;
	private Vector3 v2;
	private Vector3 v3;
	private Color color;

	Tri(Vector3 v1, Vector3 v2, Vector3 v3, Color color) {
		this.v1 = v1;
		this.v2 = v2;
		this.v3 = v3;
		this.color = color;
	}

	public void updateVectors(Matrix4 modelViewMatrix) {
		update(this.v1, modelViewMatrix);
		update(this.v2, modelViewMatrix);
		update(this.v3, modelViewMatrix);
	}

	private Vector3 update(Vector3 v, Matrix4 modelViewMatrix) {
		Vector4 temp = modelViewMatrix.mul(new Vector4(v.getX(), v.getY(), v.getZ(), 1.0D));
		//System.out.println("[" + temp.getX() + "\t" + temp.getY() + "\t" + temp.getZ() + "]");
		return temp.toVector3();
	}

	private int getHeight(int height) {
		return height;
	}

	private int getWidth(int width) {
		return width;
	}

	public Vector3 getV1() {
		return this.v1;

	}

	public Vector3 getV2() {
		return this.v2;

	}

	public Vector3 getV3() {
		return this.v3;

	}

	public Color getColor() {
		return this.color;
	}

	public void setV1(Vector3 v1) {
		this.v1 = v1;
	}

	public void setV2(Vector3 v2) {
		this.v2 = v2;
	}

	public void setV3(Vector3 v3) {
		this.v3 = v3;
	}
	
	

	@Override
	public String toString() {
		return "[" + this.v1 + "\t" + this.v2 + "\t" + this.v3 + "]";
	}

}
