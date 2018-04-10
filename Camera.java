public class Camera {

	private final Vector3 position;

	private final Vector3 rotation;

	public Camera() {
		this.position = new Vector3(10, 0, 0);
		this.rotation = new Vector3(0, 0, 0);
	}

	public Camera(Vector3 position, Vector3 rotation) {
		this.position = position;
		this.rotation = rotation;
	}

	public Vector3 getPosition() {
		return this.position;
	}

	public void setPosition(float x, float y, float z) {
		this.position.setX(x);
		this.position.setY(y);
		this.position.setZ(z);
	}

	public void movePosition(float offsetX, float offsetY, float offsetZ) {
		if (offsetZ != 0) {
			this.position.setX(this.position.getX() + (Math.sin(Math.toRadians(this.rotation.getY())) * -1.0f * offsetZ));
			this.position.setZ(this.position.getZ() + (Math.cos(Math.toRadians(this.rotation.getY())) * offsetZ));
		}
		if (offsetX != 0) {
			this.position.setX(this.position.getX() + (Math.sin(Math.toRadians(this.rotation.getY() - 90)) * -1.0f * offsetX));
			this.position.setZ(this.position.getZ() + (Math.cos(Math.toRadians(this.rotation.getY() - 90)) * offsetX));
		}
		this.position.setY(this.position.getY() + offsetY);
	}

	public Vector3 getRotation() {
		return this.rotation;
	}

	public void setRotation(float x, float y, float z) {
		this.rotation.setX(x);
		this.rotation.setY(y);
		this.rotation.setZ(z);
	}

	public void moveRotation(float offsetX, float offsetY, float offsetZ) {
		this.rotation.setX(this.rotation.getX() + offsetX);
		this.rotation.setY(this.rotation.getY() + offsetY);
		this.rotation.setZ(this.rotation.getZ() + offsetZ);

	}
}
