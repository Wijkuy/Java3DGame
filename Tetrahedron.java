import java.awt.Color;
import java.util.List;

public class Tetrahedron extends Mesh {

	public Tetrahedron() {
		this.tri.add(new Tri(new Vector3(1, 1, 1), new Vector3(-1, -1, 1), new Vector3(-1, 1, -1), Color.WHITE));
		this.tri.add(new Tri(new Vector3(1, 1, 1), new Vector3(-1, -1, 1), new Vector3(1, -1, -1), Color.RED));
		this.tri.add(new Tri(new Vector3(-1, 1, -1), new Vector3(1, -1, -1), new Vector3(1, 1, 1), Color.GREEN));
		this.tri.add(new Tri(new Vector3(-1, 1, -1), new Vector3(1, -1, -1), new Vector3(-1, -1, 1), Color.BLUE));
	}

	public Tetrahedron(List<Tri> mesh) {
		super(mesh);
		// TODO Auto-generated constructor stub
	}

}
