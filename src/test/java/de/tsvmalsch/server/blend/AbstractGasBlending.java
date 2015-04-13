package de.tsvmalsch.server.blend;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.rules.TestName;

import de.tsvmalsch.client.GasBlenderService;
import de.tsvmalsch.shared.CalcResult;
import de.tsvmalsch.shared.CylinderContents;

/**
 * Abstract test class for testing real and ideal gas blending
 *
 */
public class AbstractGasBlending {

	@Rule
	public TestName testName = new TestName();

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
		System.out.println("\nCalculation for test " + testName.getMethodName()
				+ ":");
		System.out.println("Expected:   " + expected.toString());
		System.out.println("Calculated: " + calculated.toString());
		Assert.assertEquals("Calculated helium does not match expected!",
				expected.getHeAdded(), calculated.getHeAdded(), 0.29);

		Assert.assertEquals("Calculated oxygen does not match expected!",
				expected.getO2Added(), calculated.getO2Added(), 0.29);

		Assert.assertEquals("Calculated Helium does not match expected!",
				expected.getAirAdded(), calculated.getAirAdded(), 0.29);

	}

	protected void assertMix(CalcResult expected, CalcResult calculated,
			boolean checkStartPressure) {
		this.assertMix(expected, calculated);
		if (checkStartPressure) {

			Assert.assertEquals(
					"Calculated initial pressure does not match expected!",
					expected.getStartPressure(), calculated.getStartPressure(),
					0.1);
		}

	}

}
