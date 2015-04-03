package de.tsvmalsch.client.composite;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.tsvmalsch.client.UserService;
import de.tsvmalsch.client.UserServiceAsync;
import de.tsvmalsch.shared.model.Member;

public class ToolbarComposite extends Composite {

	/**
	 * Create a remote service proxy to talk to the server-side Greeting
	 * service.
	 */
	private final UserServiceAsync userService = GWT.create(UserService.class);

	Logger logger = Logger.getLogger(ToolbarComposite.class.getCanonicalName());

	FillPanelComposite fillPanelComposite = null;

	private class FillCylinderClickHandler implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			fillPanelComposite.setVisible(false);
		}
	};

	private class UserDataClickHandler implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			fillPanelComposite.setVisible(true);

		}
	};

	Button btnFillPanel = new Button();

	Button btnUserData = new Button();
	Button btnFillHistory = new Button();

	Label lblCurrentMemberName = new Label();

	public ToolbarComposite() {

		RootPanel rootPanel = RootPanel.get();
		rootPanel.clear();

		userService.getCurrentMember(new AsyncCallbackGetCurrentMember());

		VerticalPanel vPanel = new VerticalPanel();

		HorizontalPanel hPanel = new HorizontalPanel();

		hPanel.add(lblCurrentMemberName);
		lblCurrentMemberName.setStyleName("gwt-login-label");

		hPanel.add(btnFillPanel);
		btnFillPanel.setText("Fill Cylinder");
		hPanel.add(btnFillHistory);
		btnFillHistory.setText("Fill History");
		hPanel.add(btnUserData);
		btnUserData.setText("Personal Data");

		btnFillPanel.addClickHandler(new FillCylinderClickHandler());
		btnUserData.addClickHandler(new UserDataClickHandler());

		vPanel.add(hPanel);


		fillPanelComposite = new FillPanelComposite();
		vPanel.add(fillPanelComposite);
		
		fillPanelComposite.setVisible(true);
		

		rootPanel.add(vPanel);
		
		initWidget(rootPanel);
		//initWidget(vPanel);
	}

	class AsyncCallbackGetCurrentMember implements AsyncCallback<Member> {

		public void onFailure(Throwable caught) {

			logger.log(Level.SEVERE, "Failure when determining member.", caught);

		}

		public void onSuccess(Member result) {
			lblCurrentMemberName.setText("Hi " + result.getFirstName());
		};
	}

}