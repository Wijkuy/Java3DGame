import java.util.Arrays;

public class Transformation {
	private final Matrix4 projectionMatrix;
	private final Matrix4 worldMatrix;
	private final Matrix4 viewMatrix;
	private final Matrix4 tempMatrix;
	private final Matrix4 modelViewProjectionMatrix;

	public Transformation() {
		this.projectionMatrix = new Matrix4();
		this.worldMatrix = new Matrix4();
		this.tempMatrix = new Matrix4();
		this.viewMatrix = new Matrix4();
		this.modelViewProjectionMatrix = new Matrix4();
	}

	public Matrix4 getProjectionMatrix() {
		return this.projectionMatrix;
	}

	public Matrix4 updateProjectionMatrix(float fov, float width, float height, float zNear, float zFar) {
		float aspectRatio = width / height;
		this.projectionMatrix.identity();
		this.projectionMatrix.perspective(fov, aspectRatio, zNear, zFar);
		return this.projectionMatrix;
	}

	public Vector3 projectedPoint(Vector3 point) {
		Vector4 temp = this.worldMatrix.mul(new Vector4(point));
		//System.out.println(this.projectionMatrix);
		temp.setX(temp.getX() / temp.getW());
		temp.setY(temp.getY() / temp.getW());
		temp.setZ(temp.getZ() / temp.getW());
		if (temp.getW() != 0) {
			return new Vector3(temp);
		}
		return new Vector3(0, 0, 0);
	}

	public Matrix4 getViewMatrix() {
		return this.worldMatrix;
	}

	public Matrix4 getViewMatrix(Camera camera) {
		Vector3 pos = camera.getPosition();
		Vector3 rot = camera.getRotation();

		this.viewMatrix.identity();
		this.tempMatrix.identity();

		this.tempMatrix.rotate((float) Math.toRadians(rot.getX()), new Vector3(1, 0, 0));
		this.viewMatrix.mul(this.tempMatrix);

		this.tempMatrix.rotate((float) Math.toRadians(rot.getY()), new Vector3(0, 1, 0));
		this.viewMatrix.mul(this.tempMatrix);

		this.tempMatrix.translate(-pos.getX(), -pos.getY(), -pos.getZ());
		this.viewMatrix.mul(this.tempMatrix);
		// System.out.println("view test");
		// System.out.println(viewMatrix.toString());
		this.tempMatrix.identity();

		return this.viewMatrix;
	}

	public Matrix4 getWorldMatrix(Mesh mesh, Matrix4 viewMatrix) {
		Vector3 pos = mesh.getPosition();
		Vector3 rot = mesh.getRotation();

		this.worldMatrix.identity();
		this.tempMatrix.identity();

		this.tempMatrix.identity();
		this.tempMatrix.translate(pos.getX(), pos.getY(), pos.getZ());
		this.worldMatrix.mul(this.tempMatrix);

		this.tempMatrix.rotateX((float) Math.toRadians(-rot.getX()));
		this.worldMatrix.mul(this.tempMatrix);

		this.tempMatrix.rotateY((float) Math.toRadians(-rot.getY()));
		this.worldMatrix.mul(this.tempMatrix);

		this.tempMatrix.rotateZ((float) Math.toRadians(-rot.getZ()));
		this.worldMatrix.mul(this.tempMatrix);

		this.tempMatrix.scale(mesh.getScale());
		this.worldMatrix.mul(this.tempMatrix);

		// this.tempMatrix.set(this.projectionMatrix);
		this.tempMatrix.set(this.viewMatrix);
		this.worldMatrix.mul(this.tempMatrix);

		this.tempMatrix.identity();

		return this.worldMatrix;
	}

}
