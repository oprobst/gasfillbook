package de.tsvmalsch.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.tsvmalsch.client.composite.ToolbarComposite;

public class MainPanel extends Composite {

	ToolbarComposite toolbar = null;

	//FillPanelComposite contextWidget = new FillPanelComposite();

	Label contextWidget = new Label ("Hello");
	public MainPanel() {

		RootPanel rootPanel = RootPanel.get();

		VerticalPanel verticalPanel = new VerticalPanel();
	
		rootPanel.add(verticalPanel);

		toolbar = new ToolbarComposite(verticalPanel);

		verticalPanel.add(toolbar);

		verticalPanel.add(contextWidget);
		initWidget(verticalPanel);
	}
}
