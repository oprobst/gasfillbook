package de.tsvmalsch.client;

import java.util.Collection;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.tsvmalsch.shared.model.Cylinder;
import de.tsvmalsch.shared.model.Member;

/**
 * Provide access to all cylinders.
 * 
 * @author Oliver Probst
 */
@RemoteServiceRelativePath("cylinder")
public interface CylinderService extends RemoteService {

	Collection<Cylinder> getAllCylinderOf(Member member);

	void addCylinder(Cylinder cylinder);

	void removeCylinder(Cylinder cylinder);
	
}
