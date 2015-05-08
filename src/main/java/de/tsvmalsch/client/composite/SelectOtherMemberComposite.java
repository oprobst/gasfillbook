package de.tsvmalsch.client.composite;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.SuggestBox;

import de.tsvmalsch.client.DefaultAsyncCallback;
import de.tsvmalsch.client.UserService;
import de.tsvmalsch.client.UserServiceAsync;
import de.tsvmalsch.client.listener.CurrentAccountingMemberListener;
import de.tsvmalsch.shared.model.Member;

public class SelectOtherMemberComposite extends Composite {

	class AsyncCallbackAllMembers extends
			DefaultAsyncCallback<Collection<String>> {

		public void onSuccess(Collection<String> result) {
			suggestBoxContent.addAll(result);
		}
	}

	class AsyncCallbackMemberByName extends DefaultAsyncCallback<Member> {

		public void onSuccess(Member result) {
			if (result != null) {
				setCurrentMember(result);
			}
		}
	}

	class AsyncCallbackMemberByNumber extends DefaultAsyncCallback<Member> {

		public void onSuccess(Member result) {
			setCurrentMember(result);
		}
	}

	class CheckBoxHandler implements ValueChangeHandler<Boolean> {

		@Override
		public void onValueChange(ValueChangeEvent<Boolean> event) {
			accountingChanged();
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

	private final UserServiceAsync authService = GWT.create(UserService.class);

	private List<CurrentAccountingMemberListener> currentlyAccountedListener = new LinkedList<CurrentAccountingMemberListener>();

	private final CylinderSelectComposite cylinderSelectComposite;

	private Member loggedInUser;

	public void setLoggedInUser(Member loggedInUser) {
		this.loggedInUser = loggedInUser;
		if (this.selectedMember == null) {
			this.selectedMember = loggedInUser;
		}
	}

	private RadioButton rbtCylinderOwnersAccount = new RadioButton("creditor",
			"Besitzer");

	private RadioButton rbtownAccount = new RadioButton("creditor", "Ich");

	private Member selectedMember;;

	private SuggestBox suggestBox = null;;

	MultiWordSuggestOracle suggestBoxContent = new MultiWordSuggestOracle();;

	private IntegerBox textBoxMemberNumber = null;;

	/**
	 * @param cylinderSelectComposite
	 *            Will be notified if member to fill for changed.
	 */
	public SelectOtherMemberComposite(
			CylinderSelectComposite cylinderSelectComposite) {

		this.cylinderSelectComposite = cylinderSelectComposite;
		authService.getAllMembersNames(new AsyncCallbackAllMembers());

		HorizontalPanel hp = new HorizontalPanel();

		Label lblUsername = new Label("Name:");
		hp.add(lblUsername);

		suggestBox = new SuggestBox(suggestBoxContent);

		suggestBox.ensureDebugId("cwSuggestBox");

		suggestBox.getValueBox().addBlurHandler(
				new TextBoxMemberNameChangeHandler());

		hp.add(suggestBox);

		Label lblNumber = new Label("Mitgl.Nr.:");
		hp.add(lblNumber);

		textBoxMemberNumber = new IntegerBox();

		textBoxMemberNumber.setStyleName("txt-3digit");

		textBoxMemberNumber
				.addChangeHandler(new TextBoxMemberNumberChangeHandler());
		hp.add(textBoxMemberNumber);

		Label lblCharge = new Label("Es zahlt ");
		rbtownAccount.setValue(true);
		rbtownAccount.addValueChangeHandler(new CheckBoxHandler());
		rbtCylinderOwnersAccount.addValueChangeHandler(new CheckBoxHandler());
		hp.add(lblCharge);
		hp.add(rbtownAccount);
		hp.add(rbtCylinderOwnersAccount);

		initWidget(hp);
	};

	private void accountingChanged() {
		if (rbtownAccount.getValue()) {

			notifyListener(loggedInUser);
		} else {
			notifyListener(selectedMember);
		}
	};

	public void addAccountedMemberListener(GasBlendingComposite gbc) {
		this.currentlyAccountedListener.add(gbc);

	}

	private void notifyListener(Member newAccountedUserSelected) {
		for (CurrentAccountingMemberListener caml : this.currentlyAccountedListener) {
			caml.accountedMember(newAccountedUserSelected);
		}

	}

	protected void setCurrentMember(Member member) {
		textBoxMemberNumber.setText("" + member.getMemberNumber());
		suggestBox.setText(member.getFirstName() + " " + member.getLastName());
		cylinderSelectComposite.setCurrentMember(member);
		selectedMember = member;
		accountingChanged();
	}
}