package de.tsvmalsch.client;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DialogBox;

public abstract class DefaultAsyncCallback<T> implements AsyncCallback<T> {
	
	Logger logger = Logger.getLogger(DefaultAsyncCallback.class
			.getCanonicalName());

	@Override
	public void onFailure(Throwable caught) {
		// Show the RPC error message to the user
		DialogBox dialogBox = new DialogBox();
		dialogBox.setTitle(failureMessage());
		dialogBox.setText(caught.getMessage());
		logger.log(Level.SEVERE, failureMessage(), caught);

		dialogBox.center();

	}

	public String failureMessage() {
		return "Remote Procedure Call failed.";
	}
}
