/*
 * Project and Training 2: Pie Chart - Computer Science, Berner Fachhochschule
 */
package ch.bfh.matrix;

import java.util.Arrays;

/**
 * Represents a two-dimensional matrix of double-values. Objects are immutable
 * and methods implementing matrix operations always return new matrix objects.
 */
public class Matrix {

	// expected precision in floating point calculations
	// use this for equality comparisons of double values
	public static final double EPSILON = 1e-10;

	// the matrix values in lines and columns
	protected double[][] values;

	/**
	 * Creates a matrix with values given in a two-dimensional array. First
	 * dimension represents lines, second the columns.
	 *
	 * @param values a non-empty and rectangular two-dimensional array
	 */
	public Matrix(final double[][] values) throws IllegalArgumentException {
		if (values == null || values.length == 0) {
			throw new UnsupportedOperationException();
		}

		int colCount = values[0].length;
		if (colCount == 0) {
			throw new UnsupportedOperationException();
		}
		for (int i = 1; i < values.length; i++) {
			if (values[i].length != colCount) {
				throw new UnsupportedOperationException();
			}
		}
		this.values = copy(values);

	}

	/**
	 * @return the number of lines in this matrix
	 */
	public int getNbOfLines() {
		return values.length;
	}

	/**
	 * @return the number of columns in this matrix
	 */
	public int getNbOfColumns() {
		return values[0].length;
	}

	/**
	 * Returns the value at the given position in the matrix.
	 *
	 * @param line indicates the index for the line
	 * @param col  indicates the index for the column
	 * @return the value at the indicated position
	 */
	public double get(final int line, final int col) {
		if (0 <= line && line < getNbOfLines() && 0 <= col && col < getNbOfColumns()) {
			return values[line][col];
		} else {
			throw new UnsupportedOperationException();
		}

	}

	/**
	 * Calculates the transpose of this matrix.
	 *
	 * @return the transpose of this matrix
	 */
	public Matrix transpose() {
		double[][] result = new double[getNbOfColumns()][];
		for (int i = 0; i < getNbOfColumns(); i++) {
			result[i] = new double[getNbOfLines()];
			for (int j = 0; j < getNbOfLines(); j++) {
				result[i][j] = values[j][i];
			}
		}
		return new Matrix(result);
	}

	/**
	 * Calculates the product of this matrix with the given scalar value.
	 *
	 * @param scalar the scalar value to multiply with
	 * @return the scalar product
	 */
	public Matrix multiply(final double scalar) {
		double[][] copiedMatrix = copy(values);
		for (int i = 0; i < getNbOfLines(); i++) {
			for (int j = 0; j < getNbOfColumns(); j++) {
				copiedMatrix[i][j] *= scalar;
			}
		}
		return new Matrix(copiedMatrix);
	}

	/**
	 * Calculates the product of this matrix with another matrix.
	 *
	 * @param other the other matrix to multiply with
	 * @return the matrix product
	 */
	public Matrix multiply(final Matrix other) {
		if (getNbOfColumns() != other.getNbOfLines()) {
			throw new UnsupportedOperationException();
		}
		double[][] result = new double[getNbOfLines()][other.getNbOfColumns()];
		for (int i = 0; i < getNbOfLines(); i++) {
			for (int j = 0; j < other.getNbOfColumns(); j++) {
				for (int k = 0; k < other.getNbOfLines(); k++) {
					result[i][j] += values[i][k] * other.values[k][j];
				}
			}
		}
		return new Matrix(result);
	}

	/**
	 * Calculates the sum of this matrix with another matrix.
	 *
	 * @param other the other matrix to add with
	 * @return the matrix sum
	 */
	public Matrix add(final Matrix other) {
		if (getNbOfLines() != other.getNbOfLines() || getNbOfColumns() != other.getNbOfColumns()) {
			throw new UnsupportedOperationException();
		}
		double[][] result = new double[getNbOfLines()][getNbOfColumns()];
		for (int i = 0; i < getNbOfLines(); i++) {
			for (int j = 0; j < getNbOfColumns(); j++) {
				result[i][j] = values[i][j] + other.values[i][j];
			}
		}
		return new Matrix(result);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		double result = 1;
		for (int i = 0; i < getNbOfLines(); i++) {
			for (int j = 0; j < getNbOfColumns(); j++) {
				result += values[i][j];
			}
		}
		result = prime * result;
		return (int) result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Matrix other = (Matrix) obj;
		if (getNbOfLines() != other.getNbOfLines() || getNbOfColumns() != other.getNbOfColumns()) {
			return false;
		}
		for (int i = 0; i < getNbOfLines(); i++) {
			for (int j = 0; j < getNbOfColumns(); j++) {
				if (Math.abs(values[i][j] - other.values[i][j]) > EPSILON) {
					return false;
				}
			}

		}
		return true;
	}

	@Override
	public String toString() {
		String text = "";
		for (int i = 0; i < getNbOfLines(); i++) {
			for (int j = 0; j < getNbOfColumns(); j++) {
				text += values[i][j] + " ";
			}
			text += "\n";
		}
		return text;
	}

	private static double[][] copy(double[][] values) {
		double[][] copy = new double[values.length][];
		for (int i = 0; i < values.length; i++) {
			copy[i] = new double[values[i].length];
			for (int j = 0; j < values[i].length; j++) {
				copy[i][j] = values[i][j];
			}
		}
		return copy;
	}
}
