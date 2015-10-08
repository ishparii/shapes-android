package edu.luc.etl.cs313.android.shapes.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A shape visitor for calculating the bounding box, that is, the smallest
 * rectangle containing the shape. The resulting bounding box is returned as a
 * rectangle at a specific location.
 */
public class BoundingBox implements Visitor<Location> {

	// TODO entirely your job (except onCircle)

	@Override
	public Location onCircle(final Circle c) {
		final int radius = c.getRadius();
		return new Location(-radius, -radius, new Rectangle(2 * radius, 2 * radius));
	}

	@Override
	public Location onFill(final Fill f) {
		return f.getShape().accept(this);
	}

	@Override
	public Location onGroup(final Group g) {
		List<? extends Shape> shapes = g.getShapes();

		Shape firstShape = shapes.get(0);
		Location firstLoc = firstShape.accept(this);
		Rectangle firstBoundingBox = (Rectangle)firstLoc.getShape();

		int xMin = firstLoc.getX();
		int yMin = firstLoc.getY();
		int xMax = xMin + firstBoundingBox.getWidth();
		int	yMax = yMin + firstBoundingBox.getHeight();

		for (int i=1; i<shapes.size(); i++) {
			Shape shape = shapes.get(i);
			Location loc = shape.accept(this);

			Rectangle rectangleCurrent = (Rectangle)loc.getShape();
			int xMinCurrent = loc.getX();
			int yMinCurrent = loc.getY();
			int xMaxCurrent = xMinCurrent + rectangleCurrent.getWidth();
			int yMaxCurrent = yMinCurrent + rectangleCurrent.getHeight();

			xMin = Math.min(xMinCurrent, xMin);
			yMin = Math.min(yMinCurrent, yMin);
			xMax = Math.max(xMaxCurrent, xMax);
			yMax = Math.max(yMaxCurrent, yMax);
		}
		return new Location(xMin, yMin, new Rectangle(xMax-xMin, yMax-yMin));
	}

	@Override
	public Location onLocation(final Location l) {
		Location loc = l.getShape().accept(this);
		int xNew = l.getX() + loc.getX();
		int yNew = l.getY() + loc.getY();
		Rectangle boundingBoxNew = (Rectangle)loc.getShape();
		return new Location(xNew, yNew, boundingBoxNew);
	}

	@Override
	public Location onRectangle(final Rectangle r) {
		return new Location(0,0, new Rectangle(r.getWidth(), r.getHeight()));
	}

	@Override
	public Location onStroke(final Stroke c) {
		return c.getShape().accept(this);
	}

	@Override
	public Location onOutline(final Outline o) {
		return o.getShape().accept(this);
	}

	@Override
	public Location onPolygon(final Polygon s) {
		List<? extends Point> points = s.getPoints();
		List<Integer> x = new ArrayList<Integer>(points.size());
		List<Integer> y = new ArrayList<Integer>(points.size());

		for (Point point : points) {
			x.add(point.getX());
			y.add(point.getY());
		}
		int xMin = Collections.min(x);
		int xMax = Collections.max(x);
		int yMin = Collections.min(y);
		int yMax = Collections.max(y);

		return new Location(xMin, yMin, new Rectangle(xMax-xMin, yMax-yMin));
	}
}
