package de.tsvmalsch.server.blend;

import org.junit.Assert;

import de.tsvmalsch.client.GasBlenderService;
import de.tsvmalsch.shared.CalcResult;
import de.tsvmalsch.shared.CylinderContents;

/**
 * Abstract test class for testing real and ideal gas blending
 *
 */
public class AbstractGasBlending {

	protected CylinderContents desiredMix = new CylinderContents();

	protected final GasBlenderService gbs = new GasBlenderServiceImpl();

	protected CylinderContents startMix = new CylinderContents();

	protected CylinderContents mix(double o2, double he, double p) {
		CylinderContents c = new CylinderContents();
		c.setfHe(he);
		c.setfO2(o2);
		c.setPressure(p);
		return c;
	}

	protected void assertMix(CalcResult expected, CalcResult calculated) {
		Assert.assertEquals("Calculated helium does not match expected!",
				expected.getHeAdded(), calculated.getHeAdded(), 0.04);

		Assert.assertEquals("Calculated oxygen does not match expected!",
				expected.getO2Added(), calculated.getO2Added(), 0.04);

		Assert.assertEquals("Calculated Helium does not match expected!",
				expected.getAirAdded(), calculated.getAirAdded(), 0.04);
	}

}
