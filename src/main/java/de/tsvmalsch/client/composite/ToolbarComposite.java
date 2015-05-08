package de.tsvmalsch.client.composite;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.tsvmalsch.client.Constants;
import de.tsvmalsch.client.UserService;
import de.tsvmalsch.client.UserServiceAsync;
import de.tsvmalsch.shared.model.Member;

public class ToolbarComposite extends Composite {

	class AsyncCallbackGetCurrentMember implements AsyncCallback<Member> {

		public void onFailure(Throwable caught) {

			logger.log(Level.SEVERE, "Failure when determining member.", caught);

		}

		public void onSuccess(Member result) {
			lblCurrentMemberName.setText("Hallo " + result.getFirstName());

			if (result.getIsAdmin()) {
				tp.add(new AdminComposite(), "Admin");
			}
		};
	}

	private CylinderDataComposite cylinderDataComposite;

	private FillPanelComposite fillPanelComposite;

	private Label lblCurrentAccountBalance = new Label("Kontostand: ? €");
	private Label lblCurrentMemberName = new Label();

	private Logger logger = Logger.getLogger(ToolbarComposite.class
			.getCanonicalName());

	private TabLayoutPanel tp;

	private UserDataComposite userDataComposite;
	private UserFillBookComposite userFillBookComposite;

	/**
	 * Create a remote service proxy to talk to the server-side Greeting
	 * service.
	 */
	private final UserServiceAsync userService = GWT.create(UserService.class);

	public ToolbarComposite() {

		userService.getCurrentMember(new AsyncCallbackGetCurrentMember());

		VerticalPanel vPanel = new VerticalPanel();

		vPanel.setWidth(Constants.GLOBAL_WIDTH_STRING);

		tp = new TabLayoutPanel(2.6, Unit.EM);
		tp.setAnimationDuration(200);

		tp.setHeight(Constants.GLOBAL_HEIGHT_STRING);
		tp.setWidth(Constants.GLOBAL_WIDTH_STRING);

		HorizontalPanel hPanel = new HorizontalPanel();

		hPanel.add(lblCurrentMemberName);
		hPanel.setWidth(Constants.GLOBAL_WIDTH_STRING);
		hPanel.add(lblCurrentAccountBalance);
		vPanel.add(hPanel);

		lblCurrentAccountBalance.setStyleName("current-debit-label");
		lblCurrentMemberName.setStyleName("gwt-login-label");

		userDataComposite = new UserDataComposite();

		fillPanelComposite = new FillPanelComposite();
		cylinderDataComposite = new CylinderDataComposite();
		userFillBookComposite = new UserFillBookComposite();
		tp.add(fillPanelComposite, "Füllen");

		tp.add(userFillBookComposite, "Abrechnung");
		tp.add(cylinderDataComposite, "Flaschen");
		tp.add(userDataComposite, "Stammdaten");

		userFillBookComposite.setToolbar(this);

		vPanel.add(tp);

		initWidget(vPanel);
	}

	public void setCurrentCredit(float amount) {
		lblCurrentAccountBalance.setText("Aktueller Kontostand: " + amount
				+ " €");
	}

}