package de.tsvmalsch.client.composite;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DoubleBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.tsvmalsch.client.ConfigurationService;
import de.tsvmalsch.client.ConfigurationServiceAsync;
import de.tsvmalsch.client.DefaultAsyncCallback;
import de.tsvmalsch.client.GasBlenderService;
import de.tsvmalsch.client.GasBlenderServiceAsync;
import de.tsvmalsch.client.listener.CurrentAccountingMemberListener;
import de.tsvmalsch.client.listener.CurrentCylinderListener;
import de.tsvmalsch.shared.CalcResult;
import de.tsvmalsch.shared.CylinderContents;
import de.tsvmalsch.shared.model.BlendingType;
import de.tsvmalsch.shared.model.Configuration;
import de.tsvmalsch.shared.model.Cylinder;
import de.tsvmalsch.shared.model.Member;

public class GasBlendingComposite extends Composite implements
		CurrentCylinderListener, CurrentAccountingMemberListener {

	class CalculateBlendingHandler implements ClickHandler, KeyUpHandler {

		@Override
		public void onClick(ClickEvent arg0) {
			calculateBlending();
		}

		@Override
		public void onKeyUp(KeyUpEvent arg0) {
			calculateBlending();
		}
	}

	class ConfigurationServiceCallback extends
			DefaultAsyncCallback<Configuration> {

		@Override
		public void onSuccess(Configuration result) {
			config = result;
			if (blendingType != BlendingType.PARTIAL_METHOD) {
				lblBarTargetO2Pressure.setText("bar Nx"
						+ config.getNxCascadeOxygen());
			}
		}

	}

	class GasBlenderCallback extends DefaultAsyncCallback<CalcResult> {

		private String formatDouble(double d) {
			return Double.toString(d);// Float.toString(Math.round(d * 10) /
										// 10.0f);
		}

		private void generateBlendingHint(CalcResult r) {

			double cPress = txbRemainingPressure.getValue();
			double size = currentCylinder.getTwinSetSizeInLiter();

			StringBuilder sb = new StringBuilder();
			sb.append("<div class='blending-hint'><ul>");
			if (r.StartPressure < txbRemainingPressure.getValue()) {
				sb.append("<li>Aktuellen Druck von ");
				sb.append((int) (cPress));
				sb.append(" bar <b>auf ");
				sb.append(formatDouble(r.StartPressure));
				sb.append(" bar ablassen</b> (");
				sb.append((int) (cPress - r.StartPressure * size));
				sb.append(" barL).</li>");
			} else {
				sb.append("<li>Anfänglicher Flaschendruck von ");
				sb.append((int) (cPress));
				sb.append(" bar (=");
				sb.append((int) (cPress * size));
				sb.append(" barL).</li>");
			}
			cPress = r.StartPressure;

			if (blendingType == BlendingType.PARTIAL_METHOD) {

				if (heFirst) {

					cPress += r.HeAdded;
					sb.append(generateHeString(cPress, r));
					cPress += r.O2Added;
					sb.append(generateO2String(cPress, r));

				} else {

					cPress += r.O2Added;
					sb.append(generateO2String(cPress, r));

					cPress += r.HeAdded;
					sb.append(generateHeString(cPress, r));

				}
			} else {
				cPress += r.O2Added;
				sb.append(generateCascadeString(cPress, r));
			}

			sb.append("<li>Toppe mit ");
			sb.append(formatDouble(r.EndPressure - cPress));
			sb.append(" bar <b>Pressluft bis ");
			sb.append((int) (r.EndPressure));
			sb.append(" bar</b> (+");
			sb.append((int) ((r.EndPressure - cPress) * size));
			sb.append(" barL).</li></ul></div>");

			lblBlendingHint.setHTML(sb.toString());
			calcResult = r;

		}

		private String generateCascadeString(double cPress, CalcResult r) {

			StringBuilder sb = new StringBuilder();
			sb.append("<li>Fülle ");
			sb.append(formatDouble(r.O2Added));
			sb.append(" bar <b>aus Nitrox Kaskade bis ");
			sb.append(formatDouble(cPress));
			sb.append(" bar</b> (+");
			sb.append((int) (r.O2Added * currentCylinder
					.getTwinSetSizeInLiter()));
			sb.append(" barL).</li>");
			return sb.toString();
		}

		private String generateHeString(double cPress, CalcResult r) {
			if (r.HeAdded == 0) {
				return "";
			}
			StringBuilder sb = new StringBuilder();
			sb.append("<li>Fülle ");
			sb.append(formatDouble(r.HeAdded));
			sb.append(" bar <b>Helium bis ");
			sb.append(formatDouble(cPress));
			sb.append(" bar</b> (+");
			sb.append((int) (r.HeAdded * currentCylinder
					.getTwinSetSizeInLiter()));
			sb.append(" barL).</li>");
			return sb.toString();
		}

		private String generateO2String(double cPress, CalcResult r) {
			if (r.O2Added == 0) {
				return "";
			}

			StringBuilder sb = new StringBuilder();
			sb.append("<li>Fülle ");
			sb.append(formatDouble(r.O2Added));
			sb.append(" bar <b>Sauerstoff bis ");
			sb.append(formatDouble(cPress));
			sb.append(" bar</b> (+");
			sb.append((int) (r.O2Added * currentCylinder
					.getTwinSetSizeInLiter()));
			sb.append(" barL).</li>");
			return sb.toString();
		}

		public void onSuccess(CalcResult r) {

			calcResult = r;
			if (r.successfull) {
				generateBlendingHint(r);

				txbBarReallyFilledHe.setValue(r.HeAdded);
				txbBarReallyFilledO2.setValue(r.O2Added);

				// TODO
				if (blendingType == BlendingType.PARTIAL_METHOD) {
					lblFillingCost.setText("Füllkosten: "
							+ Math.round((int) ((r.HeAdded
									* currentCylinder.getTwinSetSizeInLiter()
									* 0.0175 + r.O2Added
									* currentCylinder.getTwinSetSizeInLiter()
									* 0.0055) * 100)) / 100f + " Euro");
				} else {
					lblFillingCost.setText("Füllkosten: "
							+ Math.round((int) ((r.HeAdded
									* currentCylinder.getTwinSetSizeInLiter()
									* 0.0175 + r.O2Added
									* currentCylinder.getTwinSetSizeInLiter()
									// TODO Real oxygen content of cascade
									// instead of .40
									* 0.0055d * (.241d)) * 100)) / 100f
							+ " Euro");
				}

			} else {
				lblBlendingHint.setHTML("" + "<p>" + r.failureSting + "</p>");

			}
		}
	}

	private HTML lblBlendingHint = new HTML("<p>Gas Blending </p>");

	private Label lblRemainingPressure = new Label("Restdruck: ");
	private Label lblBarRemainingPressure = new Label("bar");

	private Label lblPercentRemainingO2 = new Label("% O₂");
	private Label lblPercentRemainingHe = new Label("% He");
	private Label lblTargetPressure = new Label("Enddruck: ");
	private Label lblBarTargetPressure = new Label("bar");

	private Label lblPercentTargetO2 = new Label("% O₂");
	private Label lblPercentTargetHe = new Label("% He");
	private Label lblFinalBlending = new Label("Tatsächlich gefüllt: ");

	private Label lblBarTargetO2Pressure = new Label("bar O₂");

	private Label lblBarTargetHePressure = new Label("bar He");
	private Label lblTemperature = new Label("Temperature (°C): ");
	private DoubleBox txbRemainingPressure = new DoubleBox();
	private DoubleBox txbRemainingO2 = new DoubleBox();
	private DoubleBox txbRemainingHe = new DoubleBox();
	private DoubleBox txbTargetPressure = new DoubleBox();
	private DoubleBox txbTargetO2Percent = new DoubleBox();
	private DoubleBox txbTargetHePercent = new DoubleBox();
	private DoubleBox txbBarReallyFilledO2 = new DoubleBox();

	private DoubleBox txbBarReallyFilledHe = new DoubleBox();
	private IntegerBox txbTemperature = new IntegerBox();

	private Label lblMixingOrder = new Label("Zuerst ");
	private RadioButton rbtFirstHe = new RadioButton("mixingOrder", "He");
	private RadioButton rbtFirstO2 = new RadioButton("mixingOrder", "O₂");

	private Label lblFillingCost = new Label("Füllkosten: 0,00 Euro");

	private Button btnAccount = new Button();

	private final int blendingType;

	private final GasBlenderServiceAsync gasBlenderService = GWT
			.create(GasBlenderService.class);

	private final ConfigurationServiceAsync configService = GWT
			.create(ConfigurationService.class);

	private Cylinder currentCylinder = null;

	private boolean heFirst = true;

	public GasBlendingComposite(int blendingType) {
		configService
				.getCurrentConfiguration(new ConfigurationServiceCallback());

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

		t.setWidget(2, 0, lblTemperature);
		t.setWidget(2, 1, txbTemperature);
		t.setWidget(2, 3, lblMixingOrder);
		t.setWidget(2, 4, rbtFirstHe);
		t.setWidget(2, 5, rbtFirstO2);

		t.getFlexCellFormatter().setColSpan(3, 0, 7);
		t.setWidget(3, 0, lblBlendingHint);

		t.setWidget(4, 0, lblFinalBlending);

		t.setWidget(4, 3, txbBarReallyFilledO2);
		t.setWidget(4, 4, lblBarTargetO2Pressure);
		t.setWidget(4, 5, txbBarReallyFilledHe);
		t.setWidget(4, 6, lblBarTargetHePressure);

		HorizontalPanel hp = new HorizontalPanel();
		hp.add(lblFillingCost);
		btnAccount.setText("Füllung abrechnen!");
		hp.add(btnAccount);

		vp.add(hp);
		formatWidgets();
		initWidget(vp);

	}

	private CylinderContents targetMix = null;
	private CalcResult calcResult = null;

	private void calculateBlending() {

		heFirst = rbtFirstHe.getValue();
		CylinderContents start = new CylinderContents();
		CylinderContents target = new CylinderContents();
		start.setPressure(txbRemainingPressure.getValue());
		start.setfO2(txbRemainingO2.getValue());
		start.setfHe(txbRemainingHe.getValue());
		target.setPressure(txbTargetPressure.getValue());
		target.setfO2(txbTargetO2Percent.getValue());
		target.setfHe(txbTargetHePercent.getValue());
		Integer temperatur = txbTemperature.getValue();

		if (target.getPressure() == null || target.getfO2() == null
				|| target.getfHe() == null || start.getPressure() == null
				|| start.getfO2() == null || start.getfHe() == null
				|| temperatur == null) {

			txbBarReallyFilledO2.setValue(0.0);
			txbBarReallyFilledHe.setValue(0.0);
			lblBlendingHint.setHTML("<b>Bitte Felder vollständig füllen.</b>");
			return;
		}

		start.setfO2(start.getfO2() / 100);
		start.setfHe(start.getfHe() / 100);
		target.setfHe(target.getfHe() / 100);
		target.setfO2(target.getfO2() / 100);
		this.targetMix = target;
		double size = currentCylinder.getTwinSetSizeInLiter();

		if (blendingType == BlendingType.NX40_CASCADE) {
			gasBlenderService.calcIdeal(start, target, 0.4,
					new GasBlenderCallback());
		} else {
			gasBlenderService.calcReal(start, target, size,
					temperatur.intValue(), true, new GasBlenderCallback());
		}
	}

	@Override
	public void cylinderSelected(Cylinder currentOne) {
		currentCylinder = currentOne;
		calculateBlending();
	}

	private void formatWidgets() {

		btnAccount.setStyleName("button-book-filling");

		rbtFirstHe.setValue(true);

		txbRemainingPressure.setValue(50.0);
		txbRemainingO2.setValue(20.9d);
		txbRemainingHe.setValue(0d);
		txbTargetPressure.setValue(230.0d);
		txbTargetO2Percent.setValue(32.0d);
		txbTargetHePercent.setValue(0.0d);
		txbBarReallyFilledO2.setValue(0.0d);
		txbBarReallyFilledHe.setValue(0.0d);
		txbTemperature.setValue(20);

		txbRemainingPressure.setStyleName("txt-3digit");
		txbRemainingO2.setStyleName("txt-3digit");
		txbRemainingHe.setStyleName("txt-3digit");
		txbTargetPressure.setStyleName("txt-3digit");
		txbTargetO2Percent.setStyleName("txt-3digit");
		txbTargetHePercent.setStyleName("txt-3digit");
		txbBarReallyFilledO2.setStyleName("txt-3digit");
		txbBarReallyFilledHe.setStyleName("txt-3digit");
		txbTemperature.setStyleName("txt-3digit");

		txbRemainingPressure.setMaxLength(4);
		txbRemainingO2.setMaxLength(5);
		txbRemainingHe.setMaxLength(5);
		txbTargetPressure.setMaxLength(5);
		txbTargetO2Percent.setMaxLength(5);
		txbTargetHePercent.setMaxLength(5);
		txbBarReallyFilledO2.setMaxLength(5);
		txbBarReallyFilledHe.setMaxLength(5);
		txbTemperature.setMaxLength(3);

		CalculateBlendingHandler blurHandler = new CalculateBlendingHandler();
		txbRemainingPressure.addKeyUpHandler(blurHandler);
		txbRemainingO2.addKeyUpHandler(blurHandler);
		txbRemainingHe.addKeyUpHandler(blurHandler);
		txbTargetPressure.addKeyUpHandler(blurHandler);
		txbTargetO2Percent.addKeyUpHandler(blurHandler);
		txbTargetHePercent.addKeyUpHandler(blurHandler);
		txbTemperature.addKeyUpHandler(blurHandler);

		rbtFirstHe.addClickHandler(blurHandler);
		rbtFirstO2.addClickHandler(blurHandler);

	
		if (blendingType == BlendingType.NX40_CASCADE) {
			btnAccount.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent arg0) {

					new ConfirmBlendingDialog(fillingMember).showCascadeConfirmation(
							currentCylinder, targetMix, calcResult,
							txbBarReallyFilledO2.getValue(),
							config.getNxCascadeOxygen(),
							config.getPricePerBarLO2(), accounted);
				}
			});
		} else if (blendingType == BlendingType.PARTIAL_METHOD) {
			btnAccount.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent arg0) {
					new ConfirmBlendingDialog(fillingMember)
							.showPartialPressureConfirmation(currentCylinder,
									targetMix, calcResult,
									txbBarReallyFilledHe.getValue(),
									txbBarReallyFilledO2.getValue(),
									config.getPricePerBarLHe(),
									config.getPricePerBarLO2(), accounted);
				}
			});
		} else {
			btnAccount.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent arg0) {
					new ConfirmBlendingDialog(fillingMember).showAirConfirmation(
							currentCylinder, txbRemainingPressure.getValue(),
							txbTargetPressure.getValue());
				}
			});
		}

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
			lblTemperature.setVisible(false);
			txbTemperature.setVisible(false);
			lblMixingOrder.setVisible(false);
			rbtFirstHe.setVisible(false);
			rbtFirstO2.setVisible(false);

		}
		if (blendingType != BlendingType.PARTIAL_METHOD) {
			txbBarReallyFilledHe.setVisible(false);
			lblBarTargetHePressure.setVisible(false);
			lblPercentTargetHe.setVisible(false);
			lblPercentRemainingHe.setVisible(false);
			txbTargetHePercent.setVisible(false);
			txbRemainingHe.setVisible(false);
			lblTemperature.setVisible(false);
			txbTemperature.setVisible(false);
			lblMixingOrder.setVisible(false);
			rbtFirstHe.setVisible(false);
			rbtFirstO2.setVisible(false);

			lblBarTargetO2Pressure.setText("bar Nx40");

		}
	}

	private Configuration config = new Configuration();

	private Member accounted = null;
	private Member fillingMember = null;

	public void setFillingMember(Member fillingMember) {
		this.fillingMember = fillingMember;
	}

	@Override
	public void accountedMember(Member theOneWhoPays) {
		accounted = theOneWhoPays;
	}
}