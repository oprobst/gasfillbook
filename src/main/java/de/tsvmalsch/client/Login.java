package de.tsvmalsch.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class Login extends Composite {
	private TextBox textBoxUsername;
	private TextBox textBoxPassword;
	private final RootPanel rootPanel;

	public Login(final RootPanel rootPanel) {
		this.rootPanel = rootPanel;
		VerticalPanel verticalPanel = new VerticalPanel();
		initWidget(verticalPanel);

		Label lblWelcome = new Label("Hallo,");
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

		textBoxUsername = new TextBox();
		flexTable.setWidget(1, 1, textBoxUsername);

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

				rootPanel.clear();
				rootPanel.add(new MainPanel());
			}
		});
		flexTable.setWidget(3, 1, btnSignIn);
	}
}
