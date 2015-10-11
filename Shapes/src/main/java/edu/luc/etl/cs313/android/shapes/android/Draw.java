package edu.luc.etl.cs313.android.shapes.android;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;

import java.util.List;

import edu.luc.etl.cs313.android.shapes.model.*;

/**
 * A Visitor for drawing a shape to an Android canvas.
 */
public class Draw implements Visitor<Void> {

	// TODO entirely your job (except onCircle)

	private final Canvas canvas;

	private final Paint paint;

	public Draw(final Canvas canvas, final Paint paint) {
		this.canvas = canvas; // FIXME
		this.paint = paint; // FIXME
		paint.setStyle(Style.STROKE);
	}

	@Override
	public Void onCircle(final Circle c) {
		canvas.drawCircle(0, 0, c.getRadius(), paint);
		return null;
	}

	@Override
	public Void onStroke(final Stroke c) {
		int colorOrigin = paint.getColor();
		int color = c.getColor();
		paint.setColor(color);
		c.getShape().accept(this);
		paint.setColor(colorOrigin);
		return null;
	}

	@Override
	public Void onFill(final Fill f) {
		Paint.Style styleOrigin = paint.getStyle();
		paint.setStyle(Style.FILL_AND_STROKE);
		f.getShape().accept(this);
		paint.setStyle(styleOrigin);
		return null;
	}

	@Override
	public Void onGroup(final Group g) {
		List<? extends Shape> shapes = g.getShapes();
		for (Shape shape : shapes){
			shape.accept(this);
		}
		return null;
	}

	@Override
	public Void onLocation(final Location l) {
		canvas.translate(l.getX(), l.getY());
		l.getShape().accept(this);
		canvas.translate(-l.getX(), -l.getY());
		return null;
	}

	@Override
	public Void onRectangle(final Rectangle r) {
		canvas.drawRect(0, 0, r.getWidth(), r.getHeight(), paint);
		return null;
	}

	@Override
	public Void onOutline(Outline o) {
		Paint.Style styleOrigin = paint.getStyle();
		paint.setStyle(Style.STROKE);
		o.getShape().accept(this);
		paint.setStyle(styleOrigin);
		return null;
	}

	@Override
	public Void onPolygon(final Polygon s) {

		List<? extends Point> points = s.getPoints();
		int size = points.size()*4;
		final float[] pts = new float[size];

		//first point
		Point pointFirst = points.get(0);
		pts[0] = (float)pointFirst.getX();
		pts[1] = (float)pointFirst.getY();

		int i = 2;
		for (int k=1; k<points.size(); k++) {
			float xCurrent = (float)points.get(k).getX();
			float yCurrent = (float)points.get(k).getY();
			pts[i] = pts[i+2] = xCurrent;
			pts[i+1] = pts[i+3] = yCurrent;
			i = i + 4;
		}

		//to join last point with the first one
		pts[size-2] = pts[0];
		pts[size-1] = pts[1];

		canvas.drawLines(pts, paint);
		return null;
	}
}
