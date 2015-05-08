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

import de.tsvmalsch.shared.model.Member;

public class Login extends Composite {

	protected Member getCurrentMember() {
		return currentMember;
	}

	protected void setCurrentMember(Member currentMember) {
		this.currentMember = currentMember;
		textBoxMemberNumber.setText("" + currentMember.getMemberNumber());
		suggestBox.setText(currentMember.getFirstName() + " "
				+ currentMember.getLastName());
		textBoxPassword.setText("");
		textBoxPassword.setFocus(true);
	}

	private Member currentMember;

	private IntegerBox textBoxMemberNumber;
	private TextBox textBoxPassword;
	private Label lblWelcome = new Label("Hallo,");
	MultiWordSuggestOracle suggestBoxContent = new MultiWordSuggestOracle();
	final SuggestBox suggestBox;

	Logger logger = Logger.getLogger(Login.class.getCanonicalName());

	/**
	 * Create a remote service proxy to talk to the server-side Greeting
	 * service.
	 */
	private final UserServiceAsync authService = GWT.create(UserService.class);

	private final GasFillBook parent;

	public Login(GasFillBook gasFillBook) {
		parent = gasFillBook;
		authService.getAllMembersNames(new AsyncCallbackAllMembers());

		VerticalPanel verticalPanel = new VerticalPanel();
		initWidget(verticalPanel);

		lblWelcome.setStyleName("gwt-Label-Login");
		verticalPanel.add(lblWelcome);

		Label lblLoginToYour = new Label(
				"bitte gib Deinen Namen oder deine Mitgliedsnummer ein.");
		lblLoginToYour.setStyleName("gwt-Label-Login");
		verticalPanel.add(lblLoginToYour);

		FlexTable flexTable = new FlexTable();
		verticalPanel.add(flexTable);
		flexTable.setWidth("345px");

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
		textBoxMemberNumber.setStyleName(".txt-3digit");

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

		Button btnSignIn = new Button("Sign In");
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
		flexTable.setWidget(3, 1, btnSignIn);
	}

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
	};

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
	};

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

	class TextBoxMemberNumberChangeHandler implements ChangeHandler {
		@Override
		public void onChange(ChangeEvent event) {
			int i = textBoxMemberNumber.getValue();
			authService.getMemberByNumber(i, new AsyncCallbackMemberByNumber());
		}
	};

	class TextBoxMemberNameChangeHandler implements BlurHandler {

		@Override
		public void onBlur(BlurEvent event) {
			String name = suggestBox.getText();
			authService.getMemberByName(name, new AsyncCallbackMemberByName());
		}
	};

}
