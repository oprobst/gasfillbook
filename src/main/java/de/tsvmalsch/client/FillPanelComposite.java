package de.tsvmalsch.client;

import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class FillPanelComposite extends Composite {

	/*
	 * private class FillCylinderClickHandler implements ClickHandler {
	 * 
	 * @Override public void onClick(ClickEvent event) { ctxComp.add(new
	 * Label("FillCylinder")); } };
	 */
	private class UserDataClickHandler implements ChangeHandler {
		public void onChange(com.google.gwt.event.dom.client.ChangeEvent event) {

		};
	};

	ListBox cboChooseAction = new ListBox();

	Button btnUserData = new Button();

	public FillPanelComposite() {

		VerticalPanel vp = new VerticalPanel();
		HorizontalPanel hp = new HorizontalPanel();
		Label lblSelectAction = new Label("I'd like to ");
		hp.add(lblSelectAction);

		cboChooseAction.addChangeHandler(new UserDataClickHandler());
		cboChooseAction.addItem("fill an own cylinder");
		cboChooseAction.addItem("fill a club cylinder");
		cboChooseAction.addItem("fill a cylinder of another member");
		hp.add(cboChooseAction);
		//initWidget(hp);
		vp.add(hp);
		initWidget(vp);
	}

}