package edu.luc.etl.cs313.android.shapes.model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.lang.Class;

/**
 * A visitor to compute the number of basic shapes in a (possibly complex)
 * shape.
 */
public class Size implements Visitor<Integer> {

	// TODO entirely your job

	private Integer sizeOfShapes(Shape shape){
		if(shape instanceof Circle || shape instanceof Rectangle || shape instanceof Polygon){
			return 1;
		}
		else if(shape instanceof Group){
			Integer count=0;

			List<? extends Shape> list = ((Group) shape).getShapes();
			Iterator<? extends Shape> iterator = list.iterator();

			while(iterator.hasNext()){
				count += sizeOfShapes(iterator.next());
			}

			return count;
		}
		else if(shape instanceof Location){
			Shape sh = ((Location)shape).getShape();
			return sizeOfShapes(sh);
		}

		else if(shape instanceof Fill){
			Shape sh = ((Fill)shape).getShape();
			return sizeOfShapes(sh);
		}
		else if(shape instanceof Stroke){
			Shape sh = ((Stroke)shape).getShape();
			return sizeOfShapes(sh);
		}
		else{
			Shape sh = ((Outline)shape).getShape();
			return sizeOfShapes(sh);
		}


		/*  tried another approach, had problem with return syntax under all these exceptions
		else{
			Class<? extends Shape> classOfShape = shape.getClass();

			try{
				Method method = classOfShape.getMethod("getShape");

				try {
					Shape newShapeInstance = classOfShape.newInstance();

					try{
						Object newShape = method.invoke(newShapeInstance);
						return sizeOfShapes((Shape)newShape);
					}
					catch (InvocationTargetException e){
						System.out.println(e.toString());
					}
				}
				catch (InstantiationException e){
					System.out.println(e.toString());
				}
				catch (IllegalAccessException e){
					System.out.println(e.toString());
				}
			}
			catch (NoSuchMethodException e){
				System.out.println(e.toString());
			}
			return -1;
		}
		*/
	}

	@Override
	public Integer onPolygon(final Polygon p) {
		return sizeOfShapes(p);
	}

	@Override
	public Integer onCircle(final Circle c) {
		return sizeOfShapes(c);
	}

	@Override
	public Integer onGroup(final Group g) {
		return sizeOfShapes(g);
	}

	@Override
	public Integer onRectangle(final Rectangle q) {
		return sizeOfShapes(q);
	}

	@Override
	public Integer onOutline(final Outline o) {
		return sizeOfShapes(o);
	}

	@Override
	public Integer onFill(final Fill c) {
		return sizeOfShapes(c);
	}

	@Override
	public Integer onLocation(final Location l) {
		return sizeOfShapes(l);
	}

	@Override
	public Integer onStroke(final Stroke c) {
		return sizeOfShapes(c);
	}
}
