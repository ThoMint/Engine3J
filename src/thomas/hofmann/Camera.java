package thomas.hofmann;

public class Camera {
	public double width = 0.0;
	public double height = 0.0;
	public double foV = 0.0;
	public double zNear = 0.0;
	public double zFar = 0.0;

	public Vector3D camV;
	public Vector3D lightSource;

	public double aspectRatio = 0.0;
	public double zScale = 0.0;

	public Camera(double foV, double zNear, double zFar) {
		super();
		camV = new Vector3D(0.0, 0.0, 0.0);
		lightSource = new Vector3D(0.0, 0.0, 1.0);
		lightSource.normalizeV();
		this.foV = foV;
		this.zNear = zNear;
		this.zFar = zFar;
	}

	public void init(int width, int height) {
		this.width = width;
		this.height = height;
		
		foV = (Math.PI / 180.0) * foV;
		foV = 1.0 / (Math.tan(foV / 2.0));
		aspectRatio = ((double) width) / ((double) height);
		zScale = zFar / (zFar - zNear);
	}
}