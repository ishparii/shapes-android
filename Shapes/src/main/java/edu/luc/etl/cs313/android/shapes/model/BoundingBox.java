package edu.luc.etl.cs313.android.shapes.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import edu.luc.etl.cs313.android.shapes.android.R;

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
	public Location onFill(final Fill f) {  //Anne Check me Updated Oct 6 10pm
		Shape shape = f.getShape();
		if(shape instanceof Rectangle){
			return onRectangle((Rectangle) shape);
		}
		else if(shape instanceof Circle){
			return onCircle((Circle) shape);
		}
		else if(shape instanceof Polygon){
			return onPolygon((Polygon) shape);
		}
		else if(shape instanceof Group){
			return onGroup((Group)shape);
		}
		else if(shape instanceof Stroke){
			return onStroke((Stroke)shape);
		}
		else if (shape instanceof Location){
			return onLocation((Location)shape);
		}
		else{
			return onOutline((Outline)shape);
		}
	}

	private void addCoordinate(Location lc, List<Integer> x, List<Integer> y){   //Anne Fix me
		int minCoordinateX = lc.getX();
		int minCoordinateY = lc.getY();

		x.add(minCoordinateX);
		y.add(minCoordinateY);

		Rectangle r =(Rectangle)lc.getShape();
		int maxCoordinateX = minCoordinateX+r.getWidth();
		int maxCoordinateY = minCoordinateY+r.getHeight();

		x.add(maxCoordinateX);
		y.add(maxCoordinateY);
	}



	@Override
	public Location onGroup(final Group g) {     //Anne Fix me
		List<? extends Shape> groupOfShapes = g.getShapes();
		Iterator<? extends  Shape> iteratorShapes = groupOfShapes.iterator();

		List<Integer> listOfX= new ArrayList<Integer>();
		List<Integer> listOfY = new ArrayList<Integer>();


		while(iteratorShapes.hasNext()){
			Shape sh = iteratorShapes.next();

			if(sh instanceof Circle){
				Location lc = onCircle((Circle) sh);

				addCoordinate(lc,listOfX,listOfY);
			}
			else if(sh instanceof Rectangle){
				Location lc = onRectangle((Rectangle) sh);
				addCoordinate(lc,listOfX,listOfY);
			}
			else if(sh instanceof Polygon){
				Location lc = onPolygon((Polygon) sh);
				addCoordinate(lc, listOfX, listOfY);

			}
			else if(sh instanceof Fill){
				Location lc = onFill((Fill) sh);
				addCoordinate(lc, listOfX, listOfY);
			}
			else if(sh instanceof Location){
				Location lc = onLocation((Location) sh);
				addCoordinate(lc, listOfX, listOfY);
			}
			else if(sh instanceof Outline){
				Location lc = onOutline((Outline) sh);
				addCoordinate(lc, listOfX, listOfY);
			}
			else if(sh instanceof Stroke){
				Location lc = onStroke((Stroke) sh);
				addCoordinate(lc, listOfX, listOfY);
			}
			else{
				Location lc = onGroup((Group) sh);
				addCoordinate(lc, listOfX, listOfY);
			}
		}

		int widthMax, heightMax, widthMin, heightMin;

		widthMax = Collections.max(listOfX);
		heightMax = Collections.max(listOfY);
		widthMin = Collections.min(listOfX);
		heightMin = Collections.min(listOfY);

		return new Location(widthMin, heightMin, new Rectangle(widthMax-widthMin,heightMax-heightMin));
	}

	@Override
	public Location onLocation(final Location l) {     //Anne Check me Updated Oct 6 10pm
		Shape shape = l.getShape();
		if(shape instanceof Rectangle){
			Rectangle r = (Rectangle)shape;
			return new Location(l.getX(),l.getY(),new Rectangle(r.getWidth(), r.getHeight()));
		}
		else if(shape instanceof Circle){
			Circle c = (Circle)shape;
			return new Location(l.getX()-c.getRadius(),l.getY()-c.getRadius(),new Rectangle(c.getRadius()*2, c.getRadius()*2));
		}
		else if(shape instanceof Polygon){
			return onPolygon((Polygon) shape);
		}
		else if(shape instanceof Stroke){
			return onStroke((Stroke)shape);
		}
		else if(shape instanceof Fill){
			return onFill((Fill)shape);
		}
		else if(shape instanceof Outline){
			return onOutline((Outline)shape);
		}
		else {
			return onGroup((Group)shape);
		}
	}


	@Override
	public Location onRectangle(final Rectangle r) {
		return new Location(0,0, new Rectangle(r.getWidth(), r.getHeight()));
	}


	@Override
	public Location onStroke(final Stroke c) {     //Anne Check me Updated Oct 6 10pm
		Shape shape = c.getShape();
		if(shape instanceof Rectangle){
			return onRectangle((Rectangle) shape);
		}
		else if(shape instanceof Circle){
			return onCircle((Circle) shape);
		}
		else if(shape instanceof Polygon){
			return onPolygon((Polygon) shape);
		}
		else if(shape instanceof Outline){
			return onOutline((Outline)shape);
		}
		else if(shape instanceof Fill){
			return onFill((Fill)shape);
		}
		else if(shape instanceof Location){
			return onLocation((Location)shape);
		}
		else{
			return onGroup((Group)shape);
		}

	}

	@Override
	public Location onOutline(final Outline o) {    //Anne Check me Updated Oct 6 10pm
		Shape shape = o.getShape();
		if(shape instanceof Rectangle){
			return onRectangle((Rectangle) shape);
		}
		else if(shape instanceof Circle){
			return onCircle((Circle) shape);
		}
		else if(shape instanceof Polygon){
			return onPolygon((Polygon) shape);
		}
		else if(shape instanceof Group){
			return onGroup((Group)shape);
		}
		else if(shape instanceof Stroke){
			return onStroke((Stroke)shape);
		}
		else if(shape instanceof Fill){
			return onFill((Fill)shape);
		}
		else{
			return onLocation((Location)shape);
		}
	}

	@Override
	public Location onPolygon(final Polygon s) {  //Anne Check me
		int[] arrX = new int[s.getPoints().size()];
		int[] arrY = new int[s.getPoints().size()];

		for(int n=0;n<s.getPoints().size();n++){
			arrX[n] = s.getPoints().get(n).getX();
			arrY[n] = s.getPoints().get(n).getY();
		}

		int widthMax = arrX[0];
		int heightMax = arrY[0];
		int widthMin = arrX[0];
		int heightMin = arrY[0];

		for (int x : arrX) {
			if(x>widthMax){
				widthMax = x;
			}
			if(x<widthMin) {
				widthMin = x;
			}
		}
		for (int y: arrY) {
			if(y>heightMax){
				heightMax = y;
			}
			if(y<heightMin)
				heightMin = y;
		}
		return new Location(widthMin, heightMin, new Rectangle(widthMax-widthMin,heightMax-heightMin));
	}
}
