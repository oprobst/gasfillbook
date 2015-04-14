package de.tsvmalsch.client.composite;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.tsvmalsch.shared.CalcResult;
import de.tsvmalsch.shared.CylinderContents;
import de.tsvmalsch.shared.model.Cylinder;
import de.tsvmalsch.shared.model.Member;

public class ConfirmBlendingDialog extends DialogBox {

	HTML userMessage = new HTML();

	public ConfirmBlendingDialog() {

		setTitle("Mischung buchen");

		// Enable animation.
		setAnimationEnabled(true);

		// Enable glass background.
		setGlassEnabled(true);

		HorizontalPanel hp = new HorizontalPanel();
		VerticalPanel vp = new VerticalPanel();
		vp.add(userMessage);
		vp.add(hp);

		Button ok = new Button("OK");
		ok.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				ConfirmBlendingDialog.this.hide();
				bookBlending();
			}
		});
		hp.add(ok);
		Button cancel = new Button("Abbruch");
		cancel.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {

				ConfirmBlendingDialog.this.hide();
			}
		});
		hp.add(cancel);

		setWidget(vp);
		setModal(true);
		setStyleName("blending-summary-hint-dialog" );
	}

	protected void bookBlending() {
		// TODO Auto-generated method stub

	}

	public void showCascadeConfirmation(Cylinder currentCylinder,
			CylinderContents targetMix, CalcResult calcResult,
			Double o2Content, double o2PartialPressureCascade, double o2Price,
			Member payedBy) {

		generateText(currentCylinder, targetMix, calcResult, 0d, o2Content,
				true, o2PartialPressureCascade, 0, o2Price, payedBy);
		show();
	}

	public void showPartialPressureConfirmation(Cylinder currentCylinder,
			CylinderContents targetMix, CalcResult calcResult,
			double heContent, double o2Content, double hePrice, double o2Price,
			Member payedBy) {
		generateText(currentCylinder, targetMix, calcResult, heContent, o2Content,
				false, 0d, hePrice, o2Price, payedBy);
		show();
	}

	// TODO Implement real values.//TODO externalize
	private double getNxCascadePrice(double o2Price, double o2ContentCascade) {
		return Math.round(.24 * o2Price * 100000) / 100000.0d;

	}

	private double calculateFinalNxCascadePrice(double barLFilled,
			double o2Price, double o2ContentCascade) {

		return Math.round(barLFilled
				* getNxCascadePrice(o2Price, o2ContentCascade) * 100) / 100.0d;
	}

	private void generateText(Cylinder currentCylinder,
			CylinderContents targetMix, CalcResult calcResult,
			double heContent, double o2Content, boolean o2IsNxFromCascade,
			double o2PartialPressureCascade, double hePrice, double o2Price,
			Member payedBy) {

		double heFinalPrice = Math.round(heContent
				* currentCylinder.getTwinSetSizeInLiter() * hePrice * 100) / 100.0d;

		double o2FinalPrice;
		if (o2IsNxFromCascade) {
			o2FinalPrice = calculateFinalNxCascadePrice(o2Content
					* currentCylinder.getTwinSetSizeInLiter(), o2Price,
					o2PartialPressureCascade);
		} else {
			o2FinalPrice = Math.round(o2Content
					* currentCylinder.getTwinSetSizeInLiter() * o2Price * 100) / 100.0d;
		}
		double completePrice = heFinalPrice + o2FinalPrice;

		StringBuilder sb = new StringBuilder();
		sb.append("<div  class='blending-summary-hint'><p>Du hast in Flasche <i>");
		sb.append(currentCylinder.getUiIdentifier());
		sb.append("</i> ein ");
		if (targetMix.getfHe() > 0) {
			sb.append("TMX ");
			sb.append(targetMix.getfO2() * 100);
			sb.append("|");
			sb.append(targetMix.getfHe() * 100);
		} else {
			sb.append("Nx");
			sb.append(targetMix.getfO2() * 100);
		}
		sb.append(" gefüllt.<br>");

		sb.append("Dabei hast Du <ul><li>");
		if (heContent > 0) {
			sb.append(heContent);
			sb.append(" bar Helium für ");
			sb.append(heFinalPrice);
			sb.append(" € (=");
			sb.append(heContent * currentCylinder.getTwinSetSizeInLiter());
			sb.append(" barL zu ");
			sb.append(hePrice);
			sb.append(" €)");
			sb.append(" und </li><li>");
		}
		if (o2IsNxFromCascade) {
			sb.append(o2Content);
			sb.append(" bar Nx");
			sb.append(o2PartialPressureCascade);
			sb.append(" für ");
			sb.append(o2FinalPrice);
			sb.append(" € gefüllt (= ");
			sb.append(o2Content * currentCylinder.getTwinSetSizeInLiter());
			sb.append(" barL zu ");
			sb.append(getNxCascadePrice(o2Price, o2PartialPressureCascade));
			sb.append(" €). ");

		} else {
			sb.append(o2Content);
			sb.append(" bar Sauerstoff für ");
			sb.append(o2FinalPrice);
			sb.append(" € (=");
			sb.append(o2Content * currentCylinder.getTwinSetSizeInLiter());
			sb.append(" barL zu ");
			sb.append(o2Price);
			sb.append(" €) gefüllt. </li>");

		}
		sb.append("</ul>Mit Betätigen des Ok Buttons wird der Betrag von <b> ");
		sb.append(completePrice);
		sb.append(" € </b>");
		if (payedBy != null) {
			sb.append("dem Nutzer ");
			sb.append(payedBy.getFirstName());
			sb.append(" ");
			sb.append(payedBy.getLastName());
		}
		sb.append(" in Rechnung gestellt.</p></div>");

		sb.append("<div class='security-hint'><b>Hinweis:</b> <ul><li>Das Tauchen mit Mischgasen bedarf einer besonderen Ausbildung.</li>");
		sb.append("<li>Die Richtigkeit des resultierenden Gemischs <i>muss</i> nach dem Mischen analysiert werden.</li>");
		sb.append("<li>Die Flasche ");
		sb.append(currentCylinder.getUiIdentifier());
		sb.append(" <i>muss</i> mit einem entsprechenden Füll-Label versehen werden.</li>");
		sb.append("<li>Die Richtigkeit des Gemischs <i>muss</i> vor <br/> dem Tauchen durch den Taucher selbst <i>nochmals</i> kontrolliert werden.</li></ul></div>");
		userMessage.setHTML(sb.toString());
	}

	public void showAirConfirmation(Cylinder currentCylinder, double from,
			double to) {
		StringBuilder sb = new StringBuilder();
		sb.append("<p>Du hast in Flasche  <i>");
		sb.append(currentCylinder.getUiIdentifier());
		sb.append("</i> ");
		sb.append(to - from);
		sb.append(" bar (=");
		sb.append((to - from) * currentCylinder.getTwinSetSizeInLiter());
		sb.append(" barL) Pressluft gefüllt.<br/>");

		sb.append("Bei Betätigen des OK Buttons wird die Füllung registriert. <br/><b>Gut Luft!</b></p>");
		userMessage.setHTML(sb.toString());
		show();

	}
}