package de.tsvmalsch.client.composite;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.cell.client.DateCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DefaultDateTimeFormatInfo;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.tsvmalsch.client.DefaultAsyncCallback;
import de.tsvmalsch.client.UserService;
import de.tsvmalsch.client.UserServiceAsync;
import de.tsvmalsch.shared.CreateDemoDataService;
import de.tsvmalsch.shared.model.Cylinder;
import de.tsvmalsch.shared.model.FillingInvoiceItem;
import de.tsvmalsch.shared.model.Member;

public class UserFillBookComposite extends Composite {

	private Label lblCurrentDebt = new Label("Akt. Kontostand: 32,12 Euro");
	private Label lblAccountCondition = new Label(
			"Nächster Bankeinzug bei 100 Euro oder im Dez 2015");

	public UserFillBookComposite() {

		VerticalPanel vp = new VerticalPanel();
		HorizontalPanel hp = new HorizontalPanel();
		hp.add(lblCurrentDebt);
		hp.add(lblAccountCondition);
		vp.add(hp);

		initTable();
		vp.add(table);

		initWidget(vp);

		userService.getCurrentMember(new AsyncCallbackGetCurrentMember());

	}

	CellTable<FillingInvoiceItem> table;

	private Widget initTable() {

		table = new CellTable<FillingInvoiceItem>();
		table.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);

		// Date
		String pattern = "dd.MM.yyyy";
		DefaultDateTimeFormatInfo info = new DefaultDateTimeFormatInfo();
		final DateTimeFormat dtf = new DateTimeFormat(pattern, info) {
		};
		DateCell dateCell = new DateCell(dtf);
		Column<FillingInvoiceItem, Date> dateColumn = new Column<FillingInvoiceItem, Date>(
				dateCell) {
			@Override
			public Date getValue(FillingInvoiceItem object) {
				return object.getDateOfFilling();
			}
		};

		dateColumn.setSortable(true);
		table.addColumn(dateColumn, "Datum");

		// ID
		TextColumn<FillingInvoiceItem> nameColumn = new TextColumn<FillingInvoiceItem>() {
			@Override
			public String getValue(FillingInvoiceItem object) {
				return Long.toString(object.getId());
			}
		};
		nameColumn.setSortable(true);
		table.addColumn(nameColumn, "ID");

		// Air
		TextColumn<FillingInvoiceItem> airColumn = new TextColumn<FillingInvoiceItem>() {
			@Override
			public String getValue(FillingInvoiceItem object) {
				return Integer.toString(object.getLiterAirFilled());
			}
		};
		table.addColumn(airColumn, "Air(l)");

		// O2
		TextColumn<FillingInvoiceItem> o2Column = new TextColumn<FillingInvoiceItem>() {
			@Override
			public String getValue(FillingInvoiceItem object) {
				return Integer.toString(object.getLiterOxygenFilled());
			}
		};
		table.addColumn(o2Column, "O2(l)");

		// He
		TextColumn<FillingInvoiceItem> heColumn = new TextColumn<FillingInvoiceItem>() {
			@Override
			public String getValue(FillingInvoiceItem object) {
				return Integer.toString(object.getLiterHeliumFilled());
			}
		};
		table.addColumn(heColumn, "He(l)");

		// Price
		TextColumn<FillingInvoiceItem> priceColumn = new TextColumn<FillingInvoiceItem>() {
			@Override
			public String getValue(FillingInvoiceItem object) {
				return Double.toString(object.calculatePrice());
			}
		};
		table.addColumn(priceColumn, "Preis");

		// Cylinder
		TextColumn<FillingInvoiceItem> CylinderColumn = new TextColumn<FillingInvoiceItem>() {
			@Override
			public String getValue(FillingInvoiceItem object) {
				if (object.getFilledCylinder() != null
						&& object.getFilledCylinder().iterator().next() != null) {
					Cylinder cyl = object.getFilledCylinder().iterator().next();
					if (cyl.getName() != null) {
						return cyl.getName();
					} else {
						return cyl.getSerialNumber();
					}
				} else {
					return "";
				}
			}
		};
		table.addColumn(CylinderColumn, "Flasche");

		// Blender
		TextColumn<FillingInvoiceItem> blenderColumn = new TextColumn<FillingInvoiceItem>() {
			@Override
			public String getValue(FillingInvoiceItem object) {
				if (object.getBlendingMember() != null) {
					return Integer.toString(object.getBlendingMember()
							.getMemberNumber());
				}
				return "";
			}
		};
		table.addColumn(blenderColumn, "Füller");

		/*
		 * // Invoice Date
		 * 
		 * TextColumn<FillingInvoiceItem> invoiceColumn = new
		 * TextColumn<FillingInvoiceItem>() {
		 * 
		 * @Override public String getValue(FillingInvoiceItem object) { if
		 * (object.calculatePrice() == 0) { return "free"; } if
		 * (object.getInvoicingDate() == null) { return "offen"; } return
		 * dtf.format(object.getInvoicingDate()); } };
		 * table.addColumn(invoiceColumn, "Rechnung");
		 */
		TextColumn<FillingInvoiceItem> paymentColumn = new TextColumn<FillingInvoiceItem>() {
			@Override
			public String getValue(FillingInvoiceItem object) {
				if (object.calculatePrice() == 0) {
					return "frei";
				}
				if (object.getPaymentReceiptDate() == null) {
					return "offen";
				}
				return dtf.format(object.getPaymentReceiptDate());
			}
		};
		table.addColumn(paymentColumn, "Bezahlt");

		table.setRowCount(fiiList.size(), true);

		table.setRowData(fiiList);
		return table;
	}

	private final UserServiceAsync userService = GWT.create(UserService.class);

	private static final List<FillingInvoiceItem> fiiList = new ArrayList<FillingInvoiceItem>();

	class AsyncCallbackGetCurrentMember extends DefaultAsyncCallback<Member> {

		public void onSuccess(Member member) {

			fiiList.clear();
			fiiList.addAll(CreateDemoDataService.createDummyInvoiceItem(member));

			table.setRowData(fiiList);
			table.setRowCount(fiiList.size(), true);

			table.redraw();

		};
	}
}
