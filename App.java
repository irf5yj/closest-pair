package closestpairs;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class App {

	public static void main(String[] args) {
		
		//List<Point> points = Arrays.asList(new Point(399, 893));

		ArrayList<Point> points = new ArrayList<Point>();
		for (int i=0; i<10000000; i++) {
			Random r = new Random();
			double newX = r.nextInt((1000 - 1) + 1) + 1;
			double newY = r.nextInt((1000 - 1) + 1) + 1;
			points.add(new Point(newX, newY));
		}
		//System.out.println(points);
		//System.out.println(points.size());

		long startTime = System.nanoTime();
		
		Algorithm algorithm = new Algorithm(points);
		System.out.println("\nClosest Pair of Points: " + algorithm.solveProblem());

		long timeElapsed = System.nanoTime() - startTime;
		
		System.out.println("Execution time in nanoseconds: " + timeElapsed);
	}

}
