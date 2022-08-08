package RA;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.math3.stat.regression.SimpleRegression;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

public class RegressionRelates {

	public static void main(String[] args) {
		File f = new File("C:\\javatest\\dataAnalysis\\realestatewithamenity.csv");
		SimpleRegression simplieRegression = new SimpleRegression(true);
		double[] data = new double[2];
		int count = 1;
		double[] titleIdx = { 5, 9, 10, 14, 16, 18, 20, 22, 24, 26, 28 };
		List<List<double[]>> Alldata = new ArrayList<>();

		try {
			CSVReader read = new CSVReader(new FileReader(f));
			String[] readLine;
			String[] title = read.readNext();
			for(int i=0;i<titleIdx.length;i++) {
				List<double[]> foradd = new ArrayList<double[]>();
				double[] ary = {10, titleIdx[i]};
				foradd.add(ary);
				Alldata.add(foradd);
			}

			while ((readLine = read.readNext()) != null) {
				int index = 0;
				data[0] = Double.parseDouble(readLine[8]); // y = price
				data[1] = Double.parseDouble(readLine[5]); // x1 = size
				Alldata.get(index).add(data);
				data[1] = Double.parseDouble(readLine[9]); // x1 = floor
				Alldata.get(index).add(data);
				data[1] = Double.parseDouble(readLine[10]); // x1 = builtyear
				Alldata.get(index).add(data);
				index += 3;

				for (int i = 13; i < 28; i++) {
					int idx = i - 13;
					if (idx % 2 == 1) {
						data[1] = Double.parseDouble(readLine[idx + 13]);
						Alldata.get(index).add(data);
						index++;
					}
				}

				count++;
			}
			
			System.out.println(count);
			for(List<double[]> ary : Alldata) {
				double[][] datas = new double[ary.size()][2];
				for(int i = 0; i < ary.size(); i++) {
					datas[i] = ary.get(i);
				}
				simplieRegression.addData(datas);
				System.out.println(" - price & " + title[(int) titleIdx[Alldata.indexOf(ary)]]);
				System.out.println("Intercept = " + simplieRegression.getIntercept());
				System.out.println("R square " + simplieRegression.getRSquare());
				System.out.println("VIF " + (1 / (1 - simplieRegression.getRSquare())));
				System.out.println("Significance Level " + simplieRegression.getSignificance());
				System.out.println();
			}
		} catch (IOException | CsvValidationException e) {
			e.printStackTrace();
		}
	}
}
