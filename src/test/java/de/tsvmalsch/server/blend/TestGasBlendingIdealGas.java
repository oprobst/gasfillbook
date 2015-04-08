package de.tsvmalsch.server.blend;

import org.junit.Assert;
import org.junit.Test;

import de.tsvmalsch.shared.CalcResult;

public class TestGasBlendingIdealGas extends AbstractGasBlending {

	@Test
	public void test_Standard_Nx32_Mix() {

		CalcResult expected = new CalcResult(29.3, 0.0, 150.7);

		startMix = mix(.209, 0.0, 5.0);
		desiredMix = mix(.32, 0.0, 230.0);
		double tanksize = 12.0;
		double O2ContentCascade = 4.0;

		CalcResult calculated = gbs.calcDalton(startMix, desiredMix,
				O2ContentCascade, tanksize);
		assertMix(expected, calculated);
	}

	@Test
	public void test_Standard_Nx32_Mix_Without_Req_AIR() {

		CalcResult expected = new CalcResult(0.0, 0.0, 96.6);

		startMix = mix(.209, 0.0, 70.0);
		desiredMix = mix(.32, 0.0, 166.6);
		double tanksize = 24.0;
		double O2ContentCascade = 4.0;

		CalcResult calculated = gbs.calcDalton(startMix, desiredMix,
				O2ContentCascade, tanksize);
		assertMix(expected, calculated);
	}

	/**
	 * In reality, there is no oxygen in cascade, but this test verifies the
	 * algorithm and it do make sense. :-)
	 */
	@Test
	public void test_Standard_Nx32_With_OxygenCascade() {

		//Calculated by DIR Wetnotes
		CalcResult expected = new CalcResult(28.0, 0.0, 122.0);
		
		startMix = mix(.209, 0.0, 50.0);
		desiredMix = mix(.32, 0.0, 200.0);
		double tanksize = 24.0;
		double O2ContentCascade = 1.0;

		CalcResult calculated = gbs.calcDalton(startMix, desiredMix,
				O2ContentCascade, tanksize);
		assertMix(expected, calculated);
	}

}
