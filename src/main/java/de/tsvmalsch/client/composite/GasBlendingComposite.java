package de.tsvmalsch.client.composite;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.tsvmalsch.shared.model.BlendingType;

public class GasBlendingComposite extends Composite {

	private HTML lblBlendingHint = new HTML(
			"<p> Dies ist ein Beispieltext<br/> "
					+ "<ul> <li> 39bar ablassen</li>"
					+ "<li> 40bar Nx40 aus der Kaskade füllen (auf 120bar)</li>"
					+ "<li> Mit Luft füllen</li></ul></p>");
	/*
	 * private final UserServiceAsync userService =
	 * GWT.create(UserService.class); private final CylinderServiceAsync
	 * cylinderService = GWT .create(CylinderService.class);
	 */

	private Label lblRemainingPressure = new Label("Restdruck");
	private Label lblBarRemainingPressure = new Label("bar");

	private Label lblPercentRemainingO2 = new Label("% O2");
	private Label lblPercentRemainingHe = new Label("% He");

	private Label lblTargetPressure = new Label("Enddruck");
	private Label lblBarTargetPressure = new Label("bar");
	private Label lblPercentTargetO2 = new Label("% O2");
	private Label lblPercentTargetHe = new Label("% He");

	private Label lblFinalBlending = new Label("Tatsächlich gefüllt: ");
	private Label lblBarTargetO2Pressure = new Label("bar O2");
	private Label lblBarTargetHePressure = new Label("bar He");

	private TextBox txbRemainingPressure = new TextBox();
	private TextBox txbRemainingO2 = new TextBox();
	private TextBox txbRemainingHe = new TextBox();
	private TextBox txbTargetPressure = new TextBox();
	private TextBox txbTargetO2Percent = new TextBox();
	private TextBox txbTargetHePercent = new TextBox();
	private TextBox txbBarReallyFilledO2 = new TextBox();
	private TextBox txbBarReallyFilledHe = new TextBox();

	private Label lblFillingCost = new Label("Füllkosten: 12,34 Euro");
	private Button btnAccount = new Button();

	private final int blendingType;

	private void formatWidgets() {

		lblBlendingHint.setStyleName("blending-hint");
		btnAccount.setStyleName("button-book-filling");

		txbRemainingO2.setStyleName("txt-3digit");
		txbRemainingPressure.setStyleName("txt-3digit");
		txbRemainingO2.setStyleName("txt-3digit");
		txbRemainingHe.setStyleName("txt-3digit");
		txbTargetPressure.setStyleName("txt-3digit");
		txbTargetO2Percent.setStyleName("txt-3digit");
		txbTargetHePercent.setStyleName("txt-3digit");
		txbBarReallyFilledO2.setStyleName("txt-3digit");
		txbBarReallyFilledHe.setStyleName("txt-3digit");

		txbRemainingPressure.setMaxLength(3);
		txbRemainingO2.setMaxLength(3);
		txbRemainingHe.setMaxLength(3);
		txbTargetPressure.setMaxLength(3);
		txbTargetO2Percent.setMaxLength(3);
		txbTargetHePercent.setMaxLength(3);
		txbBarReallyFilledO2.setMaxLength(3);
		txbBarReallyFilledHe.setMaxLength(3);

		if (blendingType != BlendingType.NX40_CASCADE
				&& blendingType != BlendingType.PARTIAL_METHOD) {
			txbRemainingO2.setVisible(false);
			txbBarReallyFilledO2.setVisible(false);
			txbTargetO2Percent.setVisible(false);
			txbBarReallyFilledO2.setVisible(false);
			lblPercentRemainingO2.setVisible(false);
			lblBarTargetO2Pressure.setVisible(false);
			lblPercentTargetO2.setVisible(false);
			lblBlendingHint.setVisible(false);
			lblFinalBlending.setVisible(false);
			lblFillingCost.setVisible(false);

		}
		if (blendingType != BlendingType.PARTIAL_METHOD) {
			txbBarReallyFilledHe.setVisible(false);
			lblBarTargetHePressure.setVisible(false);
			lblPercentTargetHe.setVisible(false);
			lblPercentRemainingHe.setVisible(false);
			txbTargetHePercent.setVisible(false);
			txbRemainingHe.setVisible(false);
		}
	}

	public GasBlendingComposite(int blendingType) {

		this.blendingType = blendingType;
		VerticalPanel vp = new VerticalPanel();

		FlexTable t = new FlexTable();

		vp.add(t);

		t.setWidget(0, 0, lblRemainingPressure);
		t.setWidget(0, 1, txbRemainingPressure);
		t.setWidget(0, 2, lblBarRemainingPressure);
		t.setWidget(0, 3, txbRemainingO2);
		t.setWidget(0, 4, lblPercentRemainingO2);
		t.setWidget(0, 5, txbRemainingHe);
		t.setWidget(0, 6, lblPercentRemainingHe);

		t.setWidget(1, 0, lblTargetPressure);
		t.setWidget(1, 1, txbTargetPressure);
		t.setWidget(1, 2, lblBarTargetPressure);
		t.setWidget(1, 3, txbTargetO2Percent);
		t.setWidget(1, 4, lblPercentTargetO2);
		t.setWidget(1, 5, txbTargetHePercent);
		t.setWidget(1, 6, lblPercentTargetHe);

		t.getFlexCellFormatter().setColSpan(2, 0, 6);
		t.setWidget(2, 0, lblBlendingHint);

		t.setWidget(3, 0, lblFinalBlending);
		t.setWidget(3, 3, txbBarReallyFilledO2);
		t.setWidget(3, 4, lblBarTargetO2Pressure);
		t.setWidget(3, 5, txbBarReallyFilledHe);
		t.setWidget(3, 6, lblBarTargetHePressure);

		HorizontalPanel hp = new HorizontalPanel();
		hp.add(lblFillingCost);
		btnAccount.setText("Füllung abrechnen!");
		hp.add(btnAccount);
		vp.add(hp);
		formatWidgets();
		initWidget(vp);
	}
}