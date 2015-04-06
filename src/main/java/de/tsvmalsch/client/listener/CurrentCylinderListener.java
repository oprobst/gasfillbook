package de.tsvmalsch.client.listener;

import de.tsvmalsch.shared.model.Cylinder;

/**
 * Will be notified if a new cylinder was selected.
 * 
 * @author Oliver Probst
 */
public interface CurrentCylinderListener {

	void cylinderSelected(Cylinder currentOne);
}
