package de.tsvmalsch.client.composite;

import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class FillPanelComposite extends Composite {

	private int currentView = 0;
	private VerticalPanel vp = null;

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

				return;
			case 1:// club cylinder
				cylinderSelectComposite.setVisible(true);
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

		initWidget(vp);
	}

	CylinderSelectComposite cylinderSelectComposite = new CylinderSelectComposite();
	SelectOtherMemberComposite selectOtherMemberComposite = new SelectOtherMemberComposite();
}