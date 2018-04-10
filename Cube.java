import java.awt.Color;
import java.util.List;

public class Cube extends Mesh {

	public Cube() {

		// x
		this.tri.add(new Tri(new Vector3(1, -1, 1), new Vector3(1, -1, -1), new Vector3(1, 1, -1), new Color(110, 224, 72)));
		this.tri.add(new Tri(new Vector3(1, -1, 1), new Vector3(1, 1, 1), new Vector3(1, 1, -1), new Color(110, 224, 72)));

		this.tri.add(new Tri(new Vector3(-1, -1, 1), new Vector3(-1, -1, -1), new Vector3(-1, 1, -1), new Color(150, 64, 172)));
		this.tri.add(new Tri(new Vector3(-1, -1, 1), new Vector3(-1, 1, 1), new Vector3(-1, 1, -1), new Color(150, 64, 172)));

		// y
		this.tri.add(new Tri(new Vector3(1, 1, 1), new Vector3(-1, 1, 1), new Vector3(1, 1, -1), new Color(0, 24, 72)));
		this.tri.add(new Tri(new Vector3(-1, 1, 1), new Vector3(-1, 1, -1), new Vector3(1, 1, -1), new Color(0, 24, 72)));

		this.tri.add(new Tri(new Vector3(1, -1, 1), new Vector3(-1, -1, 1), new Vector3(1, -1, -1), new Color(0, 24, 72)));
		this.tri.add(new Tri(new Vector3(-1, -1, 1), new Vector3(-1, -1, -1), new Vector3(1, -1, -1), new Color(0, 24, 72)));

		// z
		this.tri.add(new Tri(new Vector3(1, -1, 1), new Vector3(-1, -1, 1), new Vector3(1, 1, 1), Color.BLACK));
		this.tri.add(new Tri(new Vector3(1, 1, 1), new Vector3(-1, 1, 1), new Vector3(-1, -1, 1), Color.BLACK));

		this.tri.add(new Tri(new Vector3(1, -1, -1), new Vector3(-1, -1, -1), new Vector3(1, 1, -1), new Color(58, 99, 55)));
		this.tri.add(new Tri(new Vector3(1, 1, -1), new Vector3(-1, 1, -1), new Vector3(-1, -1, -1), new Color(58, 99, 55)));

		this.setPosition(0, 0, 0);
		this.setRotation(0, 0, 0);
		this.setScale(10f);

	}

	@Override
	public void setPosition(float x, float y, float z) {
		this.position.setX(x);
		this.position.setY(y);
		this.position.setZ(z);
		this.position.mul(getScale());
	}

	public Cube(List<Tri> mesh) {
		super(mesh);
		// TODO Auto-generated constructor stub
	}

}
