package thomas.hofmann;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Comparator;

public class Mesh {
	public ArrayList<Triangle> triangles;
	private ArrayList<Triangle> rotatedXTris;
	private ArrayList<Triangle> rotatedYTris;
	private ArrayList<Triangle> rotatedZTris;
	private ArrayList<Triangle> translatedTris;
	private ArrayList<Triangle> projectedTris;
	private ArrayList<Triangle> trisToDraw;
	private Camera cam;
	private Vector3D l1, l2;
	private Vector3D normal;
	private Vector3D tempV;
	private double x = 0.0;
	private double y = 0.0;
	private double z = 0.0;
	private double rotX = 0.0;
	private double rotY = 0.0;
	private double rotZ = 0.0;

	private boolean ccwVertex;
	public boolean fill = true;
	public boolean outLine = false;

	Comparator<Triangle> c;

	public Mesh(double[][][] vtcs, boolean ccwFaces, boolean ccwVertex) {
		this.ccwVertex=ccwVertex;
		if (ccwFaces) {
			c = new Comparator<Triangle>() {

				@Override
				public int compare(Triangle o1, Triangle o2) {
					return Double.compare(o1.vectors[0].xyz[2], o2.vectors[0].xyz[2]);
				}
			};
		} else {
			c = new Comparator<Triangle>() {

				@Override
				public int compare(Triangle o1, Triangle o2) {
					return Double.compare(o1.vectors[0].xyz[2], o2.vectors[0].xyz[2]);
				}
			};
		}
		l1 = new Vector3D();
		l2 = new Vector3D();
		normal = new Vector3D();
		tempV = new Vector3D();
		triangles = new ArrayList<Triangle>();
		rotatedXTris = new ArrayList<Triangle>();
		rotatedYTris = new ArrayList<Triangle>();
		rotatedZTris = new ArrayList<Triangle>();
		translatedTris = new ArrayList<Triangle>();
		projectedTris = new ArrayList<Triangle>();
		trisToDraw = new ArrayList<Triangle>();
		for (int i = 0; i < vtcs.length; i++) {
			triangles.add(new Triangle(vtcs[i]));
			rotatedXTris.add(new Triangle());
			rotatedYTris.add(new Triangle());
			rotatedZTris.add(new Triangle());
			translatedTris.add(new Triangle());
			projectedTris.add(new Triangle());
		}
	}

	public Mesh(ArrayList<Triangle> tris, boolean ccwFaces, boolean ccwVertex) {
		this.ccwVertex=ccwVertex;
		if (ccwFaces) {
			c = new Comparator<Triangle>() {

				@Override
				public int compare(Triangle o1, Triangle o2) {
					return Double.compare(o1.vectors[0].xyz[2], o2.vectors[0].xyz[2]);
				}
			};
		} else {
			c = new Comparator<Triangle>() {

				@Override
				public int compare(Triangle o1, Triangle o2) {
					return Double.compare(o1.vectors[0].xyz[2], o2.vectors[0].xyz[2]);
				}
			};
		}
		l1 = new Vector3D();
		l2 = new Vector3D();
		normal = new Vector3D();
		tempV = new Vector3D();
		triangles = new ArrayList<Triangle>();
		rotatedXTris = new ArrayList<Triangle>();
		rotatedYTris = new ArrayList<Triangle>();
		rotatedZTris = new ArrayList<Triangle>();
		translatedTris = new ArrayList<Triangle>();
		projectedTris = new ArrayList<Triangle>();
		trisToDraw = new ArrayList<Triangle>();
		for (int i = 0; i < tris.size(); i++) {
			triangles.add(tris.get(i));
			rotatedXTris.add(new Triangle());
			rotatedYTris.add(new Triangle());
			rotatedZTris.add(new Triangle());
			translatedTris.add(new Triangle());
			projectedTris.add(new Triangle());
		}
	}

	public void setCamera(Camera cam) {
		this.cam = cam;
	}

