package thomas.hofmann;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Mesh {
	public ArrayList<Triangle> triangles;
	private ArrayList<Triangle> rotatedXTris;
	private ArrayList<Triangle> rotatedYTris;
	private ArrayList<Triangle> rotatedZTris;
	private ArrayList<Triangle> projectedTris;
	private Camera cam;
	private double x=0.0;
	private double y=0.0;
	private double z=0.0;
	private double rotX=0.0;
	private double rotY=0.0;
	private double rotZ=0.0;

	public Mesh(double[][][] vtcs) {
		triangles = new ArrayList<Triangle>();
		rotatedXTris = new ArrayList<Triangle>();
		rotatedYTris = new ArrayList<Triangle>();
		rotatedZTris = new ArrayList<Triangle>();
		projectedTris = new ArrayList<Triangle>();
		for (int i = 0; i < vtcs.length; i++) {
			triangles.add(new Triangle(vtcs[i]));
			rotatedXTris.add(new Triangle());
			rotatedYTris.add(new Triangle());
			rotatedZTris.add(new Triangle());
			projectedTris.add(new Triangle());
		}
	}
	
	public void setCamera(Camera cam) {
		this.cam=cam;
	}
	
	public void translate(double x, double y, double z) {
		this.x=x;
		this.y=y;
		this.z=z;
	}
	
	public void rotate(double rotX, double rotY, double rotZ) {
		this.rotX=rotX;
		this.rotY=rotY;
		this.rotZ=rotZ;
	}
	
	public void render(Graphics g) {
		for(int i=0;i<triangles.size();i++) {
			rotateX(triangles.get(i), rotatedXTris.get(i));
			rotateY(rotatedXTris.get(i), rotatedYTris.get(i));
			rotateZ(rotatedYTris.get(i), rotatedZTris.get(i));
			project(rotatedZTris.get(i), projectedTris.get(i));
			drawTriangle(projectedTris.get(i), g);
		}
	}
	
	private void project(Vector3D src, Vector3D out) {
		out.xyz[0] = src.xyz[0] + x;
		out.xyz[1] = src.xyz[1] + y;
		out.xyz[2] = src.xyz[2] + z;
		
		out.xyz[0] = (((cam.foV * out.xyz[0]) / out.xyz[2]) + 1.0) * 0.5 * cam.width;
		out.xyz[1] = (((cam.aspectRatio * cam.foV * out.xyz[1]) / out.xyz[2]) + 1.0) * 0.5 * cam.height;
		out.xyz[2] = (out.xyz[2] * cam.zScale) - (cam.zNear * cam.zScale);
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
		out.xyz[1] = (src.xyz[1]*Math.cos(rotX))+(src.xyz[2]*Math.sin(rotX));
		out.xyz[2] = (src.xyz[1]*(-Math.sin(rotX)))+(src.xyz[2]*Math.cos(rotX));
	}
	
	private void rotateY(Vector3D src, Vector3D out) {
		out.xyz[0] = (src.xyz[0]*Math.cos(rotY))+(src.xyz[2]*(-Math.sin(rotY)));
		out.xyz[1] = src.xyz[1];
		out.xyz[2] = (src.xyz[0]*Math.sin(rotY))+(src.xyz[2]*Math.cos(rotY));
	}
	
	private void rotateZ(Vector3D src, Vector3D out) {
		out.xyz[0] = (src.xyz[0]*Math.cos(rotZ))+(src.xyz[1]*Math.sin(rotZ));
		out.xyz[1] = (src.xyz[0]*(-Math.sin(rotZ)))+(src.xyz[1]*Math.cos(rotZ));
		out.xyz[2] = src.xyz[2];
	}

	private void project(Triangle src, Triangle out) {
		project(src.vectors[0], out.vectors[0]);
		project(src.vectors[1], out.vectors[1]);
		project(src.vectors[2], out.vectors[2]);
	}
	
	private void drawTriangle(Triangle t, Graphics g) {
		g.setColor(Color.black);
		g.drawLine((int) t.vectors[0].xyz[0], (int) t.vectors[0].xyz[1], (int) t.vectors[1].xyz[0], (int) t.vectors[1].xyz[1]);
		g.drawLine((int) t.vectors[0].xyz[0], (int) t.vectors[0].xyz[1], (int) t.vectors[2].xyz[0], (int) t.vectors[2].xyz[1]);
		g.drawLine((int) t.vectors[1].xyz[0], (int) t.vectors[1].xyz[1], (int) t.vectors[2].xyz[0], (int) t.vectors[2].xyz[1]);
	}
}