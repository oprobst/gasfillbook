package de.tsvmalsch.shared;

import java.io.Serializable;

public class CalcResult implements Serializable{
	
	public boolean successfull = true;
	public String failureSting = "";
	
	public int StartPressure; // Might not be the same as what you expected,
	// if the calculator determined a bleed-down was
	// required.
	public double EndPressure;
	public double O2Added;
	public double HeAdded;
	public double AirAdded;
	// These %s are useful if you are using an analyser to check
	// each stage of the mix.
	public double peakHePercent;
	public double peakO2Percent;
}