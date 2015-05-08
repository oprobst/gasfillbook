package de.tsvmalsch.server;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
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
	
	private final Session session;

	public CylinderServiceImpl() throws Exception {
		session = HibernateUtil.getSessionFactory().getCurrentSession();

	}

	@Override
	public Set<Cylinder> getAllCylinderOf(Member member) {

		HashSet<Cylinder> dummy = new HashSet<Cylinder>();
		Cylinder cy = new Cylinder();
		cy.setName("D12-a");
		cy.setMaximumPreasure(300);
		cy.setNextInspectionDate(new Date(System.currentTimeMillis() + 1000
				* 60 * 24 * 360 * 1000));
		cy.setSizeInLiter(12.0d);
		cy.setOwner(member);
		cy.setSerialNumber("0000124124");
		Cylinder cy2 = new Cylinder();
		cy2.setName("D12-a");
		cy2.setMaximumPreasure(300);
		cy2.setNextInspectionDate(new Date(System.currentTimeMillis() + 1000
				* 60 * 24 * 360 * 1000));
		cy2.setSizeInLiter(12.0d);
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
			cy3.setSizeInLiter(11.1d);
			cy3.setOwner(member);
			cy3.setSerialNumber("MES123");
			dummy.add(cy3);
		}

		member.setCylinders(dummy);
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

	@Override
	public void setSelectedCylinder(Cylinder cylinder) {
		HttpServletRequest request = this.getThreadLocalRequest();
		HttpSession session = request.getSession(true);
		session.setAttribute("currentCylinder", cylinder);
	}

	@Override
	public Cylinder getSelectedCylinder() {
		HttpServletRequest request = this.getThreadLocalRequest();
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("currentCylinder") == null)
			return null;

		Cylinder m = (Cylinder) session.getAttribute("currentCylinder");
		return m;
	}

}
