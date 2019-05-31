package thomas.hofmann;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class OBJLoader {

	@SuppressWarnings("resource")
	public static Mesh loadFile(String path, String spacing, boolean ccwFaces, boolean ccwVertex) throws FileNotFoundException {
		File file = new File(path);
		Scanner scanner = new Scanner(file);
		
		ArrayList<Vector3D> vertexPool = new ArrayList<Vector3D>();
		ArrayList<Triangle> trianglePool = new ArrayList<Triangle>();

		String line = new String();
		
		while (scanner.hasNextLine()) {
			line = scanner.nextLine();
			if(line.length()>0) {
				if(line.charAt(0)=='v') {
					vertexPool.add(toVector(line, spacing));
				}
			}
		}
		
		scanner.close();
		scanner = new Scanner(file);
		while (scanner.hasNextLine()) {
			line = scanner.nextLine();
			if(line.length()>0) {
				if(line.charAt(0)=='f') {
					trianglePool.addAll((toTriangle(line, vertexPool, spacing)));
				}
			}
		}

		return new Mesh(trianglePool, ccwFaces, ccwVertex);
	}
	
	private static ArrayList<Triangle> toTriangle(String line, ArrayList<Vector3D> pool, String spacing) {
		String[] splitLine=line.split(spacing);
		ArrayList<Triangle> ts = new ArrayList<>();
		int[] nums = new int[splitLine.length];
		int j=0;
		for(int i=0;i<splitLine.length;i++) {
			if(!(splitLine[i].contains(spacing)||splitLine[i].contains("f")||splitLine[i].isEmpty())) {
				nums[j]=(Integer.valueOf(String.valueOf(splitLine[i]))-1);
				j++;
			}
		}
		
		ts.add( new Triangle(new double[][] {
			{pool.get(nums[0]).xyz[0], pool.get(nums[0]).xyz[1], pool.get(nums[0]).xyz[2]},
			{pool.get(nums[1]).xyz[0], pool.get(nums[1]).xyz[1], pool.get(nums[1]).xyz[2]},
			{pool.get(nums[2]).xyz[0], pool.get(nums[2]).xyz[1], pool.get(nums[2]).xyz[2]}
		}));
		
		for(int i=4;i<nums.length;i++) {
			ts.add( new Triangle(new double[][] {
				{pool.get(nums[i-2]).xyz[0], pool.get(nums[i-2]).xyz[1], pool.get(nums[i-2]).xyz[2]},
				{pool.get(nums[i-1]).xyz[0], pool.get(nums[i-1]).xyz[1], pool.get(nums[i-1]).xyz[2]},
				{pool.get(nums[0]).xyz[0], pool.get(nums[0]).xyz[1], pool.get(nums[0]).xyz[2]}
			}));
		}
		
		return ts;
	}

	private static Vector3D toVector(String line, String spacing) {
		String[] splitLine=line.split(spacing);
		double[] nums = new double[splitLine.length];
		int j=0;
		for(int i=0;i<splitLine.length;i++) {
			if(!(splitLine[i].contains(spacing)||splitLine[i].contains("v"))||splitLine[i].isEmpty()) {
				nums[j]=Double.valueOf(String.valueOf(splitLine[i]));
				j++;
			}
		}
		return new Vector3D(nums[0], nums[1], nums[2]);
	}
}
