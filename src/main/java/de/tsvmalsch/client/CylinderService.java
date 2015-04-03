package de.tsvmalsch.client;

import java.util.Set;

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

	Set<Cylinder> getAllCylinderOf(Member member);

	void addCylinder(Cylinder cylinder);

	void removeCylinder(Cylinder cylinder);

	void setSelectedCylinder(Cylinder cylinder);

	Cylinder getSelectedCylinder();

}
