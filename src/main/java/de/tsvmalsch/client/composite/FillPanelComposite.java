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

import de.tsvmalsch.client.DefaultAsyncCallback;
import de.tsvmalsch.client.UserService;
import de.tsvmalsch.client.UserServiceAsync;
import de.tsvmalsch.shared.model.BlendingType;
import de.tsvmalsch.shared.model.Member;
import de.tsvmalsch.shared.model.UserRights;

public class FillPanelComposite extends Composite {

	private int currentView = 0;
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
				userService.setMemberToFillFor(-2, null);
				return;
			case 1:// club cylinder
				cylinderSelectComposite.setVisible(true);
				selectOtherMemberComposite.setVisible(false);
				userService.setMemberToFillFor(-1, null);
				return;
			case 2:// cylinder of other member
				selectOtherMemberComposite.setVisible(true);
				cylinderSelectComposite.setVisible(true);
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
		Label lblSelectAction = new Label("I'd like to ");
		hp.add(lblSelectAction);

		cboChooseAction.addChangeHandler(new UserDataClickHandler());
		cboChooseAction.addItem("fill an own cylinder");
		cboChooseAction.addItem("fill a club cylinder");
		cboChooseAction.addItem("fill a cylinder of another member");
		hp.add(cboChooseAction);

		vp.add(hp);

		vp.add(selectOtherMemberComposite);
		vp.add(cylinderSelectComposite);

		vp.add(tp);

		selectOtherMemberComposite.setVisible(false);
		cylinderSelectComposite.setVisible(true);
		userService.setMemberToFillFor(-2,
				new AsyncCallbackSetMemberToFillFor());

		initWidget(vp);
		userService.getCurrentMember(new AsyncCallbackGetCurrentMember());

	}

	TabLayoutPanel tp = new TabLayoutPanel(2.5, Unit.EM);

	CylinderSelectComposite cylinderSelectComposite = new CylinderSelectComposite();
	SelectOtherMemberComposite selectOtherMemberComposite = new SelectOtherMemberComposite();

	class AsyncCallbackSetMemberToFillFor extends DefaultAsyncCallback<Void> {

		@Override
		public void onSuccess(Void result) {
			// fine...
		}

	}

	class AsyncCallbackGetCurrentMember extends DefaultAsyncCallback<Member> {

		public void onSuccess(Member result) {
			UserRights userRights = result.getRights();

			if (userRights.isAllowedToFillAir()){
				tp.add(new GasBlendingComposite(BlendingType.AIR),"Air");
			}
			if (userRights.isAllowedToFillNx40()){
				tp.add(new GasBlendingComposite(BlendingType.NX40_CASCADE),"Nx40 Cascade");
			}
			if (userRights.isAllowedToFillPartial()){
				tp.add(new GasBlendingComposite(BlendingType.PARTIAL_METHOD),"Partial Method");
			}
			if (userRights.isAllowedToFillMixer() ){
				tp.add(new GasBlendingComposite(BlendingType.MIXER),"Mixer");
			} 	
		};
	}

}