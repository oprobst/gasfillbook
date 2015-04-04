package de.tsvmalsch.client.composite;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DefaultDateTimeFormatInfo;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;

import de.tsvmalsch.client.CylinderService;
import de.tsvmalsch.client.CylinderServiceAsync;
import de.tsvmalsch.client.DefaultAsyncCallback;
import de.tsvmalsch.client.UserService;
import de.tsvmalsch.client.UserServiceAsync;
import de.tsvmalsch.shared.model.Cylinder;
import de.tsvmalsch.shared.model.Member;

public class CylinderSelectComposite extends Composite {

	private HorizontalPanel hp = null;

	private final Label lblInspectionWarning = new Label(" ");

	private final UserServiceAsync userService = GWT.create(UserService.class);
	private final CylinderServiceAsync cylinderService = GWT
			.create(CylinderService.class);

	class CylinderClickHandler implements ChangeHandler {

		@Override
		public void onChange(ChangeEvent event) {

			String key = cboSelectCylinder.getSelectedItemText();
			Cylinder c = cylinderOfMember.get(key);
			cylinderService.setSelectedCylinder(c, null);

			Date nextInsp = c.getNextInspectionDate();
			Date today = new Date();

			String pattern = "MM/yyyy";
			DefaultDateTimeFormatInfo info = new DefaultDateTimeFormatInfo();
			DateTimeFormat dtf = new DateTimeFormat(pattern, info) {
			};

			if (today.compareTo(nextInsp) > 0) {
				lblInspectionWarning.setStyleName("label-warning");
				lblInspectionWarning.setText("TÜV invalid: "
						+ dtf.format(nextInsp));
			} else {
				lblInspectionWarning.setStyleName("label-ok");
				lblInspectionWarning.setText("TÜV till: "
						+ dtf.format(nextInsp));
			}
		}

	};

	ListBox cboSelectCylinder = new ListBox();

	public CylinderSelectComposite() {
		hp = new HorizontalPanel();

		Label lblSelectAction = new Label("Fill Cylinder ");
		hp.add(lblSelectAction);

		cboSelectCylinder.addChangeHandler(new CylinderClickHandler());

		hp.add(cboSelectCylinder);

		hp.add(lblInspectionWarning);
		lblInspectionWarning.setStyleName("label-warning");

		Label lblStartPressure = new Label("Start: ");
		hp.add(lblStartPressure);

		TextBox txbStartPreasure = new TextBox();
		hp.add(txbStartPreasure);

		Label lblBar = new Label("bar");
		hp.add(lblBar);

		userService.getCurrentMember(new AsyncCallbackGetCurrentMember());

		initWidget(hp);

	}

	class AsyncCallbackGetAllCylinders extends
			DefaultAsyncCallback<Set<Cylinder>> {
		@Override
		public void onSuccess(Set<Cylinder> cylinders) {

			for (String key : cylinderOfMember.keySet()) {
				cboSelectCylinder.addItem(key);
			}

		}
	}

	class AsyncCallbackGetCurrentMember extends DefaultAsyncCallback<Member> {

		public void onSuccess(Member result) {

			for (Cylinder c : result.getCylinders()) {
				if (c.getTwinSetPartner() != null
						&& cylinderOfMember
								.containsValue(c.getTwinSetPartner())) {
					// we're fine
				} else if (c.getName() != null && !c.getName().trim().isEmpty()) {
					cylinderOfMember.put(c.getName(), c);
					cboSelectCylinder.addItem(c.getName());
				} else {
					cylinderOfMember.put(c.getSerialNumber(), c);
					cboSelectCylinder.addItem(c.getSerialNumber());
				}
			}
		};
	}

	private Map<String, Cylinder> cylinderOfMember = new HashMap<>();
}