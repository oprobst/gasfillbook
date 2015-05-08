package de.tsvmalsch.client.composite;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.tsvmalsch.client.Constants;
import de.tsvmalsch.client.DefaultAsyncCallback;
import de.tsvmalsch.client.UserService;
import de.tsvmalsch.client.UserServiceAsync;
import de.tsvmalsch.shared.model.BlendingType;
import de.tsvmalsch.shared.model.Member;
import de.tsvmalsch.shared.model.UserRights;

public class FillPanelComposite extends Composite {

	private VerticalPanel vp = null;

	private final UserServiceAsync userService = GWT.create(UserService.class);

	/*
	 * private class FillCylinderClickHandler implements ClickHandler {
	 * 
	 * @Override public void onClick(ClickEvent event) { ctxComp.add(new
	 * Label("FillCylinder")); } };
	 */
	private class UserDataClickHandler implements ChangeHandler {
		public void onChange(com.google.gwt.event.dom.client.ChangeEvent event) {

			cylinderSelectComposite.setVisible(false);

			int index = cboChooseAction.getSelectedIndex();

			switch (index) {
			case 0:// own cylinder
				selectOtherMemberComposite.setVisible(false);
				cylinderSelectComposite.setVisible(true);
				selectOtherMemberComposite.enable(false);
				return;
			case 1:// club cylinder
				cylinderSelectComposite.setVisible(true);
				selectOtherMemberComposite.setVisible(false);
				selectOtherMemberComposite.enable(false);
				return;
			case 2:// cylinder of other member
				selectOtherMemberComposite.setVisible(true);
				cylinderSelectComposite.setVisible(true);
				selectOtherMemberComposite.enable(true);
				return;
			default:
				throw new RuntimeException("Unknown ListBox selection index "
						+ index);
			}

		};
	};

	ListBox cboChooseAction = new ListBox();

	Button btnUserData = new Button();

	public FillPanelComposite() {

		vp = new VerticalPanel();
		HorizontalPanel hp = new HorizontalPanel();
		Label lblSelectAction = new Label("Ich möchte ");
		hp.add(lblSelectAction);

		cboChooseAction.addChangeHandler(new UserDataClickHandler());
		cboChooseAction.addItem("meine eigene Flasche füllen.");
		cboChooseAction.addItem("eine Vereinsflasche füllen.");
		cboChooseAction.addItem("die Flasche eines anderen Mitglieds füllen.");
		hp.add(cboChooseAction);

		vp.add(hp);

		vp.add(selectOtherMemberComposite);
		vp.add(cylinderSelectComposite);
 
		tp = new TabLayoutPanel(2.6, Unit.EM);
		tp.setAnimationDuration(200);
		tp.getElement().getStyle().setMarginTop(18.0, Unit.PX);

		tp.setHeight(Constants.GLOBAL_HEIGHT_STRING);
		tp.setWidth(Constants.GLOBAL_WIDTH_STRING);

		vp.add(tp);

		selectOtherMemberComposite.setVisible(false);
		cylinderSelectComposite.setVisible(true);

		initWidget(vp);
		userService.getCurrentMember(new AsyncCallbackGetCurrentMember());

	}

	private TabLayoutPanel tp;

	private CylinderSelectComposite cylinderSelectComposite = new CylinderSelectComposite();
	private SelectOtherMemberComposite selectOtherMemberComposite = new SelectOtherMemberComposite(
			cylinderSelectComposite);

	class AsyncCallbackGetCurrentMember extends DefaultAsyncCallback<Member> {

		public void onSuccess(Member result) {

			UserRights userRights = result.getRights();

			if (userRights.isAllowedToFillAir()) {
				GasBlendingComposite gbc = new GasBlendingComposite(
						BlendingType.AIR);
				gbc.setFillingMember(result);
				tp.add(gbc, "Luft");
				cylinderSelectComposite.addCylinderSelectedListener(gbc);
				selectOtherMemberComposite.addAccountedMemberListener(gbc);
			}
			if (userRights.isAllowedToFillNx40()) {
				GasBlendingComposite gbc = new GasBlendingComposite(
						BlendingType.NX40_CASCADE);
				gbc.setFillingMember(result);
				tp.add(gbc, "Nx40 Kaskade");
				cylinderSelectComposite.addCylinderSelectedListener(gbc);
				selectOtherMemberComposite.addAccountedMemberListener(gbc);
			}
			if (userRights.isAllowedToFillPartial()) {
				GasBlendingComposite gbc = new GasBlendingComposite(
						BlendingType.PARTIAL_METHOD);
				gbc.setFillingMember(result);
				tp.add(gbc, "Partial Methode");
				selectOtherMemberComposite.addAccountedMemberListener(gbc);
				cylinderSelectComposite.addCylinderSelectedListener(gbc);

			}
			if (userRights.isAllowedToFillMixer()) {
				// tp.add(new GasBlendingComposite(BlendingType.MIXER),
				// "Mixer");
			}
			selectOtherMemberComposite.setLoggedInUser(result);
			selectOtherMemberComposite.setCurrentMember(result);
			selectOtherMemberComposite.setVisible(false);
			selectOtherMemberComposite.enable(false);

		};
	}

}