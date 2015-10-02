package edu.luc.etl.cs313.android.shapes.model;

/**
 * A decorator for specifying the stroke (foreground) color for drawing the
 * shape.
 */
public class Stroke implements Shape {

	// TODO entirely your job

	private int color;
	private Shape shape;

	public Stroke(final int color, final Shape shape) {
		this.color = color;
		this.shape = shape;
	}

	public int getColor() {
		return color;
	}   //from -1

	public Shape getShape() {
		return shape;
	}  //from null

	@Override
	public <Result> Result accept(Visitor<Result> v) {
		return v.onStroke(this);
	}
}
