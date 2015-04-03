package de.tsvmalsch.shared.model;

/**
 * The cylinder type defines what gas type the cylinder is intended for.
 * 
 * @author Oliver Probst
 *
 */
public enum CylinderType {

	/**
	 * The cylinder is not oxygen cleaned and can be used for gas mixtures <
	 * Nx40
	 */
	AIR,

	/**
	 * Cylinder is intended to be filled with non breathing gas only
	 */
	ARGON,

	/**
	 * The Cylinder and Valve has been oxygen cleaned and can be filled with all
	 * Nx mixtures.
	 */
	OXYCLEAN,

	/**
	 * Cylinder is marked as "Atemgas" according to German Norm. This means that
	 * the cylinder is not for scuba diving, but can be used as top-off gas or
	 * for emergency kits.
	 */
	ATEMGAS,

}
