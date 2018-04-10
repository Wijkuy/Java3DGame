import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Mesh {

	protected List<Tri> tri = new ArrayList<>();

	protected final Vector3 position;

	protected float scale;

	protected final Vector3 rotation;

	public Mesh() {
		this.position = new Vector3(200,200,100);
		this.scale = 100;
		this.rotation = new Vector3(70, 80, 70);
		
		
	}

	public Mesh(List<Tri> mesh) {
		this();
		this.tri = mesh;
	}

	public Vector3 getPosition() {
		return this.position;
	}

	public void setPosition(float x, float y, float z) {
		this.position.setX(x);
		this.position.setY(y);
		this.position.setZ(z);
	}

	public float getScale() {
		return this.scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}

	public Vector3 getRotation() {
		return this.rotation;
	}

	public void setRotation(float x, float y, float z) {
		this.rotation.setX(x);
		this.rotation.setY(y);
		this.rotation.setZ(z);
	}

	public List<Tri> getTris() {
		return this.tri;
	}

	public void setTris(List<Tri> tri) {
		this.tri = tri;
	}
}