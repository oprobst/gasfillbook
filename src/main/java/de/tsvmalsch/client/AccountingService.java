package de.tsvmalsch.client;

import java.util.Collection;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.tsvmalsch.shared.model.FillingInvoiceItem;
import de.tsvmalsch.shared.model.Member;

@RemoteServiceRelativePath("accounting")
public interface AccountingService extends RemoteService {

	Collection<FillingInvoiceItem> getAllInvoiceItemsForMember(Member m);

	Collection<FillingInvoiceItem> getOpenInvoiceItemsForMember(Member m);

	void saveFillingInvoiceItem(Member recipient, FillingInvoiceItem fii);
 

	void cancelFillingInvoiceItem(Member member, FillingInvoiceItem fii);
}
