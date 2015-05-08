package de.tsvmalsch.client.composite;

import java.util.Collection;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DoubleBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.tsvmalsch.client.ConfigurationService;
import de.tsvmalsch.client.ConfigurationServiceAsync;
import de.tsvmalsch.client.DefaultAsyncCallback;
import de.tsvmalsch.client.UserService;
import de.tsvmalsch.client.UserServiceAsync;
import de.tsvmalsch.shared.MemberToListUtil;
import de.tsvmalsch.shared.model.Configuration;
import de.tsvmalsch.shared.model.Member;

public class AdminComposite extends Composite {

	class AsyncCallbackGetAllMembers extends
			DefaultAsyncCallback<Collection<Member>> {

		public void onSuccess(Collection<Member> result) {
			allMembers = result;
			updateMemberList();
		}
	}

	class AsyncCallbackGetCurrentConfig extends
			DefaultAsyncCallback<Configuration> {

		public void onSuccess(Configuration configuration) {
			config = configuration;
			updateMemberList();
		};
	}

	private Collection<Member> allMembers = null;
	private Button btnConfirm = new Button("Ändern");
	private Button btnReset = new Button("Reset");
	private Configuration config = null;
	private final ConfigurationServiceAsync configService = GWT
			.create(ConfigurationService.class);

	private Label lblEmailNotifications = new Label("Kasse");
	private Label lblListAdmins = new Label("Administratoren");
	private Label lblListBlendingInstr = new Label("Einweisungsbefugte");
	private Label lblO2ContentCascade = new Label("Akt. Kaskadengemisch");
	private Label lblPriceBarLHe = new Label("Preis barL Helium");
	private Label lblPriceBarLO2 = new Label("Preis barL Sauerstoff");
	private Label lblWelcomeText = new Label("Willkommenstext");

	private TextBox txbEmailNotifications = new TextBox();
	private TextBox txbListAdmins = new TextBox();

	private TextBox txbListBlendingInstr = new TextBox();

	private DoubleBox txbO2ContentCascade = new DoubleBox();

	private TextBox txbPriceBarLHe = new TextBox();

	private TextBox txbPriceBarLO2 = new TextBox();

	private TextBox txbWelcomeText = new TextBox();

	private final UserServiceAsync userService = GWT.create(UserService.class);

	public AdminComposite() {

		VerticalPanel vp = new VerticalPanel();
		vp.setWidth("600px");
		vp.add(lblPriceBarLHe);
		FlexTable t = new FlexTable();

		vp.add(t);

		txbPriceBarLHe.setStyleName("txt-5digit");
		txbPriceBarLO2.setStyleName("txt-5digit");
		txbWelcomeText.setStyleName("txbUserData");
		txbO2ContentCascade.setStyleName("txt-5digit");
		txbListBlendingInstr.setStyleName("txbUserData");
		txbListAdmins.setStyleName("txbUserData");
		txbEmailNotifications.setStyleName("txbUserData");

		t.setWidget(0, 0, lblWelcomeText);
		t.setWidget(1, 0, lblPriceBarLO2);
		t.setWidget(2, 0, lblPriceBarLHe);
		t.setWidget(3, 0, lblO2ContentCascade);
		t.setWidget(4, 0, lblListAdmins);
		t.setWidget(5, 0, lblListBlendingInstr);
		t.setWidget(6, 0, lblEmailNotifications);

		t.setWidget(0, 1, txbWelcomeText);
		t.setWidget(1, 1, txbPriceBarLHe);
		t.setWidget(2, 1, txbPriceBarLO2);
		t.setWidget(3, 1, txbO2ContentCascade);
		t.setWidget(4, 1, txbListBlendingInstr);
		t.setWidget(5, 1, txbListAdmins);
		t.setWidget(6, 1, txbEmailNotifications);
		txbO2ContentCascade.setMaxLength(4);
		txbPriceBarLHe.setMaxLength(7);
		txbPriceBarLO2.setMaxLength(7);

		HorizontalPanel hpButtons = new HorizontalPanel();
		hpButtons.add(btnConfirm);
		hpButtons.add(btnReset);
		vp.add(hpButtons);
		initWidget(vp);

		configService
				.getCurrentConfiguration(new AsyncCallbackGetCurrentConfig());
		userService.getAllMembers(new AsyncCallbackGetAllMembers());
	}

	private void updateMemberList() {

		if (config != null && allMembers != null) {

			txbPriceBarLHe
					.setText(Double.toString(config.getPricePerBarLHe()));
			txbPriceBarLO2
					.setText(Double.toString(config.getPricePerBarLO2()));
			txbWelcomeText.setText(config.getWelcomeText());
			txbO2ContentCascade.setValue(config.getNxCascadeOxygen());

			/*String admins = MemberToListUtil
					.memberListToCommaSeparatedString(config
							.getAdministrators());
			String instructors = MemberToListUtil
					.memberListToCommaSeparatedString(config
							.getAdministrators());
			String accountant = MemberToListUtil
					.memberListToCommaSeparatedString(config
							.getAdministrators());

			txbListBlendingInstr.setText(instructors);
			txbListAdmins.setText(admins);
			txbEmailNotifications.setText(accountant);*/
		}
	};
}
