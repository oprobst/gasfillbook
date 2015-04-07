package de.tsvmalsch.server.blend;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.tsvmalsch.client.GasBlenderService;
import de.tsvmalsch.shared.CalcResult;
import de.tsvmalsch.shared.CylinderContents;

@SuppressWarnings("serial")
public class GasBlenderServiceImpl extends RemoteServiceServlet implements
		GasBlenderService {

	// R gas constant
	static final double R = 0.0831451;
	// A and B values for our constituent gases
	static final double O2a = 1.382;
	static final double O2b = 0.03186;
	static final double N2a = 1.370;
	static final double N2b = 0.03870;
	static final double Hea = 0.0346;
	static final double Heb = 0.02380;
	// % of O2 in Air
	static final double O2inAir = 0.2095;
	// The offset to convert 'C into 'K. (roughly..)
	static final double KelvinOffset = 273.15;

	// This class represents a and b value pair, as returned by the CalcABVals
	// method.
	private class ABValPair {
		public double a;
		public double b;
	}

	// put in the ratios of the gases and get back mixed a,b
	private ABValPair CalcABVals(double o2Ratio, double n2Ratio, double heRatio) {
		double total = o2Ratio + n2Ratio + heRatio;
		double[] fractionMols = new double[3];
		fractionMols[0] = o2Ratio / total;
		fractionMols[1] = n2Ratio / total;
		fractionMols[2] = heRatio / total;

		double[] AVals = { O2a, N2a, Hea };
		double[] BVals = { O2b, N2b, Heb };
		ABValPair results = new ABValPair();

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				results.a += Math.sqrt(AVals[i] * AVals[j]) * fractionMols[i]
						* fractionMols[j];
				results.b += Math.sqrt(BVals[i] * BVals[j]) * fractionMols[i]
						* fractionMols[j];
			}
		}
		return results;
	}

	// put in volume, pressure, temp (abs) plus the a,b constants
	// and get back mols
	static double Mols(double Volume, double Pressure, double TempKelvin,
			double a, double b) {
		// Ideal gas law used as a starting point. n = PV/RT
		double n = Pressure * Volume / (R * TempKelvin);
		double step = n / 100; // Use a small step.
		boolean side = false;
		boolean ns;
		double calcPressure;

		while (true) {
			calcPressure = pressure(Volume, n, TempKelvin, a, b);
			ns = calcPressure < Pressure;
			if (Math.abs(calcPressure - Pressure) < 0.01) {
				// Got a value that is near enough - success
				return n;
			} else {
				// Continue to search
				n += ns ? step : -step;
				if (ns != side)
					step = step * 0.9;
				side = ns;
			}
		}
	}

	static double pressure(double V, double n, double TempKelvin, double a,
			double b) {
		return n * R * (TempKelvin) / (V - n * b) - n * n * a / (V * V);
	}

	@Override
	public CalcResult calc(CylinderContents StartCyl,
			CylinderContents TargetCyl, double CylVolume, int TempCelcius,
			boolean AddHeFirst) {
		// We start with an amount of gas already in the tank so we can factor
		// it
		// Remember that 1 mol of oxygen + 1 mol of nitrogen give a 50% mix
		// which
		// is why we work in mols even though the volume cancels out.
		double startFHe = StartCyl.FHe;
		double startFO2 = StartCyl.FO2; // we will start off with one bar of
										// air.
		double startPressure = StartCyl.Pressure;

		double targetFHe = TargetCyl.FHe;
		double targetFO2 = TargetCyl.FO2;
		double targetPressure = TargetCyl.Pressure;

		// Everything internal works in Kelvin
		double TempKelvin = TempCelcius + KelvinOffset;

		// Mols in cylinder currently, mols we want.
		double startMols, targetMols;
		startMols = targetMols = 0;
		// Mols of each constituent gas
		double molsN2, molsO2, molsHe;
		molsN2 = molsO2 = molsHe = 0;

		// Sanity check our arguments.

		if (startFHe + startFO2 > 1.0 || targetFHe + targetFO2 > 1.0) {
			CalcResult r = new CalcResult();
			r.successfull = false;
			r.failureSting = "Ein Gas mit " + targetFHe * 100 + "% He und "
					+ targetFO2 + "% O2 Anteil (Summe= " + (targetFHe + targetFO2)
					* 100 + "%) kann nur von User 'Chuck Norris' gemischt werden!";
			return r;
		}

		if (startPressure > targetPressure) {
			// We need to bleed down, at a minimum to make p1 = p2,
			// so we start there now.
			startPressure = targetPressure;
		}
		ABValPair results;
		// Calculate a + b and mols values for initial mix
		results = CalcABVals(startFO2, 1 - startFO2 - startFHe, startFHe);
		// get a and b for this mix
		startMols = Mols(CylVolume, startPressure, TempKelvin, results.a,
				results.b); // get the initial mols
		// Same for our target mix
		results = CalcABVals(targetFO2, 1 - targetFO2 - targetFHe, targetFHe);
		// a and b for this trimix mix
		targetMols = Mols(CylVolume, targetPressure, TempKelvin, results.a,
				results.b); // get the final mols

		while (true) {
			// Working in nitrogen to get the air top off
			// The initial fill contained n1*(1-fo1-fh1) mols of nitrogen
			// and the final mix contained nt*(1-fo2-fh2) mols so we added
			molsN2 = targetMols * (1 - targetFO2 - targetFHe) - startMols
					* (1 - startFO2 - startFHe); // mols nitrogen in the top off
													// air.

			if (molsN2 >= 0) {
				// But that would contain n2*0.21/0.79 mols of free oxygen and
				// we started with
				// n1*fo1 mols of oxygen so we have to add an extra...
				molsO2 = targetMols * targetFO2 - startMols * startFO2 - molsN2
						* O2inAir / (1 - O2inAir); // mols of oxygen
				if (molsO2 > -0.01 && molsO2 < 0.01)
					molsO2 = 0;
				if (molsO2 >= 0) { // Initial ox mix NOT too rich
					// Helium is easier because there is none in the air we pump
					// so we add
					molsHe = targetMols * targetFHe - startMols * startFHe; // mols
																			// of
																			// helium
					if (molsHe > -0.01 && molsHe < 0.01)
						molsHe = 0;
					if (molsHe >= 0) { // initial Helium mix not too rich.
						// We are done!
						break;
					}
				}
			}
			if (startPressure >= 2.0) {
				// We need to bleed the initial pressure down to make the target
				// achievable.
				startPressure -= 1.0;
			} else {

				// Well, not the most elegant way, but suitable for the first
				// approach.

				StartCyl.Pressure -= 1;
				return calc(StartCyl, TargetCyl, CylVolume, TempCelcius,
						AddHeFirst);

			}
		}

		// These are the running totals of moles of each constituent gas
		// Start off by initialising them to what is in the cylinder to start
		// with
		double totalMolsO2 = startMols * startFO2; // mols of oxygen
		double totalMolsN2 = startMols * (1 - startFO2 - startFHe); // mols of
																	// nitrogen
		double totalMolsHe = startMols * startFHe; // mols of helium

		double gasPercent1, gasPercent2; // Peak %s of each gas.

		// Add our first gas
		if (AddHeFirst) {
			totalMolsHe += molsHe;
			gasPercent1 = totalMolsHe * 100
					/ (totalMolsO2 + totalMolsN2 + totalMolsHe); // peak %He
		} else {
			totalMolsO2 += molsO2;
			gasPercent1 = totalMolsO2 * 100
					/ (totalMolsO2 + totalMolsN2 + totalMolsHe); // peak %O2
		}
		results = CalcABVals(totalMolsO2, totalMolsN2, totalMolsHe); // get the
																		// mix
																		// coeficients
		double p3 = pressure(CylVolume,
				totalMolsO2 + totalMolsN2 + totalMolsHe, TempKelvin, results.a,
				results.b); // first fill pressure
		// Now add the other gas
		if (AddHeFirst) { // ie: oxygen last
			totalMolsO2 += molsO2;
			gasPercent2 = totalMolsO2 * 100
					/ (totalMolsO2 + totalMolsN2 + totalMolsHe);
		} else {
			totalMolsHe += molsHe;
			gasPercent2 = totalMolsHe * 100
					/ (totalMolsO2 + totalMolsN2 + totalMolsHe);
		}
		results = CalcABVals(totalMolsO2, totalMolsN2, totalMolsHe);
		double p4 = pressure(CylVolume,
				totalMolsO2 + totalMolsN2 + totalMolsHe, TempKelvin, results.a,
				results.b); // gives second fill pressure
		// Add the air top that we planned earlier
		totalMolsN2 += molsN2;
		totalMolsO2 += molsN2 * O2inAir / (1 - O2inAir);
		results = CalcABVals(totalMolsO2, totalMolsN2, totalMolsHe);
		double p5 = pressure(CylVolume,
				totalMolsO2 + totalMolsN2 + totalMolsHe, TempKelvin, results.a,
				results.b); // gives final pressure

		// Put everything into the result struct and return it
		CalcResult result = new CalcResult();
		result.StartPressure = (int) startPressure;
		if (AddHeFirst) {
			result.HeAdded = Math.round((p3 - startPressure) * 10) / 10.0;
			result.O2Added = Math.round((p4 - p3) * 10) / 10.0;
			result.peakHePercent = gasPercent1;
			result.peakO2Percent = gasPercent2;
		} else {
			result.O2Added = Math.round((p3 - startPressure) * 10) / 10.0;
			result.HeAdded = Math.round((p4 - p3) * 10) / 10.0;
			result.peakO2Percent = gasPercent1;
			result.peakHePercent = gasPercent2;
		}
		result.AirAdded = Math.round((p5 - p4) * 10) / 10.0;
		result.EndPressure = Math.round((p5) * 10) / 10.0;
		// Brief sanity check - the EndPressure we reached should equal the
		// start pressure
		return result;
	}
}
