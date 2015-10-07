package edu.luc.etl.cs313.android.shapes.android;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
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
	public Void onStroke(final Stroke c) {  //Anne Check me Oct 2nd 12pm
		int color = c.getColor();
		Shape shape = c.getShape();

		paint.setColor(color);

		if(shape instanceof Circle){
			this.onCircle((Circle)shape);
		}
		else if(shape instanceof Rectangle){
			this.onRectangle((Rectangle)shape);
		}
		else if(shape instanceof Polygon){
			this.onPolygon((Polygon)shape);
		}
		else if(shape instanceof Group){
			this.onGroup((Group)shape);
		}
		else if(shape instanceof Outline){
			this.onOutline((Outline)shape);
		}
		else if(shape instanceof Fill){
			this.onFill((Fill)shape);
		}
		else if(shape instanceof Location) {
			this.onLocation((Location) shape);
		}
		else{
		}
		return null;
	}

	@Override
	public Void onFill(final Fill f) {  //Anne Check me Oct 2nd 12pm
		Shape shape = f.getShape();

		paint.setStyle(Style.FILL_AND_STROKE);

		if(shape instanceof Circle){
			this.onCircle((Circle)shape);
		}
		else if(shape instanceof Rectangle){
			this.onRectangle((Rectangle)shape);
		}
		else if(shape instanceof Polygon){
			this.onPolygon((Polygon)shape);
		}
		else if(shape instanceof Group){
			this.onGroup((Group)shape);
		}
		else if(shape instanceof Outline){
			this.onOutline((Outline)shape);
		}
		else if(shape instanceof Stroke){
			this.onStroke((Stroke)shape);
		}
		else if(shape instanceof Location) {
			this.onLocation((Location) shape);
		}
		else{
		}
		return null;
	}

	@Override
	public Void onGroup(final Group g) {   //Anne Check me Oct 2nd 12pm
		List<? extends Shape> listOfShapes = g.getShapes();

		Iterator<? extends Shape> iterator = listOfShapes.iterator();

		while(iterator.hasNext()){
			Shape sh = iterator.next();

			if(sh instanceof Circle){
				this.onCircle((Circle)sh);
			}
			else if(sh instanceof Rectangle){
				this.onRectangle((Rectangle)sh);
			}
			else if(sh instanceof Polygon){
				this.onPolygon((Polygon)sh);
			}
			else if(sh instanceof Fill){
				this.onFill((Fill)sh);
			}
			else if(sh instanceof Location){
				this.onLocation((Location)sh);
			}
			else if(sh instanceof Outline){
				this.onOutline((Outline)sh);
			}
			else if(sh instanceof Stroke){
				this.onStroke((Stroke)sh);
			}
			else{
				this.onGroup((Group)sh);
			}
		}

		return null;
	}

	@Override
	public Void onLocation(final Location l) {  //Anne Check me Oct 2nd 12pm
		canvas.translate(l.getX(), l.getY());
		Shape shape = l.getShape();
		if(shape instanceof Circle){
			this.onCircle((Circle)shape);
		}
		else if(shape instanceof Rectangle){
			this.onRectangle((Rectangle)shape);
		}
		else if(shape instanceof Polygon){
			this.onPolygon((Polygon)shape);
		}
		else if(shape instanceof Group){
			this.onGroup((Group)shape);
		}
		else if(shape instanceof Outline){
			this.onOutline((Outline)shape);
		}
		else if(shape instanceof Stroke){
			this.onStroke((Stroke)shape);
		}
		else if(shape instanceof Fill) {
			this.onFill((Fill) shape);
		}
		else if(shape instanceof Location) {
			this.onLocation((Location) shape);
		}
		else{
		}
		canvas.translate(-l.getX(),-l.getY());
		return null;
	}

	@Override
	public Void onRectangle(final Rectangle r) {
		canvas.drawRect(0,0, r.getWidth(), r.getHeight(), paint);
		return null;
	}

	@Override
	public Void onOutline(Outline o) {  //Anne Check me Oct 2nd 12pm
		Shape shape = o.getShape();

		paint.setStyle(Style.STROKE);

		if(shape instanceof Circle){
			this.onCircle((Circle)shape);
		}
		else if(shape instanceof Rectangle){
			this.onRectangle((Rectangle)shape);
		}
		else if(shape instanceof Polygon){
			this.onPolygon((Polygon)shape);
		}
		else if(shape instanceof Group){
			this.onGroup((Group)shape);
		}
		else if(shape instanceof Stroke){
			this.onStroke((Stroke)shape);
		}
		else if(shape instanceof Fill) {
			this.onFill((Fill) shape);
		}
		else if(shape instanceof Location) {
			this.onLocation((Location) shape);
		}
		else{
		}
		return null;
	}

	@Override
	public Void onPolygon(final Polygon s) {   //Anne Check me

		int size = s.getPoints().size();
		size = size*2;
		final float[] pts = new float[size];
		//get each point from the list


		for(int n=0,m=0; n<size-2; n=n+2,m++)
		{
			pts[n] = (float)s.getPoints().get(m).getX();
			pts[n+1] = (float)s.getPoints().get(m).getY();
		}

		pts[size-2] = pts[0];
		pts[size-1] = pts[1];

		canvas.drawLines(pts, paint);
		return null;
	}
}
