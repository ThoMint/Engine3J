package thomas.hofmann;

public class Triangle {
	public Vector3D[] vectors;

	public Triangle() {
		vectors = new Vector3D[3];
		vectors[0] = new Vector3D();
		vectors[1] = new Vector3D();
		vectors[2] = new Vector3D();
	}

	public Triangle(double[][] vtcs) {
		vectors = new Vector3D[3];
		vectors[0] = new Vector3D();
		vectors[1] = new Vector3D();
		vectors[2] = new Vector3D();
		vectors[0].xyz = vtcs[0];
		vectors[1].xyz = vtcs[1];
		vectors[2].xyz = vtcs[2];
	}

	public void setVtcs(double[][] vtcs) {
		vectors[0].xyz = vtcs[0];
		vectors[1].xyz = vtcs[1];
		vectors[2].xyz = vtcs[2];
	}
}