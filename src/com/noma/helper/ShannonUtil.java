package com.noma.helper;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import java.util.stream.IntStream;

import org.apache.commons.lang3.ArrayUtils;

import com.noma.algorithm.PowerParameter;
import com.noma.entity.BaseStation;
import com.noma.entity.BaseStationManager;
import com.noma.entity.UserEquipment;

import no.uib.cipr.matrix.DenseMatrix;

public class ShannonUtil {
	
	
	static double[]result=null;
	public static double[] gauss(double mean,double variance,int size){
		if(result!=null) {
			return result;
		}
		
		Random r=new Random();
		result=new double[size];
		for(int i=0;i<size;i++) {
			result[i]=(float) (r.nextGaussian()*Math.sqrt(variance)+mean);
		}
		return result;
	}
	
	
	
	/**
	 * A vey specific implementation of shannon capacity for two base stations and three users
	 * @param bm	BaseStationManager which contains distances etc
	 * @param bsX	base station X
	 * @param bsY	base station Y
	 * @param centerA	center user connected to bsX
	 * @param centerB	center user connected to bsY
	 * @param edgeC		edge user connected to both bsX and bsY
	 * @param par		contains power allocations for all users
	 * @return capacity for each user
	 */
	public static HashMap<UserEquipment,Double> getShannonCapacity(
			BaseStationManager bm,
			BaseStation bsX,
			BaseStation bsY, 
			UserEquipment centerA, 
			UserEquipment centerB,
			UserEquipment edgeC,
			PowerParameter par) {
		
		HashMap<UserEquipment,Double>result=new HashMap<>();
		
		double txSNR = bm.gettxSNR();
		
		double powerXA = par.getPower(bsX, centerA);
		double powerXC=1-powerXA;
		
		double powerYB = par.getPower(bsY, centerB);
		double powerYC=1-powerYB;
		
		double[] gainXA = bsX.getChannelGain(centerA);
		double[] gainXB = bsX.getChannelGain(centerB);
		double[] gainXC = bsX.getChannelGain(edgeC);
		
		double[] gainYA = bsY.getChannelGain(centerA);
		double[] gainYB = bsY.getChannelGain(centerB);
		double[] gainYC = bsY.getChannelGain(edgeC);
	
		double[] signalA=MatrixUtils.scalarMultiplyAndAdd(txSNR*powerXA, gainXA,0);
		double[] noiseA=MatrixUtils.scalarMultiplyAndAdd(txSNR*powerYB, gainXB,1);		
		double capaA = centerA.getBandwidth()*log2(1+centerA.getBandwidth()*MatrixUtils.sumPairwiseMultiply(signalA, pinv(noiseA)));
		
		
		double[] signalB=MatrixUtils.scalarMultiplyAndAdd(txSNR*powerYB, gainYB,0);
		double[] noiseB=MatrixUtils.scalarMultiplyAndAdd(txSNR*powerXA, gainYA,1);		
		double capaB = centerB.getBandwidth()*log2(1+centerB.getBandwidth()*MatrixUtils.sumPairwiseMultiply(signalB, pinv(noiseB)));
		
		double[] signalC=
				MatrixUtils.pairwiseAddition(
						MatrixUtils.scalarMultiplyAndAdd(txSNR*powerXC, gainXC,0),
						MatrixUtils.scalarMultiplyAndAdd(txSNR*powerYC, gainYC,0)
				);
		
		double[] noiseC=MatrixUtils.pairwiseAddition(
				MatrixUtils.scalarMultiplyAndAdd(txSNR*powerXA, gainXC,0),
				MatrixUtils.scalarMultiplyAndAdd(txSNR*powerYB, gainYC,1)
				);
				
		double capaC = edgeC.getBandwidth()*log2(1+edgeC.getBandwidth()*MatrixUtils.sumPairwiseMultiply(signalC, pinv(noiseC)));
				
		
		result.put(centerA, capaA);
		result.put(centerB, capaB);
		result.put(edgeC, capaC);
		return result;
	}
		
	public static double log2(double d) {
	      return Math.log(d)/Math.log(2.0);
	   }
	
	public static HashMap<UserEquipment,Double> getShannonCapacity(BaseStationManager bm,  PowerParameter par) {
		throw new UnsupportedOperationException("As of now, general shannon capacity formula is unsupported");
	};
	
	/**
	 * inverse a vector
	 * @param values
	 * @return
	 */
	private static double []pinv(double[] values) {
		int numRows=1;
		int numColumns=values.length;
		boolean deep=true;
		DenseMatrix denseMatrix = new DenseMatrix(numRows, numColumns, values,
		            deep);
		DenseMatrix pseudoInverse = MatrixUtils.pseudoInverse(denseMatrix);
		return pseudoInverse.getData();
	}
	
	
	
	public static ComplexNumber[] getChannelCoefficient(double mean,double variance,int size){
		ComplexNumber[] coeff = new ComplexNumber[size];
		double[] gaussReal = ShannonUtil.gauss(mean, variance, size);
		double[] gaussIm = ShannonUtil.gauss(mean, variance, size);
		IntStream.range(0, size).forEach(i->coeff[i]=new ComplexNumber(gaussReal[i], gaussIm[i]));
		return coeff;
	}
	
	public static double[] getChainnelGain(ComplexNumber[] coeff) {
		Double[] cg=Arrays.stream(coeff).map(c->c.absSquared()).toArray(Double[]::new);
		return ArrayUtils.toPrimitive(cg);
	}
}
