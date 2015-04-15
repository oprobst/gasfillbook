package de.tsvmalsch.shared.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;

/**
 * This is one invoice item recording one blending execution.
 * 
 * It contains all data required for accounting a user.
 * 
 * @author Oliver Probst
 *
 */
@Entity
public class FillingInvoiceItem implements Serializable {

	/**
	 * suid
	 */
	private static final long serialVersionUID = -8540656564045693342L;

	/**
	 * The blender who made the mix.
	 */
	@ManyToOne
	@NotNull
	private Member blendingMember;

	/**
	 * Which kind of blending was executed.
	 */
	@NotNull
	private int blendingType;

	/**
	 * The date when the cylinder was filled.
	 */
	@NotNull
	private Date dateOfFilling;

	/**
	 * The cylinder filled.
	 */
	@OneToOne
	private Cylinder filledCylinder;

	/**
	 * DB generated UUID
	 */
	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private Long id;

	/**
	 * Date the invoice was sent to member. If null, currently no invoice sent.
	 * This Date is used to ensure that the invoice is send only once.
	 */
	private Date invoicingDate;

	/**
	 * The amount of liter filled.
	 */
	private int literAirFilled = 0;

	/**
	 * The amount of liter filled.
	 */
	private int literHeliumFilled = 0;

	/**
	 * The amount of liter Oxygen filled.
	 */
	private int literOxygenFilled = 0;

	/**
	 * The date when the payment was received.
	 */
	private Date paymentReceiptDate;

	/**
	 * The price per liter He when executing the gas blending
	 */
	private double pricePerLiterHelium;

	/**
	 * The price per liter O2 when executing the gas blending
	 */
	private double pricePerLiterOxygen;

	/**
	 * Pressure in cylinder when starting gas blending
	 */
	private int startPressure;

	/**
	 * If a user do a mistake when booking a gas blending execution, he can
	 * cancel the invoice item. Cancelled items are not billed.
	 * 
	 */
	private boolean valid = true;

	public double calculatePrice() {
		double price = getPricePerLiterHelium() * getLiterHeliumFilled()
				+ getPricePerLiterOxygen() * getLiterOxygenFilled();

		double rounded = (double) (int) ((price + 0.005) * 100) / 100;
		return rounded;
	}

	public Member getBlendingMember() {
		return blendingMember;
	}

	public int getBlendingType() {
		return blendingType;
	}

	public Date getDateOfFilling() {
		return dateOfFilling;
	}

	public Cylinder getFilledCylinder() {
		return filledCylinder;
	}

	public Long getId() {
		return id;
	}

	public Date getInvoicingDate() {
		return invoicingDate;
	}

	public int getLiterAirFilled() {
		return literAirFilled;
	}

	public int getLiterHeliumFilled() {
		return literHeliumFilled;
	}

	public int getLiterOxygenFilled() {
		return literOxygenFilled;
	}

	public Date getPaymentReceiptDate() {
		return paymentReceiptDate;
	}

	public double getPricePerLiterHelium() {
		return pricePerLiterHelium;
	}

	public double getPricePerLiterOxygen() {
		return pricePerLiterOxygen;
	}

	public int getStartPressure() {
		return startPressure;
	}

	public boolean isValid() {
		return valid;
	}

	public void setBlendingMember(Member member) {
		this.blendingMember = member;
	}

	public void setBlendingType(int air) {
		this.blendingType = air;
	}

	public void setDateOfFilling(Date dateOfFilling) {
		this.dateOfFilling = dateOfFilling;
	}

	public void setFilledCylinder(Cylinder filledCylinder) {
		this.filledCylinder = filledCylinder;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setInvoicingDate(Date invoicingDate) {
		this.invoicingDate = invoicingDate;
	}

	public void setLiterAirFilled(int literAirFilled) {
		this.literAirFilled = literAirFilled;
	}

	public void setLiterHeliumFilled(int literHeliumFilled) {
		this.literHeliumFilled = literHeliumFilled;
	}

	public void setLiterOxygenFilled(int literOxygenFilled) {
		this.literOxygenFilled = literOxygenFilled;
	}

	public void setPaymentReceiptDate(Date paymentReceiptDate) {
		this.paymentReceiptDate = paymentReceiptDate;
	}

	public void setPricePerLiterHelium(double pricePerLiterHelium) {
		this.pricePerLiterHelium = pricePerLiterHelium;
	}

	public void setPricePerLiterOxygen(double pricePerLiterOxygen) {
		this.pricePerLiterOxygen = pricePerLiterOxygen;
	}

	public void setStartPressure(int startPressure) {
		this.startPressure = startPressure;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}
}
