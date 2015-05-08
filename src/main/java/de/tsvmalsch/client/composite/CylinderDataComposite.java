package de.tsvmalsch.client.composite;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;

import com.google.gwt.cell.client.DateCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DefaultDateTimeFormatInfo;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

import de.tsvmalsch.client.CylinderService;
import de.tsvmalsch.client.CylinderServiceAsync;
import de.tsvmalsch.client.DefaultAsyncCallback;
import de.tsvmalsch.client.UserService;
import de.tsvmalsch.client.UserServiceAsync;
import de.tsvmalsch.shared.model.Cylinder;
import de.tsvmalsch.shared.model.Member;

public class CylinderDataComposite extends Composite {

	
	public CylinderDataComposite() {

		VerticalPanel vp = new VerticalPanel();
		initTable();
		vp.add(table);

		initWidget(vp);

		userService.getCurrentMember(new AsyncCallbackGetCurrentMember());

	}

	CellTable<Cylinder> table = new CellTable<Cylinder>();

	private Widget initTable() {

		table = new CellTable<Cylinder>();
		table.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);

		TextColumn<Cylinder> nameColumn = new TextColumn<Cylinder>() {
			@Override
			public String getValue(Cylinder object) {
				return object.getName();
			}
		};
		nameColumn.setSortable(true);
		table.addColumn(nameColumn, "Name");

		String pattern = "MM/yyyy";
		DefaultDateTimeFormatInfo info = new DefaultDateTimeFormatInfo();
		DateTimeFormat dtf = new DateTimeFormat(pattern, info) {
		};
		DateCell dateCell = new DateCell(dtf);
		Column<Cylinder, Date> dateColumn = new Column<Cylinder, Date>(dateCell) {
			@Override
			public Date getValue(Cylinder object) {
				return object.getNextInspectionDate();
			}
		};

		dateColumn.setSortable(true);
		table.addColumn(dateColumn, "TÜV");

		TextColumn<Cylinder> serialColumn = new TextColumn<Cylinder>() {
			@Override
			public String getValue(Cylinder object) {
				return object.getSerialNumber();
			}
		};
		table.addColumn(serialColumn, "S.Nr.");

		TextColumn<Cylinder> sizeColumn = new TextColumn<Cylinder>() {
			@Override
			public String getValue(Cylinder object) {
				return Double.toString(object.getSizeInLiter());
			}
		};
		table.addColumn(sizeColumn, "Gr.");

		TextColumn<Cylinder> gasType = new TextColumn<Cylinder>() {
			@Override
			public String getValue(Cylinder object) {
				if (object.getGasType() != null) {
					return object.getGasType().toString();
				}
				return "UNKNOWN";
			}
		};
		table.addColumn(gasType, "Gas");

		TextColumn<Cylinder> note = new TextColumn<Cylinder>() {
			@Override
			public String getValue(Cylinder object) {
				return object.getNote();
			}
		};
		table.addColumn(note, "Notiz");

		TextColumn<Cylinder> twin = new TextColumn<Cylinder>() {
			@Override
			public String getValue(Cylinder object) {
				if (object.getTwinSetPartner() != null) {
					return object.getTwinSetPartner().getSerialNumber();
				}
				return "";
			}
		};
		table.addColumn(twin, "Doppel");

		// Add a selection model to handle user selection.
		final SingleSelectionModel<Cylinder> selectionModel = new SingleSelectionModel<Cylinder>();
		table.setSelectionModel(selectionModel);
		selectionModel
				.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
					public void onSelectionChange(SelectionChangeEvent event) {
						Cylinder selected = selectionModel.getSelectedObject();
						if (selected != null) {
							final DialogBox dialogBox = new DialogBox();
							dialogBox.setTitle("Todo");
							dialogBox.setText("TODO: Dialog for configuring "
									+ selected.getSerialNumber());

							dialogBox.center();

							Button ok = new Button("OK");
							ok.addClickHandler(new ClickHandler() {
								public void onClick(ClickEvent event) {
									dialogBox.hide();
								}
							});

							dialogBox.add(ok);
						}
					}
				});

		table.setRowCount(cylinder.size(), true);

		table.setRowData(cylinder);
		return table;
	}

	private final UserServiceAsync userService = GWT.create(UserService.class);

	private static final List<Cylinder> cylinder = new ArrayList<Cylinder>();

	class AsyncCallbackGetCurrentMember extends DefaultAsyncCallback<Member> {

		public void onSuccess(Member member) {

			cylinder.clear();
			cylinder.addAll(member.getCylinders());

			table.setRowData(cylinder);
			table.setRowCount(cylinder.size(), true);

			table.redraw();

		};
	}
}
