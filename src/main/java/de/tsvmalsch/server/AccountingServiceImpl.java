package de.tsvmalsch.server;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collection;

import org.apache.commons.logging.Log;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gwt.editor.client.Editor.Path;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.tsvmalsch.client.AccountingService;
import de.tsvmalsch.shared.CreateDemoDataService;
import de.tsvmalsch.shared.model.Cylinder;
import de.tsvmalsch.shared.model.FillingInvoiceItem;
import de.tsvmalsch.shared.model.Member;

@SuppressWarnings("serial")
public class AccountingServiceImpl extends RemoteServiceServlet implements
		AccountingService {

	Logger log = LoggerFactory.getLogger(AccountingServiceImpl.class);

	private Session session;

	public AccountingServiceImpl() throws Exception {

	}

	@Override
	public void cancelFillingInvoiceItem(FillingInvoiceItem fii) {
		fii.setValid(false);
		writeToFile(fii);
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.update(fii);
		session.getTransaction().commit();

	}

	@Override
	public Collection<FillingInvoiceItem> getAllInvoiceItemsForMember(Member m) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<FillingInvoiceItem> getOpenInvoiceItemsForMember(Member m) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveFillingInvoiceItem(FillingInvoiceItem fii) {
		try {
			writeToFile(fii);
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			session.save(fii);
			session.getTransaction().commit();
		} catch (Exception e) {
			log.error("Could not write Invoice Item! ", fii);
			log.error("User " + fii.getAccountedMember().getMemberNumber()
					+ " price: " + fii.calculatePrice());
			throw e;
		}
	}

	private void storeFile(Member recipient, String entry) {
		String filename = "000.csv";
		if (recipient != null) {
			filename = recipient.getMemberNumber() + ".csv";
		}

		if (!Files.exists(Paths.get(filename))) {
			String header = "ID,Date,FilledBy, BlendingType, Cylinder, barL He, price He, barL O2, price O2, barL Air, sum price, valid\n";
			try {
				Files.write(Paths.get("./" + filename), header.getBytes(),
						StandardOpenOption.CREATE);
			} catch (IOException e) {
				log.error("Could not write file " + filename, e);
				return;
			}
		}
		try {
			Files.write(Paths.get("./" + filename), entry.getBytes(),
					StandardOpenOption.CREATE, StandardOpenOption.APPEND);
		} catch (IOException e) {
			log.error("Could not write file " + filename, e);

		}
	}

	private void writeToFile(FillingInvoiceItem fii) {

		Member recipient = fii.getAccountedMember();
		StringBuilder sb = new StringBuilder();
		sb.append(fii.getId());
		sb.append(",");
		sb.append(fii.getDateOfFilling());
		sb.append(",");
		if (fii.getBlendingMember() != null) {
			sb.append(fii.getBlendingMember().getMemberNumber());
		}
		sb.append(",");
		sb.append(fii.getBlendingType());
		sb.append(",");
		if (fii.getFilledCylinder() != null) {
			sb.append(fii.getFilledCylinder().getUiIdentifier());
		}
		sb.append(",");
		sb.append(fii.getLiterHeliumFilled());
		sb.append(",");
		sb.append(fii.getPricePerLiterHelium());
		sb.append(",");
		sb.append(fii.getLiterOxygenFilled());
		sb.append(",");
		sb.append(fii.getPricePerLiterOxygen());
		sb.append(",");
		sb.append(fii.getLiterAirFilled());
		sb.append(",");
		sb.append(fii.calculatePrice());
		sb.append(",");
		sb.append(fii.isValid());
		sb.append("\n");

		String entry = sb.toString();
		storeFile(recipient, entry);
	}

}
