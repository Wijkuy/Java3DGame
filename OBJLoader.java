
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class OBJLoader {

	List<Vector3> vertices = new ArrayList<>();
	List<Tri> tris = new ArrayList<>();

	public void loadMesh(String fileName) throws Exception {
		List<String> lines = Utils.readAllLines(fileName);
		for (String line : lines) {
			String[] tokens = line.split("\\s+");
			switch (tokens[0]) {
				case "v":
					// Geometric vertex
					Vector3 vec3f = new Vector3(Float.parseFloat(tokens[1])*30, Float.parseFloat(tokens[2])*30, Float.parseFloat(tokens[3])*30);
					this.vertices.add(vec3f);
					break;
				default:
					// Ignore other lines
					break;
			}
		}
		reorderLists(this.vertices);
		createTris(this.vertices);
	}

	private void createTris(List<Vector3> ver3f) {
		Vector3 temp1 = null;
		Vector3 temp2 = null;
		Vector3 temp3 = null;
		for (int i = 0; i < ver3f.size(); i++) {
			if (i % 3 == 0)
				temp1 = ver3f.get(i);
			if (i % 3 == 1)
				temp2 = ver3f.get(i);
			if (i % 3 == 2) {
				temp3 = ver3f.get(i);
				this.tris.add(new Tri(temp1, temp2, temp3, Color.CYAN));
			}
		}
	}

	private void reorderLists(List<Vector3> posList) {

		List<Integer> indices = new ArrayList();
		// Create position array in the order it has been declared
		float[] posArr = new float[posList.size() * 3];
		int i = 0;
		for (Vector3 pos : posList) {
			posArr[i * 3] = (float) pos.getX();
			posArr[i * 3 + 1] = (float) pos.getY();
			posArr[i * 3 + 2] = (float) pos.getZ();
			i++;
		}
		int[] indicesArr = new int[indices.size()];
		indicesArr = indices.stream().mapToInt((Integer v) -> v).toArray();
	}
}
