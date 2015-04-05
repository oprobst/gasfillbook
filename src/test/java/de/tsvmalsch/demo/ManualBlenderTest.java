package de.tsvmalsch.demo;

import de.tsvmalsch.client.GasBlenderService;
import de.tsvmalsch.server.blend.GasBlenderServiceImpl;
import de.tsvmalsch.shared.CalcResult;
import de.tsvmalsch.shared.CylinderContents;

public class ManualBlenderTest {

	public ManualBlenderTest() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {

		// CalcResult Calc(CylinderContents StartCyl, CylinderContents
		// TargetCyl,
		// double CylVolume, int TempCelcius, boolean AddHeFirst )
		GasBlenderService dgc = new GasBlenderServiceImpl();

		CylinderContents cs = new CylinderContents();
		cs.FHe = 0.35d;
		cs.FO2 = 0.210d;
		cs.Pressure = 100.0d;

		CylinderContents ct = new CylinderContents();
		ct.FHe = 0.45d;
		ct.FO2 = 0.18d;
		ct.Pressure = 210.0d;

		CalcResult r = dgc.calc(cs, ct, 12.0, 1, true);
		System.err.println("Start pressure " + r.StartPressure + " bar\n"
				+ "Top He " + r.HeAdded + " bar\n" + "Top O2 " + r.O2Added
				+ " bar\n" + "Top with AIR to " + r.EndPressure + " bar.");

	}
}
