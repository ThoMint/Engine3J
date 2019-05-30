package thomas.hofmann;

public class Camera {
	public double width = 0.0;
	public double height = 0.0;
	public double foV = 0.0;
	public double zNear = 0.0;
	public double zFar = 0.0;

	public double aspectRatio = 0.0;
	public double zScale = 0.0;

	public Camera(int width, int height, double foV, double zNear, double zFar) {
		super();
		this.width = width;
		this.height = height;
		this.foV = foV;
		this.zNear = zNear;
		this.zFar = zFar;
		init();
	}

	private void init() {
		foV = (Math.PI / 180.0) * foV;
		foV = 1.0 / (Math.tan(foV / 2.0));
		aspectRatio = ((double) width) / ((double) height);
		zScale = zFar / (zFar - zNear);
	}
}