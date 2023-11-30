package ch.bfh.piechart.ui;

import ch.bfh.piechart.datalayer.SalesValue;
import javafx.scene.Group;
import javafx.scene.Node;

import java.util.ArrayList;
import java.util.List;

public class PieChartUIControl extends Group {

	private double centerX;
	private double centerY;
	private double radius;

	/**
	 * @param x the x-value of the center position
	 * @param y the y-value of the center position
	 * @param r the radius for the chart
	 */
	public PieChartUIControl(double x, double y, double r) {

		centerX = x;
		centerY = y;
		radius = r;
	}

	/**
	 * Calculated the angles of pie chart slices (objects of CircleSector) based on
	 * percentages in the list of SalesValue objects. The first angle is 0.0, the
	 * last must be 2.0 * Math.PI
	 * 
	 * @param values List of SalesValues
	 * @return List of angles (Double)
	 */
	public static List<Double> getAngles(List<SalesValue> values) {
		if (values == null) {
			throw new UnsupportedOperationException();
		}
		List<Double> angles = new ArrayList<>();
		double sum = 0;
		angles.add(sum);
		for (SalesValue salesValue : values) {
			sum += salesValue.getPercentage() / 100 * 2 * Math.PI;
			angles.add(sum);
		}
		return angles;
	}

	/**
	 * Gets the list of SaleValues and creates CircleSectors accordingly Register
	 * the CircleSector.onClick() method as event handler at each circle sector
	 *
	 * @param chartData List of SalesValue
	 */
	public void addData(List<SalesValue> chartData) {
		if (chartData == null) {
			throw new UnsupportedOperationException();
		}
		List<Double> angles = getAngles(chartData);
		for (int i = 0; i < angles.size() - 1; i++) {
			double start = angles.get(i);
			double end = angles.get(i + 1);
			CircleSector circleSector = new CircleSector(start, end);
			getChildren().add(circleSector);
			circleSector.update(centerX, centerY, radius);
		}
	}

	/**
	 * Repositions the pie chart and the contained CircleSectors
	 *
	 * @param newX - the new x-value of the center position
	 * @param newY - the new y-value of the center position
	 * @param newR - the new radius for the chart
	 */
	public void resize(double newX, double newY, double newR) {
		for (Node node : getChildren()) {
			if (node instanceof CircleSector) {
				CircleSector circleSector = (CircleSector) node;
				circleSector.update(newX, newY, newR);
			}
		}

	}

}
