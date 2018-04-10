import java.util.Arrays;
import java.util.stream.IntStream;

public class Matrix4 {
	private double[][] internalMatrix = new double[4][4];
	boolean lefthanded = false;

	Matrix4() {
		identity();
	}

	Matrix4(double a00, double a01, double a02, double a03, double a10, double a11, double a12, double a13, double a20, double a21, double a22, double a23, double a30, double a31, double a32, double a33) {
		this.internalMatrix[0][0] = a00;
		this.internalMatrix[0][1] = a01;
		this.internalMatrix[0][2] = a02;
		this.internalMatrix[0][3] = a03;
		this.internalMatrix[1][0] = a10;
		this.internalMatrix[1][1] = a11;
		this.internalMatrix[1][2] = a12;
		this.internalMatrix[1][3] = a13;
		this.internalMatrix[2][0] = a20;
		this.internalMatrix[2][1] = a21;
		this.internalMatrix[2][2] = a22;
		this.internalMatrix[2][3] = a23;
		this.internalMatrix[3][0] = a30;
		this.internalMatrix[3][1] = a31;
		this.internalMatrix[3][2] = a32;
		this.internalMatrix[3][3] = a33;
	}

	Matrix4(double[][] input) {
		this.internalMatrix = input;
	}

	public Matrix4 identity() {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				this.internalMatrix[i][j] = _identitydiagonal(i, j);
			}
		}
		// System.out.println(this.toString());
		return this;
	}

	private static double _identitydiagonal(int row, int column) {
		return row == column ? 1.0 : 0.0;
	}

	/*
	 * @Source:
	 * https://stackoverflow.com/questions/18404890/how-to-build-perspective-
	 * projection-matrix-no-api
	 */

	public Matrix4 perspective(float fov, float aspectRatio, float zNear, float zFar) {
		if (fov <= 0 || aspectRatio == 0) {
			return identity();
		}
		float frustumDepth = zFar - zNear;
		float oneOverDepth = 1 / frustumDepth;
		float halfTanFov = (float) Math.tan(Math.toRadians(fov * 0.5));

		this.internalMatrix[0][0] = 1.0 / (halfTanFov * aspectRatio);
		this.internalMatrix[0][1] = 0.0;
		this.internalMatrix[0][2] = 0.0;
		this.internalMatrix[0][3] = 0.0;
		this.internalMatrix[1][0] = 0.0;
		this.internalMatrix[1][1] = 1.0 / halfTanFov;
		this.internalMatrix[1][2] = 0.0;
		this.internalMatrix[1][3] = 0.0;
		this.internalMatrix[2][0] = 0.0;
		this.internalMatrix[2][1] = 0.0;
		this.internalMatrix[2][2] = -((zFar + zNear) * oneOverDepth);
		this.internalMatrix[2][3] = -((2.0 * zNear * zFar) / frustumDepth);
		this.internalMatrix[3][0] = 0.0;
		this.internalMatrix[3][1] = 0.0;
		this.internalMatrix[3][2] = -1.0;
		this.internalMatrix[3][3] = 0.0;
		return this;
	}

	public Matrix4 rotate(float radians, Vector3 vertex) {
		float temp1 = (float) vertex.getX();
		float temp2 = (float) vertex.getY();
		float temp3 = (float) vertex.getZ();

		return rotation(radians, temp1, temp2, temp3);
	}

	/*
	 * @Source:
	 * http://www.codinglabs.net/article_world_view_projection_matrix.aspx
	 */
	public Matrix4 rotateX(float theta) {
		this.internalMatrix[0][0] = 1.0;
		this.internalMatrix[0][1] = 0.0;
		this.internalMatrix[0][2] = 0.0;
		this.internalMatrix[0][3] = 0.0;
		this.internalMatrix[1][0] = 0.0;
		this.internalMatrix[1][1] = Math.cos(theta);
		this.internalMatrix[1][2] = -Math.sin(theta);
		this.internalMatrix[1][3] = 0.0;
		this.internalMatrix[2][0] = 0.0;
		this.internalMatrix[2][1] = Math.sin(theta);
		this.internalMatrix[2][2] = Math.cos(theta);
		this.internalMatrix[2][3] = 0.0;
		this.internalMatrix[3][0] = 0.0;
		this.internalMatrix[3][1] = 0.0;
		this.internalMatrix[3][2] = 0.0;
		this.internalMatrix[3][3] = 1.0;
		return this;
	}

	/*
	 * @Source:
	 * http://www.codinglabs.net/article_world_view_projection_matrix.aspx
	 */
	public Matrix4 rotateY(float theta) {
		this.internalMatrix[0][0] = Math.cos(theta);
		this.internalMatrix[0][1] = 0.0;
		this.internalMatrix[0][2] = Math.sin(theta);
		this.internalMatrix[0][3] = 0.0;
		this.internalMatrix[1][0] = 0.0;
		this.internalMatrix[1][1] = 1.0;
		this.internalMatrix[1][2] = 0.0;
		this.internalMatrix[1][3] = 0.0;
		this.internalMatrix[2][0] = -Math.sin(theta);
		this.internalMatrix[2][1] = 0.0;
		this.internalMatrix[2][2] = Math.cos(theta);
		this.internalMatrix[2][3] = 0.0;
		this.internalMatrix[3][0] = 0.0;
		this.internalMatrix[3][1] = 0.0;
		this.internalMatrix[3][2] = 0.0;
		this.internalMatrix[3][3] = 1.0;
		return this;
	}

	/*
	 * @Source:
	 * http://www.codinglabs.net/article_world_view_projection_matrix.aspx
	 */
	public Matrix4 rotateZ(float theta) {
		this.internalMatrix[0][0] = Math.cos(theta);
		this.internalMatrix[0][1] = -Math.sin(theta);
		this.internalMatrix[0][2] = 0.0;
		this.internalMatrix[0][3] = 0.0;
		this.internalMatrix[1][0] = Math.sin(theta);
		this.internalMatrix[1][1] = Math.cos(theta);
		this.internalMatrix[1][2] = 0.0;
		this.internalMatrix[1][3] = 0.0;
		this.internalMatrix[2][0] = 0.0;
		this.internalMatrix[2][1] = 0.0;
		this.internalMatrix[2][2] = 1.0;
		this.internalMatrix[2][3] = 0.0;
		this.internalMatrix[3][0] = 0.0;
		this.internalMatrix[3][1] = 0.0;
		this.internalMatrix[3][2] = 0.0;
		this.internalMatrix[3][3] = 1.0;
		return this;
	}

	/*
	 * @Title: 5.2 The normalized matrix for rotations about the origin
	 * 
	 * @Source:
	 * https://sites.google.com/site/glennmurray/Home/rotation-matrices-
	 * and-formulas/rotation-about-an-arbitrary-axis-in-3-dimensions
	 */
	public Matrix4 rotation(float radians, float x, float y, float z) {
		float cos = (float) Math.cos(radians);
		float sin = (float) Math.sin(radians);
		float C = 1.0F - cos;
		float xy = x * y;
		float xz = x * z;
		float yz = y * z;
		this.internalMatrix[0][0] = (cos + x * x * C);
		this.internalMatrix[0][1] = (xy * C - z * sin);
		this.internalMatrix[0][2] = (xz * C + y * sin);
		this.internalMatrix[0][3] = 0.0F;
		this.internalMatrix[1][0] = (xy * C + z * sin);
		this.internalMatrix[1][1] = (cos + y * y * C);
		this.internalMatrix[1][2] = (yz * C - x * sin);
		this.internalMatrix[1][3] = 0.0F;
		this.internalMatrix[2][0] = (xz * C - y * sin);
		this.internalMatrix[2][1] = (yz * C + x * sin);
		this.internalMatrix[2][2] = (cos + z * z * C);
		this.internalMatrix[2][3] = 0.0F;
		this.internalMatrix[3][0] = 0.0F;
		this.internalMatrix[3][1] = 0.0F;
		this.internalMatrix[3][2] = 0.0F;
		this.internalMatrix[3][3] = 1.0F;
		return this;
	}

	/*
	 * 3D Coordinate axes rotation matrices Source:
	 * https://sites.google.com/site/glennmurray/Home/rotation-matrices-and-
	 * formulas/rotation-about-an-arbitrary-axis-in-3-dimensions
	 */
	public Matrix4 rotateXYZ(float alpha, float beta, float gamma) {
		this.internalMatrix[0][0] = Math.cos(beta) * Math.cos(gamma);
		this.internalMatrix[0][1] = -Math.cos(beta) * Math.sin(gamma);
		this.internalMatrix[0][2] = Math.sin(beta);
		this.internalMatrix[0][3] = 0.0;
		this.internalMatrix[1][0] = (Math.cos(alpha) * Math.sin(gamma)) + (Math.sin(alpha) * Math.sin(beta) * Math.cos(gamma));
		this.internalMatrix[1][1] = (Math.cos(alpha) * Math.cos(gamma)) - (Math.sin(alpha) * Math.sin(beta) * Math.sin(gamma));
		this.internalMatrix[1][2] = -(Math.sin(alpha) * Math.cos(beta));
		this.internalMatrix[1][3] = 0.0;
		this.internalMatrix[2][0] = (Math.sin(alpha) * Math.sin(gamma)) - (Math.cos(alpha) * Math.sin(beta) * Math.cos(gamma));
		this.internalMatrix[2][1] = (Math.sin(alpha) * Math.cos(gamma)) + (Math.cos(alpha) * Math.sin(beta) * Math.sin(gamma));
		this.internalMatrix[2][2] = Math.cos(alpha) * Math.cos(beta);
		this.internalMatrix[2][3] = 0.0;
		this.internalMatrix[3][0] = 0.0;
		this.internalMatrix[3][1] = 0.0;
		this.internalMatrix[3][2] = 0.0;
		this.internalMatrix[3][3] = 1.0;
		return this;
	}

	/*
	 * 3D Coordinate axes rotation matrices Source:
	 * https://sites.google.com/site/glennmurray/Home/rotation-matrices-and-
	 * formulas/rotation-about-an-arbitrary-axis-in-3-dimensions
	 */
	public Matrix4 rotateZYX(float alpha, float beta, float gamma) {
		this.internalMatrix[0][0] = Math.cos(beta) * Math.cos(gamma);
		this.internalMatrix[0][1] = (Math.cos(gamma) * Math.sin(alpha) * Math.sin(beta)) - (Math.cos(alpha) * Math.sin(gamma));
		this.internalMatrix[0][2] = (Math.cos(alpha) * Math.cos(gamma) * Math.sin(beta)) + (Math.sin(alpha) * Math.sin(gamma));
		this.internalMatrix[0][3] = 0.0;
		this.internalMatrix[1][0] = Math.cos(beta) * Math.sin(gamma);
		this.internalMatrix[1][1] = (Math.cos(alpha) * Math.cos(gamma)) - (Math.sin(alpha) * Math.sin(beta) * Math.sin(gamma));
		this.internalMatrix[1][2] = (-Math.cos(gamma) * Math.sin(alpha)) + (Math.cos(alpha) * Math.sin(beta) * Math.sin(gamma));
		this.internalMatrix[1][3] = 0.0;
		this.internalMatrix[2][0] = -Math.sin(beta);
		this.internalMatrix[2][1] = Math.cos(beta) * Math.sin(alpha);
		this.internalMatrix[2][2] = Math.cos(alpha) * Math.cos(beta);
		this.internalMatrix[2][3] = 0.0;
		this.internalMatrix[3][0] = 0.0;
		this.internalMatrix[3][1] = 0.0;
		this.internalMatrix[3][2] = 0.0;
		this.internalMatrix[3][3] = 1.0;
		return this;
	}

	public Matrix4 translate(Vector3 position) {
		return translate(position.getX(), position.getY(), position.getZ());
	}

	/*
	 * @Source:
	 * http://www.codinglabs.net/article_world_view_projection_matrix.aspx
	 */
	public Matrix4 translate(double x, double y, double z) {
		this.internalMatrix[0][3] = (this.internalMatrix[0][0] * x + this.internalMatrix[0][1] * y + this.internalMatrix[0][2] * z + this.internalMatrix[0][3]);
		this.internalMatrix[1][3] = (this.internalMatrix[1][0] * x + this.internalMatrix[1][1] * y + this.internalMatrix[1][2] * z + this.internalMatrix[1][3]);
		this.internalMatrix[2][3] = (this.internalMatrix[2][0] * x + this.internalMatrix[2][1] * y + this.internalMatrix[2][2] * z + this.internalMatrix[2][3]);
		this.internalMatrix[3][3] = (this.internalMatrix[3][0] * x + this.internalMatrix[3][1] * y + this.internalMatrix[3][2] * z + this.internalMatrix[3][3]);

		// this.internalMatrix[0][0] = 1.0;
		// this.internalMatrix[0][1] = 0.0;
		// this.internalMatrix[0][2] = 0.0;
		// this.internalMatrix[0][3] = x;

		// this.internalMatrix[1][0] = 0.0;
		// this.internalMatrix[1][1] = 1.0;
		// this.internalMatrix[1][2] = 0.0;
		// this.internalMatrix[1][3] = y;

		// this.internalMatrix[2][0] = 0.0;
		// this.internalMatrix[2][1] = 0.0;
		// this.internalMatrix[2][2] = 1.0;
		// this.internalMatrix[2][3] = z;

		// this.internalMatrix[3][0] = 0.0;
		// this.internalMatrix[3][1] = 0.0;
		// this.internalMatrix[3][2] = 0.0;
		// this.internalMatrix[3][3] = 1.0;

		// System.out.println(this.toString());
		return this;
	}

	public Matrix4 scale(double x) {
		return scale(x, x, x);
	}

	public Matrix4 scale(Vector3 v) {
		return scale(v.getX(), v.getY(), v.getZ());

	}

	public Matrix4 scale(double x, double y, double z) {

		this.internalMatrix[0][1] = 0.0;
		this.internalMatrix[0][2] = 0.0;
		this.internalMatrix[0][3] = 0.0;
		this.internalMatrix[1][0] = 0.0;
		this.internalMatrix[1][2] = 0.0;
		this.internalMatrix[1][3] = 0.0;
		this.internalMatrix[2][0] = 0.0;
		this.internalMatrix[2][1] = 0.0;
		this.internalMatrix[2][3] = 0.0;
		this.internalMatrix[3][0] = 0.0;
		this.internalMatrix[3][1] = 0.0;
		this.internalMatrix[3][2] = 0.0;

		this.internalMatrix[0][0] = x;
		this.internalMatrix[1][1] = y;
		this.internalMatrix[2][2] = z;
		this.internalMatrix[3][3] = 1.0D;
		return this;

	}

	public double[][] to2DArray() {
		return this.internalMatrix;
	}

	public double[] toArray() {
		double[] temp = new double[16];
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				temp[(i * 4) + j] = this.internalMatrix[i][j];
			}
		}
		return temp;
	}

	public Matrix4 mul(Matrix4 otherMatrix) {
		double[][] temp = otherMatrix.to2DArray();
		double[][] temp1 = new double[4][4];

		// System.out.println("Curr
		// Matrix=============================================");
		// System.out.println(this.toString());
		// System.out.println("Multiply by\nother
		// Matrix=============================================");
		// System.out.println(otherMatrix.toString());
		for (int i = 0; i < this.internalMatrix.length; i++) {
			for (int j = 0; j < temp[0].length; j++) {
				for (int k = 0; k < this.internalMatrix[0].length; k++) {
					temp1[i][j] += this.internalMatrix[i][k] * temp[k][j];
				}
			}
		}
		// System.out.println("Result Equals\nnew
		// Matrix=============================================");
		// System.out.println(otherMatrix.toString());
		this.internalMatrix = temp1;
		return this;
	}

	public Vector4 mul(Vector4 vector) {
		double x = this.internalMatrix[0][0] * vector.getX() + this.internalMatrix[0][1] * vector.getY() + this.internalMatrix[0][2] * vector.getZ() + this.internalMatrix[0][3] * vector.getW();
		double y = this.internalMatrix[1][0] * vector.getX() + this.internalMatrix[1][1] * vector.getY() + this.internalMatrix[1][2] * vector.getZ() + this.internalMatrix[1][3] * vector.getW();
		double z = this.internalMatrix[2][0] * vector.getX() + this.internalMatrix[2][1] * vector.getY() + this.internalMatrix[2][2] * vector.getZ() + this.internalMatrix[2][3] * vector.getW();
		double w = this.internalMatrix[3][0] * vector.getX() + this.internalMatrix[3][1] * vector.getY() + this.internalMatrix[3][2] * vector.getZ() + this.internalMatrix[3][3] * vector.getW();
		return new Vector4(x, y, z, w);
	}

	public Vector3 mulToVec3(Vector4 vector) {
		double x = this.internalMatrix[0][0] * vector.getX() + this.internalMatrix[0][1] * vector.getY() + this.internalMatrix[0][2] * vector.getZ() + this.internalMatrix[0][3] * vector.getW();
		double y = this.internalMatrix[1][0] * vector.getX() + this.internalMatrix[1][1] * vector.getY() + this.internalMatrix[1][2] * vector.getZ() + this.internalMatrix[1][3] * vector.getW();
		double z = this.internalMatrix[2][0] * vector.getX() + this.internalMatrix[2][1] * vector.getY() + this.internalMatrix[2][2] * vector.getZ() + this.internalMatrix[2][3] * vector.getW();
		return new Vector3(x, y, z);

	}

	public Matrix4 set(Matrix4 otherMatrix) {
		// System.out.println(otherMatrix);
		double[][] temp = otherMatrix.to2DArray();
		for (int i = 0; i < temp.length; i++) {
			for (int j = 0; j < temp[0].length; j++) {
				this.internalMatrix[i][j] = temp[i][j];
			}
		}
		// System.out.println(this.toString());

		return this;
	}

	@Override
	public String toString() {
		String s = "";
		String temp = "";
		String space = "      ";
		String temp1 = "";
		int x = 0;
		for (int i = 0; i < this.internalMatrix.length; i++) {
			for (int j = 0; j < this.internalMatrix[0].length; j++) {
				temp = Double.toString(this.internalMatrix[i][j]);
				if (temp.length() > 5)
					temp = temp.substring(0, 5);
				if (temp.length() == 5) {
					temp += "E";
				} else {
					temp += "F";
				}
				temp = (temp + space);
				if (temp.length() > 10) {
					temp = temp.substring(0, 10);
				}
				if (temp.length() < 10) {
					x = 10 - temp.length();
					for (int y = 0; y < x; y++) {
						temp1 += " ";
					}
					temp += temp1;
				}
				s += temp;
				temp = "";
				temp1 = "";
				x = 0;
			}
			s += "\n";
		}
		return s;
	}

}
