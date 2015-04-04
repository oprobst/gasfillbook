package de.tsvmalsch.shared.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Max;
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
	 * If a user do a mistake when booking a gas blending execution, he can
	 * define another FillingInvoiceItem which is an adjustment of the former
	 * item.
	 * 
	 */
	private FillingInvoiceItem adjustmentEntryFor;

	/**
	 * Which kind of blending was executed.
	 */
	@NotNull
	private int blendingType;

	/**
	 * The creditor, which shall be accounted. The member filling the cylinder
	 * can be a different one that the one who pays the bill.
	 */
	@ManyToOne
	@NotNull
	private Member creditor;

	/**
	 * The price per liter He when executing the gas blending
	 */
	private float pricePerLiterHelium;

	/**
	 * The price per liter O2 when executing the gas blending
	 */
	private float pricePerLiterOxygen;

	/**
	 * Pressure in cylinder when starting gas blending
	 */
	private int startPressure;

	/**
	 * The date when the cylinder was filled.
	 */
	@NotNull
	private Date dateOfFilling;

	/**
	 * The cylinder filled.
	 */
	@OneToMany(mappedBy = "owner", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@Max(2)
	private Set<Cylinder> filledCylinder;

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
	private int literHeliumFilled;
	/**
	 * The amount of liter Oxygen filled.
	 */
	private int literOxygenFilled;

	/**
	 * The date when the payment was received.
	 */
	private Date paymentReceiptDate;

	public int getStartPressure() {
		return startPressure;
	}

	public void setStartPressure(int startPressure) {
		this.startPressure = startPressure;
	}

	public FillingInvoiceItem getAdjustmentEntryFor() {
		return adjustmentEntryFor;
	}

	public int getBlendingType() {
		return blendingType;
	}

	public Member getCreditor() {
		return creditor;
	}

	public Date getDateOfFilling() {
		return dateOfFilling;
	}

	public Set<Cylinder> getFilledCylinder() {
		return filledCylinder;
	}

	public Long getId() {
		return id;
	}

	public Date getInvoicingDate() {
		return invoicingDate;
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

	public float getPricePerLiterHelium() {
		return pricePerLiterHelium;
	}

	public float getPricePerLiterOxygen() {
		return pricePerLiterOxygen;
	}

	public void setAdjustmentEntryFor(FillingInvoiceItem adjustmentEntryOf) {
		this.adjustmentEntryFor = adjustmentEntryOf;
	}

	public void setBlendingType(int air) {
		this.blendingType = air;
	}

	public void setCreditor(Member creditor) {
		this.creditor = creditor;
	}

	public void setDateOfFilling(Date dateOfFilling) {
		this.dateOfFilling = dateOfFilling;
	}

	public void setFilledCylinder(Set<Cylinder> filledCylinder) {
		this.filledCylinder = filledCylinder;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setInvoicingDate(Date invoicingDate) {
		this.invoicingDate = invoicingDate;
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

	public void setPricePerLiterHelium(float pricePerLiterHelium) {
		this.pricePerLiterHelium = pricePerLiterHelium;
	}

	public void setPricePerLiterOxygen(float pricePerLiterOxygen) {
		this.pricePerLiterOxygen = pricePerLiterOxygen;
	}

}
