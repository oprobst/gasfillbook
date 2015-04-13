package de.tsvmalsch.server.blend;

import org.junit.Assert;
import org.junit.Test;

import de.tsvmalsch.shared.CalcResult;

public class TestGasBlendingIdealGas extends AbstractGasBlending {

	@Test
	public void test_Drop_Initial_Pressure_Nx34_Nx26() {

		CalcResult expected = new CalcResult(0.0, 0.0, 122.6);
		expected.setStartPressure(77.4);

		startMix = mix(.34, 0.0, 180.0);
		desiredMix = mix(.26, 0.0, 200.0);
		double O2ContentCascade = 0.40;

		CalcResult calculated = gbs.calcIdeal(startMix, desiredMix,
				O2ContentCascade);
		assertMix(expected, calculated, true);
	}

	@Test
	public void test_Drop_Initial_Pressure_To_Zero() {

		CalcResult expected = new CalcResult(0.0, 0.0, 230);
		expected.setStartPressure(0);

		startMix = mix(.34, 0.0, 320.0);
		desiredMix = mix(.2095, 0.0, 230.0);
		double O2ContentCascade = 0.40;

		CalcResult calculated = gbs.calcIdeal(startMix, desiredMix,
				O2ContentCascade);
		Assert.assertTrue(calculated.isSuccessfull());
		assertMix(expected, calculated, true);
	}

	@Test
	public void test_Nx32_Mix() {

		CalcResult expected = new CalcResult(133.2, 0.0, 46.8);

		startMix = mix(.2095, 0.0, 50.0);
		desiredMix = mix(.32, 0.0, 230.0);
		double O2ContentCascade = 0.40;

		CalcResult calculated = gbs.calcIdeal(startMix, desiredMix,
				O2ContentCascade);
		assertMix(expected, calculated);
	}
	
	@Test
	public void test_Nx30_Mix() {

		CalcResult expected = new CalcResult(71.05, 0.0, 78.95);

		startMix = mix(.30, 0.0, 50.0);
		desiredMix = mix(.30, 0.0, 200.0);
		double O2ContentCascade = 0.40;

		CalcResult calculated = gbs.calcIdeal(startMix, desiredMix,
				O2ContentCascade);
		assertMix(expected, calculated);
	}

	@Test
	public void test_Standard_Nx32_Mix_Without_Req_AIR() {

		CalcResult expected = new CalcResult(96.2, 0.0, 0.0);

		startMix = mix(.21, 0.0, 70.0);
		desiredMix = mix(.32, 0.0, 166.3);
		double O2ContentCascade = 0.4;

		CalcResult calculated = gbs.calcIdeal(startMix, desiredMix,
				O2ContentCascade);
		assertMix(expected, calculated);
	}

	/**
	 * In reality, there is no oxygen in cascade, but this test verifies the
	 * algorithm and it do make sense. :-)
	 */
	@Test
	public void test_Standard_Nx32_With_OxygenCascade() {

		// Calculated by DIR Wetnotes
		CalcResult expected = new CalcResult(28.0, 0.0, 122.0);

		startMix = mix(.21, 0.0, 50.0);
		desiredMix = mix(.32, 0.0, 200.0);
		double O2ContentCascade = 1.0;

		CalcResult calculated = gbs.calcIdeal(startMix, desiredMix,
				O2ContentCascade);
		assertMix(expected, calculated);
	}

	@Test
	public void test_Try_Illegal_Mix_Nx20() {

		CalcResult expected = new CalcResult(71.05, 0.0, 78.95);
		expected.StartPressure = 190;

		startMix = mix(.34, 0.0, 10.0);
		desiredMix = mix(.20, 0.0, 200.0);
		double O2ContentCascade = 0.40;

		CalcResult calculated = gbs.calcIdeal(startMix, desiredMix,
				O2ContentCascade);
		Assert.assertFalse(calculated.successfull);
	}

	@Test
	public void test_Try_Illegal_Mix_Nx42() {

		CalcResult expected = new CalcResult(71.05, 0.0, 78.95);
		expected.StartPressure = 190;

		startMix = mix(.34, 0.0, 20.0);
		desiredMix = mix(.42, 0.0, 200.0);
		double O2ContentCascade = 0.40;

		CalcResult calculated = gbs.calcIdeal(startMix, desiredMix,
				O2ContentCascade);
		Assert.assertFalse(calculated.successfull);
	}

}
