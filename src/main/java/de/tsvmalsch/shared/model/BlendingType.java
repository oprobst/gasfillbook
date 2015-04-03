package de.tsvmalsch.shared.model;


/**
 * Enumeration describing the type of gas blending. 
 * 
 * This enumeration is also used to define user rights and is ordered hierarchically. 
 * 
 * @author Oliver Probst
 *
 */
public enum BlendingType {

	/**
	 * Fill pure air from  compressor directly
	 */
	AIR,
	
	 /**
	 * Blend gas using partial preasure method with the NX40 storage cascade only.
	 */
	NX40_CASCADE, 
	 
	 /**
	 * Blend gas using partial pressure method with 100% He and O2.
	 */
	PARTIAL_METHOD,
	 
	 
	 /**
	 * Use the constant partial mixer to blend. 
	 */
	MIXER

}
