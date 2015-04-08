package de.tsvmalsch.server.blend;

import org.junit.Test;

import de.tsvmalsch.shared.CalcResult;

public class TestGasBlendingRealGas extends AbstractGasBlending {

	@Test
	public void test_Standard_Nx32_Mix() {

		CalcResult expected = new CalcResult(29.1, 0.0, 130.9);

		startMix = mix(.209, 0.0, 50.0);
		desiredMix = mix(.32, 0.0, 230.0);
		double tanksize = 12.0;
		int temperature = 20;

		CalcResult calculated = gbs.calcVanDerWaals(startMix, desiredMix,
				tanksize, temperature, true);
		assertMix(expected, calculated);
	}

	@Test
	public void test_Standard_TMX1845_Mix() {

		CalcResult expected = new CalcResult(19.0, 109.5, 51.6);

		startMix = mix(.209, 0.0, 50.0);
		desiredMix = mix(.18, 0.45, 230.0);
		double tanksize = 24.0;
		int temperature = 20;

		CalcResult calculated = gbs.calcVanDerWaals(startMix, desiredMix,
				tanksize, temperature, true);

		System.out.println(calculated);
		assertMix(expected, calculated);
	}
}
