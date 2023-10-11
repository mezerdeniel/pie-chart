/*
 * Project and Training 2: Pie Chart - Computer Science, Berner Fachhochschule
 */
package ch.bfh.piechart.model;

import ch.bfh.piechart.datalayer.SalesValue;
import ch.bfh.piechart.datalayer.SalesValueLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DataProviderTest {

    private static DataProvider provider = null;

    @BeforeAll
    public static void init() {
       SalesValueLoader.loadSalesValues();
       provider = new DataProvider();
    }

    @Test
    public void testPercentages() throws Exception {
        List<SalesValue> salesValues = provider.getSalesValueList();
        assertTrue(salesValues.size() > 0);
        double sum = 0.0;
        for (SalesValue value : salesValues) {
            sum += value.getPercentage();
            assertNotEquals(0.0, value.getPercentage());
        }
        assertEquals(100.0, sum, SalesValue.PRECISION);
    }
}
