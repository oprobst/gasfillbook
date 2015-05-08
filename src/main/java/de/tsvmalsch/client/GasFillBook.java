package de.tsvmalsch.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.tsvmalsch.client.composite.ToolbarComposite;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class GasFillBook implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	private final Messages messages = GWT.create(Messages.class);

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		RootPanel rootPanel = RootPanel.get();

		HorizontalPanel horizontalPanel = new HorizontalPanel();
		rootPanel.add(horizontalPanel, 10, 10);
		horizontalPanel.setSize("1024px", "100px");

		VerticalPanel verticalPanel = new VerticalPanel();
		horizontalPanel.add(verticalPanel);

		Label lblWelcomeToThe = new Label("Füllstation TSV Malsch");
		lblWelcomeToThe.setStyleName("gwt-welcome-label");
		verticalPanel.add(lblWelcomeToThe);
		lblWelcomeToThe.setSize("1024px", "200px");

		Login login = new Login(this);
		verticalPanel.add(login);

	}

	public void userAuthenticated() {
		RootPanel rootPanel = RootPanel.get();
		rootPanel.clear();
		rootPanel.add(new ToolbarComposite());
	}

}
