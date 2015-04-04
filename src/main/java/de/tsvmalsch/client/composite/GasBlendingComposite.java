package de.tsvmalsch.client.composite;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.tsvmalsch.client.CylinderService;
import de.tsvmalsch.client.CylinderServiceAsync;
import de.tsvmalsch.client.UserService;
import de.tsvmalsch.client.UserServiceAsync;

public class GasBlendingComposite extends Composite {

	/*private final UserServiceAsync userService = GWT.create(UserService.class);
	private final CylinderServiceAsync cylinderService = GWT
			.create(CylinderService.class);
*/
	public GasBlendingComposite(int blendingType) {

		VerticalPanel vp = new VerticalPanel();
		//HorizontalPanel hp = new HorizontalPanel();
		Label lbl = new Label();
		lbl.setText("type " + blendingType);
		vp.add(lbl);
		//vp.add(hp);

		initWidget(vp);
	}

}