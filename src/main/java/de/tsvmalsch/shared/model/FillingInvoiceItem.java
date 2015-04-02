package de.tsvmalsch.shared.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class FillingInvoiceItem implements Serializable {

	/**
	 * suid
	 */
	private static final long serialVersionUID = -8540656564045693342L;

	private FillingInvoiceItem adjustmentEntyFor;

	@NotNull
	@Enumerated(EnumType.STRING)
	private BlendingType blendingType;

	@ManyToOne
	@NotNull
	private Member creditor;

	@NotNull
	private Date dateOfFilling;

	@OneToMany(mappedBy = "owner")
	@Max(2)
	private Set<Cylinder> filledCylinder;

	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private Long id;
	
	private Date invoicingDate;

	private int literHeliumFilled;

	private int literOxygenFilled;

	private Date paymentReceiptDate;

	private int startingPressure;

	public FillingInvoiceItem getAdjustmentEntryFor() {
		return adjustmentEntyFor;
	}

	public BlendingType getBlendingType() {
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

	public int getStartingPressure() {
		return startingPressure;
	}

	public void setAdjustmentEntryFor(FillingInvoiceItem adjustmentEntryOf) {
		this.adjustmentEntyFor = adjustmentEntryOf;
	}

	public void setBlendingType(BlendingType blendingType) {
		this.blendingType = blendingType;
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

	public void setStartingPressure(int startingPressure) {
		this.startingPressure = startingPressure;
	}

}
