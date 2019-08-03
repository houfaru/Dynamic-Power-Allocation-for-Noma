package com.noma.helper;

import java.util.Arrays;

import no.uib.cipr.matrix.DenseMatrix;
import no.uib.cipr.matrix.NotConvergedException;
import no.uib.cipr.matrix.SVD;

public final class MatrixUtils {
	/**
	 * Computes the Moore-Penrose pseudo-inverse. Based on Octave's implementation:
	 * https://github.com/NexMirror/Octave/blob/6ce11cf520201c66db1135711693af29d3a39d5d/liboctave/array/dMatrix.cc#L652
	 * https://gist.github.com/guimeira/534690afd9fb0c6d4ff07b6335d45e51
	 * 
	 * @author guimiera
	 */
	public static DenseMatrix pseudoInverse(DenseMatrix matrix) {
		SVD svd;

		try {
			svd = SVD.factorize(matrix);
		} catch (NotConvergedException e) {
			throw new RuntimeException(e);
		}

		double[] sVals = svd.getS();
		double tolerance;

		if (matrix.numRows() > matrix.numColumns()) {
			tolerance = matrix.numRows() * sVals[0] * Math.ulp(1.0);
		} else {
			tolerance = matrix.numColumns() * sVals[0] * Math.ulp(1.0);
		}

		int lastValid = sVals.length - 1;

		while (sVals[lastValid] < tolerance) {
			lastValid--;
		}

		if (lastValid < 0) {
			return new DenseMatrix(matrix.numRows(), matrix.numColumns());
		}

		int r = lastValid + 1;

		DenseMatrix sInv = new DenseMatrix(r, r);

		for (int i = 0; i < r; i++) {
			sInv.set(i, i, 1 / sVals[i]);
		}

		DenseMatrix vt = svd.getVt();
		DenseMatrix v = new DenseMatrix(vt.numColumns(), vt.numColumns());
		vt.transpose(v);
		DenseMatrix u = svd.getU();

		DenseMatrix vValid = new DenseMatrix(matrix.numColumns(), r);
		for (int i = 0; i < matrix.numColumns(); i++) {
			for (int j = 0; j < r; j++) {
				vValid.set(i, j, v.get(i, j));
			}
		}

		DenseMatrix uValid = new DenseMatrix(matrix.numRows(), r);
		for (int i = 0; i < matrix.numRows(); i++) {
			for (int j = 0; j < r; j++) {
				uValid.set(i, j, u.get(i, j));
			}
		}

		DenseMatrix c = new DenseMatrix(vValid.numRows(), sInv.numColumns());
		vValid.mult(sInv, c);

		DenseMatrix pinv = new DenseMatrix(c.numRows(), uValid.numRows());
		c.transBmult(uValid, pinv);

		return pinv;
	}

	public static double[] scalarMultiplicationAndAddition(double b, double[] vector, double r) {
		return Arrays.stream(vector).map(v -> v * b + r).toArray();
	}

	public static double[] pairwiseAddition(double[] vector1, double[] vector2) {
		assert (vector1.length == vector2.length);
		double[] res = new double[vector1.length];
		for (int i = 0; i < vector1.length; i++) {
			res[i] = vector1[i] + vector2[i];
		}

		return res;
	}

	/**
	 * Given two arrays a and b, we calculate its sum of pairwise product
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static double sumPairwiseMultiply(double[] a, double[] b) {
		assert (a.length == b.length);
		double sum = 0;
		for (int i = 0; i < a.length; i++) {
			sum += a[i] * b[i];
		}
		return sum;
	}
}