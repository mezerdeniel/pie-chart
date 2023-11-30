/*
 * Project and Training 2: Pie Chart - Computer Science, Berner Fachhochschule
 */
package ch.bfh.piechart.model;

import ch.bfh.piechart.datalayer.ConnectionManager;
import ch.bfh.piechart.datalayer.SalesValue;
import ch.bfh.piechart.datalayer.SalesValueRepository;

import java.sql.Connection;
import java.util.List;

public class DataProvider {

	private static List<SalesValue> alreadyReadSales;

	/*
	 * Loads all sales values, computes there relative percentage values, and stores
	 * the updated sales values in the database.
	 */
	static {
		Connection connection = ConnectionManager.getConnection(true);
		SalesValueRepository repository = new SalesValueRepository(connection);
		try {
			alreadyReadSales = repository.findAll();
			calculateRelativePercentage(alreadyReadSales);
			for (SalesValue salesValue : alreadyReadSales) {
				repository.update(salesValue);
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * Returns the sales values.
	 *
	 * @return a list of sales values
	 * @throws Exception if sales values cannot be obtained
	 */
	public List<SalesValue> getSalesValueList() throws Exception {
		if (alreadyReadSales == null) {
			throw new UnsupportedOperationException();
		}
		return alreadyReadSales;

	}

	private static void calculateRelativePercentage(List<SalesValue> alreadyReadSales) {
		double sum = 0;
		for (SalesValue salesValue : alreadyReadSales) {
			sum += salesValue.getNumber();
		}

		for (SalesValue salesValue : alreadyReadSales) {
			double percentage = salesValue.getNumber() / sum * 100;
			salesValue.setPercentage(percentage);
		}
	}
}