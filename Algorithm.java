package closestpairs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Algorithm {

	private List<Point> points;

	public Algorithm(List<Point> points) {
		this.points = points;
	}

	public double solveProblem() {
		List<Point> sortedXPoints = new ArrayList<>();
		List<Point> yPoints = new ArrayList<>();

		// Print Points in original order
		//System.out.println("Original Points: " + this.points);

		for (Point point : this.points) {
			sortedXPoints.add(point);
			yPoints.add(point);
		}

		sortByX(sortedXPoints);

		// Print Points sorted by X
		//System.out.println("Points sorted by X values: " + sortedXPoints);

		return findClosestPoints(sortedXPoints, yPoints, sortedXPoints.size());

	}

	public double findClosestPoints(List<Point> pointsSortedX, List<Point> pointsY, int numOfPoints) {

		System.out.println("Calling findClosestPoints with : " + numOfPoints);

		// For sufficiently small size of Points, just use brute force method
		if (numOfPoints <= 3) {
			return bruteForceApproach(pointsSortedX);
		}

		// Determine midpoint's index and value
		int middleIndex = numOfPoints / 2;
		Point middlePoint = pointsSortedX.get(middleIndex);

		// Print midpoint index and value
		//System.out.println("\nMiddle Index: " + middleIndex);
		//System.out.println("Midpoint: " + middlePoint);

		List<Point> leftSubPointsY = new ArrayList<>();
		List<Point> leftSubPointsSortedX = new ArrayList<>();
		List<Point> rightSubPointsY = new ArrayList<>();
		List<Point> rightSubPointsSortedX = new ArrayList<>();

		// divide the points into left and right subarrays based on index (not X value of index)
		for (int index = 0; index < numOfPoints; ++index) {
			//System.out.println(pointsSortedX.get(index) + " : " + numOfPoints + " : " + index);
			if (index <= middleIndex) {
				leftSubPointsY.add(pointsY.get(index));
				leftSubPointsSortedX.add(pointsSortedX.get(index));
			} else {
				rightSubPointsY.add(pointsY.get(index));
				rightSubPointsSortedX.add(pointsSortedX.get(index));
			}
		}
		
		// Print Points on Left and Right
		//System.out.println("\nLeft Points: " + leftSubPointsSortedX);
		//System.out.println("\tCount points in Left: " + leftSubPointsY.size());
		//System.out.println("Right Points: " + rightSubPointsSortedX);
		//System.out.println("\tCount points in Right: " + rightSubPointsY.size());

		//System.out.println("\n\nRecursive call thru Left subarray:");
		double sigmaLeft = findClosestPoints(leftSubPointsSortedX, leftSubPointsY, middleIndex + 1);
		//System.out.println("\n\nRecursive call thru Right subarray:");
		double sigmaRight = findClosestPoints(rightSubPointsSortedX, rightSubPointsY, numOfPoints - middleIndex - 1);
		double sigma = Math.min(sigmaLeft, sigmaRight);

		// Print count of Left and Right with their sigmas

		//System.out.println("Left sigma: " + sigmaLeft);
		//System.out.println("Right sigma: " + sigmaRight);
		//System.out.println("*Sigma: " + sigma);

		List<Point> pointsInStrip = new ArrayList<>();

		for (int index = 0; index < numOfPoints; ++index) {
			if (Math.abs(pointsY.get(index).getX() - middlePoint.getX()) < sigma) {
				pointsInStrip.add(pointsY.get(index));
			}
		}

		double minDistanceStrip = findMinimumDistanceInStrip(pointsInStrip, sigma);

		// Print count of strip and its min
		//System.out.println("\nPoints in Strip: " + pointsInStrip);
		//System.out.println("Count points in Strip: " + pointsInStrip.size());
		//System.out.println("Strip minDistance: " + minDistanceStrip);
		//System.out.println("*Note Strip minDistance = sigma if closer pair isn't found in Strip");

		return Math.min(sigma, minDistanceStrip);
	}

	public double bruteForceApproach(List<Point> points) {

		double minDistance = Double.MAX_VALUE;

		for (int i = 0; i < points.size(); ++i) {
			for (int j = i + 1; j < points.size(); ++j) {
				if (distance(points.get(i), points.get(j)) < minDistance) {
					minDistance = distance(points.get(i), points.get(j));
				}
			}
		}
		return minDistance;
	}

	private double findMinimumDistanceInStrip(List<Point> pointsInStrip, double sigma) {

		double minDistance = sigma;
		
		sortByY(pointsInStrip);
		
		for (int i = 0; i < pointsInStrip.size(); ++i) {
			for (int j = i + 1; j < pointsInStrip.size()
					&& pointsInStrip.get(j).getY() - pointsInStrip.get(i).getY() < minDistance ; ++j) {
					System.out.println("\t\tComparing two Strip points: " + pointsInStrip.get(i) + "<>" + pointsInStrip.get(j));
					if (distance(pointsInStrip.get(i), pointsInStrip.get(j))< minDistance) {
						minDistance = distance(pointsInStrip.get(i), pointsInStrip.get(j));
				}
			}
		}
		return minDistance;
	}

	private void sortByX(List<Point> points) {
		Collections.sort(points, new XSorter());
	}

	private void sortByY(List<Point> points) {
		Collections.sort(points, new YSorter());
	}
	
	private double distance(Point point1, Point point2) {
		double xDistance = Math.abs(point1.getX() - point2.getX());
		double yDistance = Math.abs(point1.getY() - point2.getY());
		return Math.sqrt(xDistance * xDistance + yDistance * yDistance);
	}
}
