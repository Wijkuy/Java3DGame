import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Sphere extends Tetrahedron {

	public Sphere() {
		this.setPosition(0, 0, 0);
		this.setRotation(10, 10, 0);
		this.setScale(10f);
		this.tri = inflate(this.getTris());
	}

	public Sphere(List<Tri> mesh) {
		super(mesh);
		// TODO Auto-generated constructor stub
	}

	private List<Tri> inflate(List<Tri> tris) {
		List<Tri> result = new ArrayList<>();
		for (Tri t : this.tri) {
			Vector3 m1 = new Vector3((t.getV1().getX() + t.getV2().getX()) / 2, (t.getV1().getY() + t.getV2().getY()) / 2, (t.getV1().getZ() + t.getV2().getZ()) / 2);
			Vector3 m2 = new Vector3((t.getV2().getX() + t.getV3().getX()) / 2, (t.getV2().getY() + t.getV3().getY()) / 2, (t.getV2().getZ() + t.getV3().getZ()) / 2);
			Vector3 m3 = new Vector3((t.getV1().getX() + t.getV3().getX()) / 2, (t.getV1().getY() + t.getV3().getY()) / 2, (t.getV1().getZ() + t.getV3().getZ()) / 2);
			result.add(new Tri(t.getV1(), m1, m3, t.getColor()));
			result.add(new Tri(t.getV2(), m1, m2, t.getColor()));
			result.add(new Tri(t.getV3(), m2, m3, t.getColor()));
			result.add(new Tri(m1, m2, m3, t.getColor()));
		}
		for (Tri t : result) {
			for (Vector3 v : new Vector3[] { t.getV1(), t.getV2(), t.getV3() }) {
				double l = Math.sqrt(v.getX() * v.getX() + v.getY() * v.getY() + v.getZ() * v.getZ()) / Math.sqrt(30000);
				v.setX(v.getX() / l);
				v.setY(v.getY() / l);
				v.setZ(v.getZ() / l);
			}
		}
		return result;
	}

}
