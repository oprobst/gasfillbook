package de.tsvmalsch.shared;

import java.io.Serializable;

/**
 * Result of a gas blending calculation.
 * 
 *
 */
public class CalcResult implements Serializable {

	/**
	 * sUID
	 */
	private static final long serialVersionUID = 1506270697848837016L;

	public double AirAdded;

	public double EndPressure;

	public String failureSting = "";

	public double HeAdded;

	public double O2Added;
	// These %s are useful if you are using an analyser to check
	// each stage of the mix.
	public double peakHePercent;
	public double peakO2Percent;
	public int StartPressure;

	public boolean successfull = true;
	public CalcResult() {

	}

	public CalcResult(double o2Added, double heAdded, double airAdded) {
		this.HeAdded = heAdded;
		this.O2Added = o2Added;
		this.AirAdded = airAdded;
	}

	public double getAirAdded() {
		return AirAdded;
	}

	public double getEndPressure() {
		return EndPressure;
	}

	public String getFailureSting() {
		return failureSting;
	}

	public double getHeAdded() {
		return HeAdded;
	}

	public double getO2Added() {
		return O2Added;
	}

	public double getPeakHePercent() {
		return peakHePercent;
	}

	public double getPeakO2Percent() {
		return peakO2Percent;
	}

	public int getStartPressure() {
		return StartPressure;
	}

	public boolean isSuccessfull() {
		return successfull;
	}

	public void setAirAdded(double airAdded) {
		AirAdded = airAdded;
	}

	public void setEndPressure(double endPressure) {
		EndPressure = endPressure;
	}

	public void setFailureSting(String failureSting) {
		this.failureSting = failureSting;
	}

	public void setHeAdded(double heAdded) {
		HeAdded = heAdded;
	}

	public void setO2Added(double o2Added) {
		O2Added = o2Added;
	}

	public void setPeakHePercent(double peakHePercent) {
		this.peakHePercent = peakHePercent;
	}

	public void setPeakO2Percent(double peakO2Percent) {
		this.peakO2Percent = peakO2Percent;
	}

	public void setStartPressure(int startPressure) {
		StartPressure = startPressure;
	}

	public void setSuccessfull(boolean successfull) {
		this.successfull = successfull;
	}

	@Override
	public String toString() {
		return "CalcResult [AirAdded=" + AirAdded + ", EndPressure="
				+ EndPressure + ", failureSting=" + failureSting + ", HeAdded="
				+ HeAdded + ", O2Added=" + O2Added + ", peakHePercent="
				+ peakHePercent + ", peakO2Percent=" + peakO2Percent
				+ ", StartPressure=" + StartPressure + ", successfull="
				+ successfull + "]";
	}
}