package de.tsvmalsch.client.composite;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DefaultDateTimeFormatInfo;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.tsvmalsch.client.DefaultAsyncCallback;
import de.tsvmalsch.client.UserService;
import de.tsvmalsch.client.UserServiceAsync;
import de.tsvmalsch.shared.model.Member;
import de.tsvmalsch.shared.model.UserRights;

public class UserDataComposite extends Composite {

	private Label lblHeader = new Label("User Data");
	private Label lblEMail = new Label("E-Mail");
	private Label lblFirstName = new Label("First name");
	private Label lblLastName = new Label("Last name");
	private Label lblMaximumDebt = new Label("Max. debt");
	private Label lblNewPassword = new Label("New password");
	private Label lblNewPassword2 = new Label("Confirm password");
	private Label lblHasGasBlender = new Label("Has gas blender brevet");
	private Label lblLastBriefing = new Label("Last briefing");
	private Label lblIsAuthFillAir = new Label("Authorization to fill air");
	private Label lblIsAuthFillNx40 = new Label("Authorization partial Nx40");
	private Label lblIsAuthFillO2 = new Label("Authorization partial O2");
	private Label lblIsAuthFillMix = new Label("Authorization use Mixer");

	private TextBox txbEmail = new TextBox();
	private TextBox txbFirstName = new TextBox();
	private TextBox txbLastName = new TextBox();
	private TextBox txbMaximumDebt = new TextBox();
	private TextBox txbNewPassword = new PasswordTextBox();
	private TextBox txbNewPassword2 = new PasswordTextBox();
	private CheckBox chkHasGasblender = new CheckBox();

	private TextBox txbLastBriefing = new TextBox();
	private CheckBox chkIsAuthFillAir = new CheckBox();
	private CheckBox chkIsAuthFillNx40 = new CheckBox();
	private CheckBox chkIsAuthFillO2 = new CheckBox();
	private CheckBox chkIsAuthFillMix = new CheckBox();

	Button btnConfirm = new Button("Confirm");
	Button btnReset = new Button("Reset");

	public UserDataComposite() {
		userService.getCurrentMember(new AsyncCallbackGetCurrentMember());
		setWidth("600px");
		VerticalPanel vp = new VerticalPanel();

		vp.add(lblHeader);
		FlexTable t = new FlexTable();

		vp.add(t);

		t.setWidget(0, 0, lblEMail);
		t.setWidget(1, 0, lblFirstName);
		t.setWidget(2, 0, lblLastName);
		t.setWidget(3, 0, lblMaximumDebt);
		t.setWidget(4, 0, lblHasGasBlender);
		t.setWidget(5, 0, lblNewPassword);
		t.setWidget(6, 0, lblNewPassword2);
		t.setWidget(7, 0, lblLastBriefing);
		t.setWidget(8, 0, lblIsAuthFillAir);
		t.setWidget(9, 0, lblIsAuthFillNx40);
		t.setWidget(10, 0, lblIsAuthFillO2);
		t.setWidget(11, 0, lblIsAuthFillMix);

		t.setWidget(0, 1, txbFirstName);
		t.setWidget(1, 1, txbLastName);
		t.setWidget(2, 1, txbEmail);
		t.setWidget(3, 1, txbMaximumDebt);
		t.setWidget(4, 1, chkHasGasblender);
		t.setWidget(5, 1, txbNewPassword);
		t.setWidget(6, 1, txbNewPassword2);
		t.setWidget(7, 1, txbLastBriefing);
		t.setWidget(8, 1, chkIsAuthFillAir);
		t.setWidget(9, 1, chkIsAuthFillNx40);
		t.setWidget(10, 1, chkIsAuthFillO2);
		t.setWidget(11, 1, chkIsAuthFillMix);

		chkHasGasblender.setEnabled(false);
		txbLastBriefing.setEnabled(false);
		chkIsAuthFillAir.setEnabled(false);
		chkIsAuthFillNx40.setEnabled(false);
		chkIsAuthFillO2.setEnabled(false);
		chkIsAuthFillMix.setEnabled(false);

		HorizontalPanel hpButtons = new HorizontalPanel();
		hpButtons.add(btnConfirm);
		hpButtons.add(btnReset); 
		vp.add(hpButtons);
		initWidget(vp);
	}

	private final UserServiceAsync userService = GWT.create(UserService.class);

	class AsyncCallbackGetCurrentMember extends DefaultAsyncCallback<Member> {

		public void onSuccess(Member member) {

			String pattern = "MM/yyyy";
			DefaultDateTimeFormatInfo info = new DefaultDateTimeFormatInfo();
			DateTimeFormat dtf = new DateTimeFormat(pattern, info) {
			};
			txbEmail.setText(member.getEmail());
			txbFirstName.setText(member.getFirstName());
			txbLastName.setText(member.getLastName());
			txbMaximumDebt.setText(Integer.toString(member.getMaximumDebit()));
			txbNewPassword.setText("");
			txbNewPassword2.setText("");
			chkHasGasblender.setValue(member.getHasGasblenderBrevet());

			UserRights rights = member.getRights();
			txbLastBriefing.setText(dtf.format(rights.getLastBriefing()));
			chkIsAuthFillAir.setValue(rights.isAllowedToFillAir());
			chkIsAuthFillNx40.setValue(rights.isAllowedToFillNx40());
			chkIsAuthFillO2.setValue(rights.isAllowedToFillPartial());
			chkIsAuthFillMix.setValue(rights.isAllowedToFillMixer());
		};
	}
}