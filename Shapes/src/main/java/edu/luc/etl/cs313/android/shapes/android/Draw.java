package edu.luc.etl.cs313.android.shapes.android;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;

import java.util.Arrays;
import java.util.Iterator;

import edu.luc.etl.cs313.android.shapes.model.*;

/**
 * A Visitor for drawing a shape to an Android canvas.
 */
public class Draw implements Visitor<Void> {

	// TODO entirely your job (except onCircle)

	private final Canvas canvas;

	private final Paint paint;

	public Draw(final Canvas canvas, final Paint paint) {
		this.canvas = canvas; // FIXME from null
		this.paint = paint; // FIXME from null
		paint.setStyle(Style.STROKE);
	}

	@Override
	public Void onCircle(final Circle c) {
		canvas.drawCircle(0, 0, c.getRadius(), paint);
		return null;
	}

	@Override
	public Void onStroke(final Stroke c) {

		return null;
	}

	@Override
	public Void onFill(final Fill f) {

		return null;
	}

	@Override
	public Void onGroup(final Group g) {

		return null;
	}

	@Override
	public Void onLocation(final Location l) {  // Anne Fix me
		canvas.translate(l.getX(), l.getY());
		onRectangle((Rectangle)l.getShape());
		canvas.translate(-l.getX(),-l.getY());
		return null;
	}

	@Override
	public Void onRectangle(final Rectangle r) {
		canvas.drawRect(0,0, r.getWidth(), r.getHeight(), paint); // draw a rectangle
		return null;
	}

	@Override
	public Void onOutline(Outline o) {

		return null;
	}

	@Override
	public Void onPolygon(final Polygon s) {

		int size = s.getPoints().size();
		size = size*2;
		final float[] pts = new float[size]; //from null
		//get each point from the list


		for(int n=0,m=0; n<size-2; n=n+2,m++)
		{
			pts[n] = (float)s.getPoints().get(m).getX(); //insert the rest of the point
			pts[n+1] = (float)s.getPoints().get(m).getY();
		}

		pts[size-2] = pts[0];  //insert the last point
		pts[size-1] = pts[1];

		canvas.drawLines(pts, paint);
		return null;
	}
}
