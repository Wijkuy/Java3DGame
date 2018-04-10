import java.awt.Color;
import java.util.List;

public class Plane extends Mesh {

	public Plane() {
		this.tri.add(new Tri(new Vector3(1, 0, 1), new Vector3(-1, 0, 1), new Vector3(1, 0, -1), Color.WHITE));
		this.tri.add(new Tri(new Vector3(-1, 0, -1), new Vector3(1, 0, -1), new Vector3(-1, 0, 1), Color.YELLOW));
		this.setPosition(1000, -300, 1000);
		this.setRotation(0, 0, 0);
		this.setScale(200f);
	}

	public Plane(List<Tri> mesh) {
		super(mesh);
		// TODO Auto-generated constructor stub
	}

}
