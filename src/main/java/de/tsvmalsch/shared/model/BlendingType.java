package de.tsvmalsch.shared.model;

/**
 * Enumeration describing the type of gas blending.
 * 
 * This enumeration is also used to define user rights and is ordered
 * hierarchically.
 * 
 * @author Oliver Probst
 *
 */
public final class BlendingType {

	private BlendingType() {
	}

	/**
	 * Fill pure air from compressor directly
	 */
	public static final int AIR = 0;

	/**
	 * Blend gas using partial preasure method with the NX40 storage cascade
	 * only.
	 */
	public static final int NX40_CASCADE = 1;

	/**
	 * Blend gas using partial pressure method with 100% He and O2.
	 */
	public static final int PARTIAL_METHOD = 2;

	/**
	 * Use the constant partial mixer to blend.
	 */
	public static final int MIXER = 3;

}
