package de.tsvmalsch.client;

import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.tsvmalsch.shared.model.Configuration;
import de.tsvmalsch.shared.model.Member;

public class Login extends Composite {

	class AsyncCallbackAllMembers implements AsyncCallback<Collection<String>> {

		public void onFailure(Throwable caught) {
			// Show the RPC error message to the user
			DialogBox dialogBox = new DialogBox();
			dialogBox.setTitle("Remote Procedure Call - Failure");
			dialogBox.setText(caught.getMessage());
			logger.log(Level.SEVERE,
					"Failure when getting all members from db.", caught);

			dialogBox.center();

		}

		public void onSuccess(Collection<String> result) {
			suggestBoxContent.addAll(result);
		}
	}

	class AsyncCallbackEMailThread implements AsyncCallback<Void> {

		public void onFailure(Throwable t) {
			logger.log(Level.WARNING, "Problem when starting email thread", t);
		}

		@Override
		public void onSuccess(Void result) {
			logger.log(Level.INFO, "Started EMail Thread successfuly");
		}
	}

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
			if (result) {

				parent.userAuthenticated();

			} else {
				DialogBox dialogBox = new DialogBox();
				dialogBox.setTitle("Authentication failed");
				dialogBox.setText("Username or password wrong.");
				dialogBox.center();

			}

		}
	}

	class AsyncCallbackGetCurrentConfig extends
			DefaultAsyncCallback<Configuration> {

		public void onSuccess(Configuration configuration) {
			if (configuration != null
					&& !configuration.getWelcomeText().trim().isEmpty()) {
				lblWelcome.setText(configuration.getWelcomeText());
			}
		};
	}

	class AsyncCallbackMemberByName implements AsyncCallback<Member> {

		public void onFailure(Throwable caught) {
			// Show the RPC error message to the user
			DialogBox dialogBox = new DialogBox();
			dialogBox.setTitle("Remote Procedure Call - Failure");
			dialogBox.setText(caught.getMessage());
			logger.log(Level.SEVERE,
					"Failure when getting all members from db.", caught);

			dialogBox.center();

		}

		public void onSuccess(Member result) {
			setCurrentMember(result);
		}
	}

	class AsyncCallbackMemberByNumber implements AsyncCallback<Member> {

		public void onFailure(Throwable caught) {
			// Show the RPC error message to the user
			DialogBox dialogBox = new DialogBox();
			dialogBox.setTitle("Remote Procedure Call - Failure");
			dialogBox.setText(caught.getMessage());
			logger.log(Level.SEVERE,
					"Failure when getting all members from db.", caught);

			dialogBox.center();

		}

		public void onSuccess(Member result) {
			setCurrentMember(result);
		}
	}

	class TextBoxMemberNameChangeHandler implements BlurHandler {

		@Override
		public void onBlur(BlurEvent event) {
			String name = suggestBox.getText();
			authService.getMemberByName(name, new AsyncCallbackMemberByName());
		}
	}

	class TextBoxMemberNumberChangeHandler implements ChangeHandler {
		@Override
		public void onChange(ChangeEvent event) {
			int i = textBoxMemberNumber.getValue();
			authService.getMemberByNumber(i, new AsyncCallbackMemberByNumber());
		}
	}

	/**
	 * Create a remote service proxy to talk to the server-side Greeting
	 * service.
	 */
	private final UserServiceAsync authService = GWT.create(UserService.class);
	private final ConfigurationServiceAsync configService = GWT
			.create(ConfigurationService.class);
	private final EMailServiceAsync mailService = GWT
			.create(EMailService.class);

	private Member currentMember;

	private Label lblWelcome = new Label("Willkommen an der Füllstation");

	private Logger logger = Logger.getLogger(Login.class.getCanonicalName());

	private final GasFillBook parent;

	private final SuggestBox suggestBox;;

	private MultiWordSuggestOracle suggestBoxContent = new MultiWordSuggestOracle();;

	private IntegerBox textBoxMemberNumber;;

	private TextBox textBoxPassword;

	public Login(GasFillBook gasFillBook) {
		parent = gasFillBook;
		authService.getAllMembersNames(new AsyncCallbackAllMembers());

		VerticalPanel verticalPanel = new VerticalPanel();

		lblWelcome.setStyleName("gwt-Label-Login-Welcome");
		verticalPanel.add(lblWelcome);

		Label lblLoginToYour = new Label(
				"Bitte gib Deinen Namen oder Deine Mitgliedsnummer ein:");
		lblLoginToYour.setStyleName("gwt-Label-Login");
		verticalPanel.add(lblLoginToYour);

		FlexTable flexTable = new FlexTable();
		verticalPanel.add(flexTable);
		flexTable.setWidth("1024px");

		Label lblUsername = new Label("Name:");
		lblUsername.setStyleName("gwt-Label-Login");
		flexTable.setWidget(0, 0, lblUsername);

		suggestBox = new SuggestBox(suggestBoxContent);
		suggestBox.ensureDebugId("cwSuggestBox");

		suggestBox.getValueBox().addBlurHandler(
				new TextBoxMemberNameChangeHandler());
		flexTable.setWidget(0, 1, suggestBox);

		Label lblNumber = new Label("Mitgliedsnr:");
		lblNumber.setStyleName("gwt-Label-Login");
		flexTable.setWidget(1, 0, lblNumber);

		textBoxMemberNumber = new IntegerBox();
		flexTable.setWidget(1, 1, textBoxMemberNumber);
		textBoxMemberNumber
				.addChangeHandler(new TextBoxMemberNumberChangeHandler());
		textBoxMemberNumber.setMaxLength(4);
		textBoxMemberNumber.setStyleName("txt-3digit");

		Label lblPassword = new Label("Password:");
		lblPassword.setStyleName("gwt-Label-Login");
		flexTable.setWidget(2, 0, lblPassword);

		textBoxPassword = new PasswordTextBox();
		flexTable.setWidget(2, 1, textBoxPassword);
		textBoxPassword.addValueChangeHandler(new ValueChangeHandler<String>() {
			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				authService.authenticate(textBoxMemberNumber.getValue(),
						textBoxPassword.getText(),
						new AsyncCallbackAuthenticate());
			}
		});

		Button btnSignIn = new Button("Einloggen");

		btnSignIn.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (suggestBox.getText().length() == 0
						|| textBoxPassword.getText().length() == 0) {
					Window.alert("Username or password is empty.");
				}

				authService.authenticate(textBoxMemberNumber.getValue(),
						textBoxPassword.getText(),
						new AsyncCallbackAuthenticate());
				logger.log(Level.INFO, "Login attempt for user "
						+ textBoxMemberNumber.getValue());

			}
		});

		btnSignIn.setStyleName("button-login");

		verticalPanel.add(btnSignIn);

		initWidget(verticalPanel);

		configService
				.getCurrentConfiguration(new AsyncCallbackGetCurrentConfig());

		mailService.startEmailThread(new AsyncCallbackEMailThread());
	};

	protected Member getCurrentMember() {
		return currentMember;
	};

	protected void setCurrentMember(Member currentMember) {
		this.currentMember = currentMember;
		textBoxMemberNumber.setText("" + currentMember.getMemberNumber());

		suggestBox.setText(currentMember.getFirstName() + " "
				+ currentMember.getLastName());
		textBoxPassword.setText("");
		textBoxPassword.setFocus(true);
	}

}
