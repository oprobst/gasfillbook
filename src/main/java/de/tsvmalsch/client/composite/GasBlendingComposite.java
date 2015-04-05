package de.tsvmalsch.client.composite;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DoubleBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.tsvmalsch.client.DefaultAsyncCallback;
import de.tsvmalsch.client.GasBlenderService;
import de.tsvmalsch.client.GasBlenderServiceAsync;
import de.tsvmalsch.shared.CalcResult;
import de.tsvmalsch.shared.CylinderContents;
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

	private DoubleBox txbRemainingPressure = new DoubleBox();
	private DoubleBox txbRemainingO2 = new DoubleBox();
	private DoubleBox txbRemainingHe = new DoubleBox();
	private DoubleBox txbTargetPressure = new DoubleBox();
	private DoubleBox txbTargetO2Percent = new DoubleBox();
	private DoubleBox txbTargetHePercent = new DoubleBox();
	private DoubleBox txbBarReallyFilledO2 = new DoubleBox();
	private DoubleBox txbBarReallyFilledHe = new DoubleBox();

	private Label lblFillingCost = new Label("Füllkosten: 12,34 Euro");
	private Button btnAccount = new Button();

	private final int blendingType;

	private void formatWidgets() {

		lblBlendingHint.setStyleName("blending-hint");
		btnAccount.setStyleName("button-book-filling");

		txbRemainingPressure.setValue(50.0);
		txbRemainingO2.setValue(20.9d);
		txbRemainingHe.setValue(0d);
		txbTargetPressure.setValue(230.0d);
		txbTargetO2Percent.setValue(32.0d);
		txbTargetHePercent.setValue(0.0d);
		txbBarReallyFilledO2.setValue(0.0d);
		txbBarReallyFilledHe.setValue(0.0d);

		txbRemainingPressure.setStyleName("txt-3digit");
		txbRemainingO2.setStyleName("txt-3digit");
		txbRemainingHe.setStyleName("txt-3digit");
		txbTargetPressure.setStyleName("txt-3digit");
		txbTargetO2Percent.setStyleName("txt-3digit");
		txbTargetHePercent.setStyleName("txt-3digit");
		txbBarReallyFilledO2.setStyleName("txt-3digit");
		txbBarReallyFilledHe.setStyleName("txt-3digit");

		txbRemainingPressure.setMaxLength(4);
		txbRemainingO2.setMaxLength(5);
		txbRemainingHe.setMaxLength(5);
		txbTargetPressure.setMaxLength(5);
		txbTargetO2Percent.setMaxLength(5);
		txbTargetHePercent.setMaxLength(5);
		txbBarReallyFilledO2.setMaxLength(5);
		txbBarReallyFilledHe.setMaxLength(5);

		CalculateBlendingHandler blurHandler = new CalculateBlendingHandler();
		txbRemainingPressure.addChangeHandler(blurHandler);
		txbRemainingO2.addChangeHandler(blurHandler);
		txbRemainingHe.addChangeHandler(blurHandler);
		txbTargetPressure.addChangeHandler(blurHandler);
		txbTargetO2Percent.addChangeHandler(blurHandler);
		txbTargetHePercent.addChangeHandler(blurHandler);

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

	class CalculateBlendingHandler implements ChangeHandler {

		@Override
		public void onChange(ChangeEvent event) {

			CylinderContents start = new CylinderContents();
			CylinderContents target = new CylinderContents();
			start.Pressure = txbRemainingPressure.getValue();
			start.FO2 = txbRemainingO2.getValue();
			start.FHe = txbRemainingHe.getValue();
			target.Pressure = txbTargetPressure.getValue();
			target.FO2 = txbTargetO2Percent.getValue();
			target.FHe = txbTargetHePercent.getValue();

			if (target.Pressure == null || target.FO2 == null
					|| target.FHe == null || start.Pressure == null
					|| start.FO2 == null || start.FHe == null) {

				txbBarReallyFilledO2.setValue(0.0);
				txbBarReallyFilledHe.setValue(0.0);
				lblBlendingHint
						.setHTML("<b>Bitte Felder vollständig füllen.</b>");
				return;
			}

			start.FO2 = start.FO2 / 100;
			start.FHe = start.FHe / 100;
			target.FHe = target.FHe / 100;
			target.FO2 = target.FO2 / 100;

			start.FO2 = txbRemainingO2.getValue();
			start.FHe = txbRemainingHe.getValue();
			target.Pressure = txbTargetPressure.getValue();
			target.FO2 = txbTargetO2Percent.getValue();
			target.FHe = txbTargetHePercent.getValue();

			// TODO Request remaining Values
			gasBlenderService.calc(start, target, 12.0, 21, true,
					new GasBlenderCallback());

		}
	}

	class GasBlenderCallback extends DefaultAsyncCallback<CalcResult> {

		public void onSuccess(CalcResult r) {

			if (r.successfull) {
				lblBlendingHint.setHTML("" + "<p>Start pressure "
						+ r.StartPressure + " bar<br/>" + "Top He " + r.HeAdded
						+ " bar<br/>" + "Top O2 " + r.O2Added + " bar<br/>"
						+ "Top with AIR to " + r.EndPressure + " bar. </p>");

				txbBarReallyFilledHe.setValue(r.HeAdded);
				txbBarReallyFilledO2.setValue(r.O2Added);

				lblFillingCost.setText("Füllkosten: "
						+ (r.HeAdded * 12 * 0.0175 + r.O2Added * 12 * 0.0055)
						+ " Euro");

			} else {
				lblBlendingHint.setHTML("" + "<p>" + r.failureSting + "</p>");

			}
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

	private final GasBlenderServiceAsync gasBlenderService = GWT
			.create(GasBlenderService.class);
}