package RA;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;
import org.apache.commons.math3.stat.regression.SimpleRegression;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

public class RegressionRelates2 {

	public static void main(String[] args) {
		File f = new File("C:\\javatest\\dataAnalysis\\realestatewithamenity.csv");
		OLSMultipleLinearRegression regression = new OLSMultipleLinearRegression();
		List<Double> dataY = new ArrayList<>();
		List<double[]> Xdatas = new ArrayList<double[]>();
		int count = 1;
		String[] titles = { "size", "floor", "builtyear", "subwayd", "daycared", "kindergartend", "elementaryd",
				"middled", "highd", "martd", "hospitald" };
		double[] titleIdx = { 5, 9, 10, 14, 16, 18, 20, 22, 24, 26, 28 }; // index nums

		try {
			CSVReader read = new CSVReader(new FileReader(f));
			String[] readLine;
			String[] title = read.readNext();

			while ((readLine = read.readNext()) != null) {
				double[] dataX = new double[6];
				dataY.add(Double.parseDouble(readLine[8])); // y = price
				
				dataX[0] = Double.parseDouble(readLine[5]); // x1 = size
				dataX[1] = Double.parseDouble(readLine[9]); // x2 = floor
				dataX[2] = Double.parseDouble(readLine[10]); // x3 = builtyear
				dataX[3] = Double.parseDouble(readLine[14]); // x4 = subwayd
				dataX[4] = Double.parseDouble(readLine[26]); // x5 = martd
				dataX[5] = Double.parseDouble(readLine[28]); // x6 = hospitald
				Xdatas.add(dataX);

				count++;
			}

			System.out.println(count);
			double[][] datas = new double[Xdatas.size()][6];
			double[] Ydatas = new double[dataY.size()];

			for (int i = 0; i < dataY.size(); i++) {
				datas[i] = Xdatas.get(i);
				Ydatas[i] = dataY.get(i);
			}
			regression.newSampleData(Ydatas, datas);
			System.out.println("R square " + regression.calculateRSquared());
			System.out.println("VIF " + (1 / (1 - regression.calculateRSquared())));
		} catch (IOException | CsvValidationException e) {
			e.printStackTrace();
		}
	}
}
