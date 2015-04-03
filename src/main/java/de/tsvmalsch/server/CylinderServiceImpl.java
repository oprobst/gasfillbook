package de.tsvmalsch.server;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.tsvmalsch.client.CylinderService;
import de.tsvmalsch.shared.model.Cylinder;
import de.tsvmalsch.shared.model.Member;

@SuppressWarnings("serial")
public class CylinderServiceImpl extends RemoteServiceServlet implements
		CylinderService {

	Logger log = LoggerFactory.getLogger(CylinderServiceImpl.class);

	public CylinderServiceImpl() throws Exception {
		// A SessionFactory is set up once for an application

	}

	@Override
	public Collection<Cylinder> getAllCylinderOf(Member member) {

		Collection<Cylinder> dummy = new LinkedList<Cylinder>();
		Cylinder cy = new Cylinder();
		cy.setName("D12-a");
		cy.setMaximumPreasure(300);
		cy.setNextInspectionDate(new Date(System.currentTimeMillis() + 1000
				* 60 * 24 * 360 * 1000));
		cy.setSizeInLiter(12.0f);
		cy.setOwner(member);
		cy.setSerialNumber("0000124124");
		Cylinder cy2 = new Cylinder();
		cy2.setName("D12-a");
		cy2.setMaximumPreasure(300);
		cy2.setNextInspectionDate(new Date(System.currentTimeMillis() + 1000
				* 60 * 24 * 360 * 1000));
		cy2.setSizeInLiter(12.0f);
		cy2.setOwner(member);
		cy2.setSerialNumber("0000124125");
		cy2.setTwinSetPartner(cy);

		dummy.add(cy);
		dummy.add(cy2);
		for (int j = 0; j < 5; j++) {
			Cylinder cy3 = new Cylinder();
			cy3.setName("S-" + j);
			cy3.setMaximumPreasure(232);
			cy3.setNextInspectionDate(new Date(System.currentTimeMillis()
					+ 1000 * 60 * 24 * (360 - j * 10) * 1000));
			cy3.setSizeInLiter(11.1f);
			cy3.setOwner(member);
			cy3.setSerialNumber("MES123");
			dummy.add(cy3);
		}

		return dummy;
	}

	@Override
	public void addCylinder(Cylinder cylinder) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeCylinder(Cylinder cylinder) {
		// TODO Auto-generated method stub

	}

}
