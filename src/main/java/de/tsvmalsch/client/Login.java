package de.tsvmalsch.client;

import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.tsvmalsch.shared.model.Member;

public class Login extends Composite {
	private TextBox textBoxUsername;
	private TextBox textBoxMemberNumber;
	private TextBox textBoxPassword;
	 private Label lblWelcome = new Label("Hallo,");

	Logger logger = Logger.getLogger("NameOfYourLogger");

	/**
	 * Create a remote service proxy to talk to the server-side Greeting
	 * service.
	 */
	private final UserAuthenticationServiceAsync authService = GWT
			.create(UserAuthenticationService.class);

	public Login() {

		authService.getAllMembers(new AsyncCallbackAllMembers());
 
		VerticalPanel verticalPanel = new VerticalPanel();
		initWidget(verticalPanel);

		lblWelcome.setStyleName("gwt-Label-Login");
		verticalPanel.add(lblWelcome);

		Label lblLoginToYour = new Label(
				"Bitte gib Deinen Namen oder deine Mitgliedsnummer ein.");
		lblLoginToYour.setStyleName("gwt-Label-Login");
		verticalPanel.add(lblLoginToYour);

		FlexTable flexTable = new FlexTable();
		verticalPanel.add(flexTable);
		flexTable.setWidth("345px");

		Label lblUsername = new Label("Name:");
		lblUsername.setStyleName("gwt-Label-Login");
		flexTable.setWidget(0, 0, lblUsername);

		textBoxUsername = new TextBox();
		flexTable.setWidget(0, 1, textBoxUsername);

		Label lblNumber = new Label("Mitgliedsnr:");
		lblNumber.setStyleName("gwt-Label-Login");
		flexTable.setWidget(1, 0, lblNumber);

		textBoxMemberNumber = new TextBox();
		flexTable.setWidget(1, 1, textBoxMemberNumber);

		Label lblPassword = new Label("Password:");
		lblPassword.setStyleName("gwt-Label-Login");
		flexTable.setWidget(2, 0, lblPassword);

		textBoxPassword = new PasswordTextBox();
		flexTable.setWidget(2, 1, textBoxPassword);

		Button btnSignIn = new Button("Sign In");
		btnSignIn.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (textBoxUsername.getText().length() == 0
						|| textBoxPassword.getText().length() == 0) {
					Window.alert("Username or password is empty.");
				}

				authService.authenticate(
						Integer.parseInt(textBoxMemberNumber.getText()),
						textBoxPassword.getText(),
						new AsyncCallbackAuthenticate());
				
				RootPanel rootPanel = RootPanel.get();
				rootPanel.clear();
				rootPanel.add(new MainPanel());
			}
		});
		flexTable.setWidget(3, 1, btnSignIn);
	}

	class AsyncCallbackAllMembers implements AsyncCallback<Collection<Member>> {

		public void onFailure(Throwable caught) {
			// Show the RPC error message to the user
			DialogBox dialogBox = new DialogBox();
			dialogBox.setTitle("Remote Procedure Call - Failure");
			dialogBox.setText(caught.getMessage());
			logger.log(Level.SEVERE,
					"Failure when getting all members from db.", caught);

			dialogBox.center();

		}

		public void onSuccess(Collection<Member> result) {
			lblWelcome.setText(result.toString());

		}
	};

	class AsyncCallbackAuthenticate implements AsyncCallback<Boolean> {

		public void onFailure(Throwable caught) {
			// Show the RPC error message to the user
			DialogBox dialogBox = new DialogBox();
			dialogBox.setTitle("Remote Procedure Call - Failure");
			dialogBox.setText(caught.getMessage());
			logger.log(Level.SEVERE, "Failure when authenticating member.",
					caught);

			dialogBox.center();
			caught.printStackTrace();

		}

		public void onSuccess(Boolean result) {
			lblWelcome.setText(result.toString());

		}
	};
}