	public void translate(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public void rotate(double rotX, double rotY, double rotZ) {
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
	}

	public void render(Graphics g) {
		trisToDraw.clear();
		for (int i = 0; i < triangles.size(); i++) {
			rotateX(triangles.get(i), rotatedXTris.get(i));
			rotateY(rotatedXTris.get(i), rotatedYTris.get(i));
			rotateZ(rotatedYTris.get(i), rotatedZTris.get(i));
			translate(rotatedZTris.get(i), translatedTris.get(i));
			getNormal(translatedTris.get(i), normal);
			getVector(cam.camV, translatedTris.get(i).vectors[0], tempV);
			if(ccwVertex) {
				if (dotProduct(normal, tempV) < 0.0) {
					illumination(translatedTris.get(i), normal);
					project(translatedTris.get(i), projectedTris.get(i));
					trisToDraw.add(projectedTris.get(i));
				}
			}else
			{
				if (dotProduct(normal, tempV) > 0.0) {
					illumination(translatedTris.get(i), normal);
					project(translatedTris.get(i), projectedTris.get(i));
					trisToDraw.add(projectedTris.get(i));
				}
			}
		}
		trisToDraw.sort(c);
		for (int i = 0; i < trisToDraw.size(); i++) {
			drawTriangle(trisToDraw.get(i), g);
		}
	}

	private void illumination(Triangle t, Vector3D nV) {
		t.color = Math.abs(dotProduct(nV, cam.lightSource));
	}

	private void project(Vector3D src, Vector3D out) {
		out.xyz[0] = (((cam.foV * src.xyz[0]) / src.xyz[2]) + 1.0) * 0.5 * cam.width;
		out.xyz[1] = (((cam.aspectRatio * cam.foV * src.xyz[1]) / src.xyz[2]) + 1.0) * 0.5 * cam.height;
		out.xyz[2] = (src.xyz[2] * cam.zScale) - (cam.zNear * cam.zScale);
	}

	private void translate(Triangle src, Triangle out) {
		translate(src.vectors[0], out.vectors[0]);
		translate(src.vectors[1], out.vectors[1]);
		translate(src.vectors[2], out.vectors[2]);
	}

	private void translate(Vector3D src, Vector3D out) {
		out.xyz[0] = src.xyz[0] + x;
		out.xyz[1] = src.xyz[1] + y;
		out.xyz[2] = src.xyz[2] + z;
	}

	private void rotateX(Triangle src, Triangle out) {
		rotateX(src.vectors[0], out.vectors[0]);
		rotateX(src.vectors[1], out.vectors[1]);
		rotateX(src.vectors[2], out.vectors[2]);
	}

	private void rotateY(Triangle src, Triangle out) {
		rotateY(src.vectors[0], out.vectors[0]);
		rotateY(src.vectors[1], out.vectors[1]);
		rotateY(src.vectors[2], out.vectors[2]);
	}

	private void rotateZ(Triangle src, Triangle out) {
		rotateZ(src.vectors[0], out.vectors[0]);
		rotateZ(src.vectors[1], out.vectors[1]);
		rotateZ(src.vectors[2], out.vectors[2]);
	}

	private void rotateX(Vector3D src, Vector3D out) {
		out.xyz[0] = src.xyz[0];
		out.xyz[1] = (src.xyz[1] * Math.cos(rotX)) + (src.xyz[2] * Math.sin(rotX));
		out.xyz[2] = (src.xyz[1] * (-Math.sin(rotX))) + (src.xyz[2] * Math.cos(rotX));
	}

	private void rotateY(Vector3D src, Vector3D out) {
		out.xyz[0] = (src.xyz[0] * Math.cos(rotY)) + (src.xyz[2] * (-Math.sin(rotY)));
		out.xyz[1] = src.xyz[1];
		out.xyz[2] = (src.xyz[0] * Math.sin(rotY)) + (src.xyz[2] * Math.cos(rotY));
	}

	private void rotateZ(Vector3D src, Vector3D out) {
		out.xyz[0] = (src.xyz[0] * Math.cos(rotZ)) + (src.xyz[1] * Math.sin(rotZ));
		out.xyz[1] = (src.xyz[0] * (-Math.sin(rotZ))) + (src.xyz[1] * Math.cos(rotZ));
		out.xyz[2] = src.xyz[2];
	}

	private void project(Triangle src, Triangle out) {
		project(src.vectors[0], out.vectors[0]);
		project(src.vectors[1], out.vectors[1]);
		project(src.vectors[2], out.vectors[2]);
		out.color = src.color;
	}

	private void crossProduct(Vector3D v1, Vector3D v2, Vector3D nV) {
		nV.xyz[0] = v1.xyz[1] * v2.xyz[2] - v1.xyz[2] * v2.xyz[1];
		nV.xyz[1] = v1.xyz[2] * v2.xyz[0] - v1.xyz[0] * v2.xyz[2];
		nV.xyz[2] = v1.xyz[0] * v2.xyz[1] - v1.xyz[1] * v2.xyz[0];
	}

	private double dotProduct(Vector3D v1, Vector3D v2) {
		return v1.xyz[0] * v2.xyz[0] + v1.xyz[1] * v2.xyz[1] + v1.xyz[2] * v2.xyz[2];
	}

	private void getVector(Vector3D p1, Vector3D p2, Vector3D v) {
		v.xyz[0] = p2.xyz[0] - p1.xyz[0];
		v.xyz[1] = p2.xyz[1] - p1.xyz[1];
		v.xyz[2] = p2.xyz[2] - p1.xyz[2];
	}

	private void getNormal(Triangle t, Vector3D nV) {
		l1.xyz[0] = t.vectors[1].xyz[0] - t.vectors[0].xyz[0];
		l1.xyz[1] = t.vectors[1].xyz[1] - t.vectors[0].xyz[1];
		l1.xyz[2] = t.vectors[1].xyz[2] - t.vectors[0].xyz[2];

		l2.xyz[0] = t.vectors[2].xyz[0] - t.vectors[0].xyz[0];
		l2.xyz[1] = t.vectors[2].xyz[1] - t.vectors[0].xyz[1];
		l2.xyz[2] = t.vectors[2].xyz[2] - t.vectors[0].xyz[2];

		crossProduct(l1, l2, nV);
		nV.normalizeV();
	}

	private void drawTriangle(Triangle t, Graphics g) {
		if (fill) {
			g.setColor(new Color((int) (t.color * 150), (int) (t.color * 150), (int) (t.color * 150)));
			g.fillPolygon(new int[] { (int) t.vectors[0].xyz[0], (int) t.vectors[1].xyz[0], (int) t.vectors[2].xyz[0] },
					new int[] { (int) t.vectors[0].xyz[1], (int) t.vectors[1].xyz[1], (int) t.vectors[2].xyz[1] }, 3);
		}
		if (outLine) {
			g.setColor(Color.black);
			g.drawPolyline(new int[] { (int) t.vectors[0].xyz[0], (int) t.vectors[1].xyz[0], (int) t.vectors[2].xyz[0] },
					new int[] { (int) t.vectors[0].xyz[1], (int) t.vectors[1].xyz[1], (int) t.vectors[2].xyz[1] }, 3);
		}
	}
}