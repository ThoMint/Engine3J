package thomas.hofmann;

public class Vector3D {
	public double[] xyz;

	public Vector3D(double x, double y, double z) {
		xyz = new double[3];
		xyz[0] = x;
		xyz[1] = y;
		xyz[2] = z;
	}

	public Vector3D() {
		xyz = new double[] { 0.0, 0.0, 0.0 };
		xyz[0] = 0.0;
		xyz[1] = 0.0;
		xyz[2] = 0.0;
	}

	public void set(double x, double y, double z) {
		xyz[0] = x;
		xyz[1] = y;
		xyz[2] = z;
	}

	public void normalizeV() {
		double length = length();
		xyz[0] = xyz[0] / length;
		xyz[1] = xyz[1] / length;
		xyz[2] = xyz[2] / length;
	}

	public double length() {
		return Math.sqrt(xyz[0] * xyz[0] + xyz[1] * xyz[1] + xyz[2] * xyz[2]);
	}

	public double getX() {
		return xyz[0];
	}

	public double getY() {
		return xyz[1];
	}

	public double getZ() {
		return xyz[2];
	}
}