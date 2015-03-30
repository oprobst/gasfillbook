package de.tsvmalsch.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class MainPanel extends Composite {

	public MainPanel() {

		VerticalPanel verticalPanel = new VerticalPanel();
		initWidget(verticalPanel);

		Label lblWelcome = new Label("Los geht's!");
		lblWelcome.setStyleName("gwt-Label-Login");
		verticalPanel.add(lblWelcome);

	}

}
