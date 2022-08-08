package RA;

import org.apache.commons.math3.stat.regression.SimpleRegression;

public class RegressionAnalysis {
	public static void main(String[] args) {
		SimpleRegression simplieRegression = new SimpleRegression(true);
		simplieRegression.addData(new double[][] {
			{ 12, 20 }, 
			{ 23, 30 }, 
			{ 32, 40 }, 
			{ 43, 50 }, 
			{ 52, 60 }, 
		});
		double N = 50.0;
		System.out.println("Intercept = " + simplieRegression.getIntercept());
		System.out.println("Prediction for " + N + " = " + simplieRegression.predict(N));
		System.out.println("R square " + simplieRegression.getRSquare());
		System.out.println("Significance Level " + simplieRegression.getSignificance());
	}
}
