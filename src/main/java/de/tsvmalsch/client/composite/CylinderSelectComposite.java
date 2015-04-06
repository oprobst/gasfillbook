package de.tsvmalsch.client.composite;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DefaultDateTimeFormatInfo;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;

import de.tsvmalsch.client.CylinderService;
import de.tsvmalsch.client.CylinderServiceAsync;
import de.tsvmalsch.client.DefaultAsyncCallback;
import de.tsvmalsch.client.UserService;
import de.tsvmalsch.client.UserServiceAsync;
import de.tsvmalsch.client.listener.CurrentCylinderListener;
import de.tsvmalsch.shared.model.Cylinder;
import de.tsvmalsch.shared.model.Member;

public class CylinderSelectComposite extends Composite {

	private Collection<CurrentCylinderListener> cylinderListeners = new LinkedList<>();

	public void addCylinderSelectedListener(CurrentCylinderListener l) {
		cylinderListeners.add(l);
	}

	private HorizontalPanel hp = null;

	private final Label lblInspectionWarning = new Label();

	private final Label lblCylinderSize = new Label();

	private final UserServiceAsync userService = GWT.create(UserService.class);
	private final CylinderServiceAsync cylinderService = GWT
			.create(CylinderService.class);

	public void setCurrentMember(Member m) {
		cylinderOfMember.clear();
		cboSelectCylinder.clear();

		boolean first = true;
		for (Cylinder c : m.getCylinders()) {
			if (first) {
				updateInspectionLabel(c);
				first = false;
			}
			if (!cylinderOfMember.containsKey(c.getUiIdentifier())) {
				cboSelectCylinder.addItem(c.getUiIdentifier());
				cylinderOfMember.put(c.getUiIdentifier(), c);
			}
		}

	}

	class CylinderClickHandler implements ChangeHandler {

		@Override
		public void onChange(ChangeEvent event) {

			lblInspectionWarning.setStyleName("label-warning");

			String key = cboSelectCylinder.getSelectedItemText();

			Cylinder c = cylinderOfMember.get(key);

			for (CurrentCylinderListener l : cylinderListeners) {
				l.cylinderSelected(c);
			}

			cylinderService.setSelectedCylinder(c, new AsyncCallback<Void>() {

				@Override
				public void onSuccess(Void result) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onFailure(Throwable caught) {
					// TODO Auto-generated method stub

				}
			});
			updateInspectionLabel(c);
		}

	};

	private void updateInspectionLabel(Cylinder c) {

		Date nextInsp = c.getNextInspectionDateTwinSet();
		Date today = new Date();

		String pattern = "MM/yyyy";
		DefaultDateTimeFormatInfo info = new DefaultDateTimeFormatInfo();
		DateTimeFormat dtf = new DateTimeFormat(pattern, info) {
		};

		if (today.compareTo(nextInsp) > 0) {
			lblInspectionWarning.setStyleName("label-warning");
			lblInspectionWarning.setText("TÜV abglaufen: "
					+ dtf.format(nextInsp));
		} else {
			lblInspectionWarning.setStyleName("label-ok");
			lblInspectionWarning.setText("TÜV bis: " + dtf.format(nextInsp));
		}
		lblCylinderSize.setText("(" + c.getTwinSetSizeInLiter() + " liter)");
	}

	ListBox cboSelectCylinder = new ListBox();

	public CylinderSelectComposite() {
		hp = new HorizontalPanel();

		Label lblSelectAction = new Label("Fülle Flasche ");
		hp.add(lblSelectAction);

		cboSelectCylinder.addChangeHandler(new CylinderClickHandler());

		hp.add(cboSelectCylinder);
		hp.add(lblCylinderSize);

		hp.add(lblInspectionWarning);
		lblInspectionWarning.setStyleName("label-warning");

		userService.getCurrentMember(new AsyncCallbackGetCurrentMember());

		initWidget(hp);

	}

	class AsyncCallbackGetCurrentMember extends DefaultAsyncCallback<Member> {

		public void onSuccess(Member result) {
			setCurrentMember(result);
		};
	}

	private Map<String, Cylinder> cylinderOfMember = new HashMap<>();
}