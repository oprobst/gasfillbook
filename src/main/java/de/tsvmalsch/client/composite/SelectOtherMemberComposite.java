package de.tsvmalsch.client.composite;

import java.util.Collection;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextBox;

import de.tsvmalsch.client.DefaultAsyncCallback;
import de.tsvmalsch.client.UserService;
import de.tsvmalsch.client.UserServiceAsync;
import de.tsvmalsch.shared.model.Member;

public class SelectOtherMemberComposite extends Composite {

	private final UserServiceAsync authService = GWT.create(UserService.class);

	private SuggestBox suggestBox = null;
	private TextBox textBoxMemberNumber = null;
	MultiWordSuggestOracle suggestBoxContent = new MultiWordSuggestOracle();

	public SelectOtherMemberComposite() {
		authService.getAllMembersNames(new AsyncCallbackAllMembers());

		HorizontalPanel hp = new HorizontalPanel();

		Label lblUsername = new Label("Name:");
		lblUsername.setStyleName("gwt-Label-Login");
		hp.add(lblUsername);

		suggestBox = new SuggestBox(suggestBoxContent);

		suggestBox.ensureDebugId("cwSuggestBox");
		suggestBox.addValueChangeHandler(new TextBoxMemberNameChangeHandler());
		hp.add(suggestBox);

		Label lblNumber = new Label("or Memberno.:");
		lblNumber.setStyleName("gwt-Label-Login");
		hp.add(lblNumber);

		textBoxMemberNumber = new TextBox();

		textBoxMemberNumber
				.addChangeHandler(new TextBoxMemberNumberChangeHandler());
		hp.add(textBoxMemberNumber);
		initWidget(hp);
	}

	class AsyncCallbackAllMembers extends
			DefaultAsyncCallback<Collection<String>> {

		public void onSuccess(Collection<String> result) {
			suggestBoxContent.addAll(result);
		}
	};

	class TextBoxMemberNumberChangeHandler implements ChangeHandler {
		@Override
		public void onChange(ChangeEvent event) {
			try {
				int i = Integer.parseInt(textBoxMemberNumber.getText());
				authService.getMemberByNumber(i,
						new AsyncCallbackMemberByNumber());
			} catch (NumberFormatException e) {
				return;
			}

		}
	};

	class TextBoxMemberNameChangeHandler implements ValueChangeHandler<String> {

		@Override
		public void onValueChange(ValueChangeEvent<String> event) {
			String name = event.getValue();
			authService.getMemberByName(name, new AsyncCallbackMemberByName());
		}
	};

	class AsyncCallbackMemberByNumber extends DefaultAsyncCallback<Member> {

		public void onSuccess(Member result) {
			setCurrentMember(result);
		}
	};

	class AsyncCallbackMemberByName extends DefaultAsyncCallback<Member> {

		public void onSuccess(Member result) {
			setCurrentMember(result);
		}
	};

	Member cylinderFromMember = null;

	protected void setCurrentMember(Member member) {
		this.cylinderFromMember = member;
		textBoxMemberNumber.setText("" + member.getMemberNumber());
		suggestBox.setText(member.getFirstName() + " " + member.getLastName());
	}

}