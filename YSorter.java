package closestpairs;

import java.util.Comparator;

public class YSorter implements Comparator<Point>{
	
	@Override
	public int compare(Point point, Point otherPoint) {
		return Double.compare(point.getY(), otherPoint.getY());
	}
}
