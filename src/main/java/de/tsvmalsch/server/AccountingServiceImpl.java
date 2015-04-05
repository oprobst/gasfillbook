package de.tsvmalsch.server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.tsvmalsch.client.AccountingService;
import de.tsvmalsch.client.UserService;
import de.tsvmalsch.shared.model.FillingInvoiceItem;
import de.tsvmalsch.shared.model.Member;

@SuppressWarnings("serial")
public class AccountingServiceImpl extends RemoteServiceServlet implements
		AccountingService {

	Logger log = LoggerFactory.getLogger(AccountingServiceImpl.class);

	public AccountingServiceImpl() throws Exception {

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

}
