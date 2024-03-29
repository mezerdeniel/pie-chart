/*
 * Project and Training 2: Pie Chart - Computer Science, Berner Fachhochschule
 */
package ch.bfh.piechart.ui;

import ch.bfh.matrix.GraphicOps;
import ch.bfh.matrix.Matrix;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

/**
 * JavaFX control representing a circle sector. To be used as visual
 * representation of a slice within a pie chart.
 */

public class CircleSector extends Path {

	/**
	 * normalized translation vector
	 */
	static final Matrix DETACH_VECTOR = new Matrix(new double[][] { { 0.0 }, { -0.2 }, // 20% of the radius from the
																						// center
			{ 1.0 } });

	static final int CLASSES = 10;
	static int classIndex = 0;

	private double centerX;
	private double centerY;
	private double radius;
	private boolean detached = false;

	private final Matrix coordsDetached;
	private final Matrix coordsAttached;

	/**
	 * Returns the coordinates of a dettached circle sector
	 *
	 * @return Matrix with coordinates
	 */
	public Matrix getCoordsDetached() {
		return coordsDetached;
	}

	/**
	 * Returns the coordinates of an attached circle sector
	 *
	 * @return Matrix with coordinates
	 */
	public Matrix getCoordsAttached() {
		return coordsAttached;
	}

	/**
	 * Creates the control and sets the style class.
	 */
	public CircleSector(double startAngle, double endAngle) {
		getStyleClass().add("color" + classIndex);
		classIndex = (classIndex + 1) % CLASSES;
		// rotates original Matrix and makes a new rotated Matrix
		Matrix start = GraphicOps.rotate(GraphicOps.UNIT_Y_VECTOR, startAngle);
		Matrix end = GraphicOps.rotate(GraphicOps.UNIT_Y_VECTOR, endAngle);

		coordsAttached = new Matrix(new double[][] { { 0.0, start.get(0, 0), end.get(0, 0) },
				{ 0.0, start.get(1, 0), end.get(1, 0) }, { 1.0, 1.0, 1.0 } });
		// detaching of the slices
		double angle = (startAngle + endAngle) / 2.0;
	    Matrix detachedVector = GraphicOps.rotate(GraphicOps.UNIT_Y_VECTOR, angle);
	    coordsDetached = GraphicOps.translate(coordsAttached, detachedVector.get(0, 0) * 0.2, detachedVector.get(1, 0) * 0.2);
		update(coordsAttached);
		addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				onClick();

			}
		});
	}

	/**
	 * Updates the visual representation based on positions given in a matrix. The *
	 * matrix must contain 3 columns: 0 = center position, 1 = arc's start position,
	 * 2 = arc's end position.
	 *
	 * @param pos Matrix containing homogeneous coordinates
	 **/
	private void update(Matrix pos) {
		// centerX x-value of the center.
		double centerX = pos.get(0, 0) / pos.get(2, 0);
		// centerY y-value of the center.
		double centerY = pos.get(1, 0) / pos.get(2, 0);
		// startX x-value of the arc's start.
		double startX = pos.get(0, 2) / pos.get(2, 2);
		// startY y-value of the arc's start.
		double startY = pos.get(1, 2) / pos.get(2, 2);
		// endX x-value of the arc's end.
		double endX = pos.get(0, 1) / pos.get(2, 1);
		// endY y-value of the arc's end.
		double endY = pos.get(1, 1) / pos.get(2, 1);

		double r = Math.sqrt(Math.pow(startX - centerX, 2.0) + Math.pow(startY - centerY, 2.0));
		getElements().clear();
		getElements().add(new MoveTo(centerX, centerY));
		getElements().add(new LineTo(startX, startY));
		getElements().add(new ArcTo(r, r, 0, endX, endY, false, false));
		getElements().add(new ClosePath());
		System.out.println(detached);
	}

	/**
	 * Updates the visual representation based on positions given in a matrix.
	 * Should be called, when the window size has changed...
	 *
	 * @param x - the current x-value of the center position
	 * @param y - the current y-value of the center position
	 * @param r - the radius for the chart
	 */
	public void update(double x, double y, double r) {
		centerX = x;
		centerY = y;
		radius = r;
		Matrix matrix = createTransformation(x, y, r).multiply(detached ? coordsDetached : coordsAttached);
		update(matrix);
	}

	/**
	 * Create a transformation matrix to scale and to translate a matrix of
	 * coordinates.
	 *
	 * @param x - the current x-value of the center position
	 * @param y - the current y-value of the center position
	 * @param r - the radius for the chart
	 */
	public static Matrix createTransformation(double x, double y, double r) {
		Matrix scaleMatrix = GraphicOps.scale(r);
		Matrix translationMatrix = GraphicOps.translate(x, y);
		return translationMatrix.multiply(scaleMatrix);
	}

	/**
	 * Called when the user clicks a slice in the user interface.
	 **/
	public void onClick() {
		detached = !detached;
		System.out.println(detached);
		update(centerX, centerY, radius);
	}
}
