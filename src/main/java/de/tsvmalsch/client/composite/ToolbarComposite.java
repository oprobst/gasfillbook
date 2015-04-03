package de.tsvmalsch.client.composite;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ToolbarComposite extends Composite {

	private class FillCylinderClickHandler implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			ctxComp.remove(1);
			ctxComp.add(new FillPanelComposite());
		}
	};

	private class UserDataClickHandler implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			ctxComp.remove(1);
			ctxComp.add(new Label("UserData"));
		}
	};

	Button btnFillPanel = new Button();

	Button btnUserData = new Button();

	private final VerticalPanel ctxComp;

	public ToolbarComposite(VerticalPanel contextWidget) {
		
		this.ctxComp = contextWidget;
		
		btnUserData.setStyleName("btnToolbar");

		btnFillPanel.setStyleName("btnToolbar");

		HorizontalPanel panel = new HorizontalPanel();
		initWidget(panel);

		panel.add(btnFillPanel);
		btnFillPanel.setText("Fill Cylinder");
		panel.add(btnUserData);
		btnUserData.setText("User Data");

		btnFillPanel.addClickHandler(new FillCylinderClickHandler());
		btnUserData.addClickHandler(new UserDataClickHandler());

	}

 
}