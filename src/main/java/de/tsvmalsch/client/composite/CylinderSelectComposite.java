package de.tsvmalsch.client.composite;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;

import de.tsvmalsch.client.CylinderService;
import de.tsvmalsch.client.CylinderServiceAsync;
import de.tsvmalsch.client.DefaultAsyncCallback;
import de.tsvmalsch.client.UserService;
import de.tsvmalsch.client.UserServiceAsync;
import de.tsvmalsch.shared.model.Cylinder;
import de.tsvmalsch.shared.model.Member;

public class CylinderSelectComposite extends Composite {

	private HorizontalPanel hp = null;

	private final UserServiceAsync userService = GWT.create(UserService.class);
	private final CylinderServiceAsync cylinderService = GWT
			.create(CylinderService.class);

	/*
	 * private class FillCylinderClickHandler implements ClickHandler {
	 * 
	 * @Override public void onClick(ClickEvent event) { ctxComp.add(new
	 * Label("FillCylinder")); } };
	 */
	private class CylinderClickHandler implements ChangeHandler {
		public void onChange(com.google.gwt.event.dom.client.ChangeEvent event) {

		};
	};

	ListBox cboSelectCylinder = new ListBox();

	Button btnUserData = new Button();

	public CylinderSelectComposite() {
		hp = new HorizontalPanel();

		Label lblSelectAction = new Label("Fill Cylinder ");
		hp.add(lblSelectAction);

		cboSelectCylinder.addChangeHandler(new CylinderClickHandler());

		hp.add(cboSelectCylinder);

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