package com.noma.helper;

public class ComplexNumber {
	
	private double real;
	private double imaginary;
	
	public ComplexNumber(double gaussReal, double gaussIm) {
		super();
		this.real = gaussReal;
		this.imaginary = gaussIm;
	}
	
	public double getImaginary() {
		return imaginary;
	}
	
	public void setImaginary(double imaginary) {
		this.imaginary = imaginary;
	}
	
	public double getReal() {
		return real;
	}
	
	public void setReal(double real) {
		this.real = real;
	}
	
	public double absSquared() {
		return real*real+imaginary*imaginary;
	}
	
}
