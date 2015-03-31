package de.tsvmalsch.shared.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Cylinder implements Serializable {

	/**
	 * suid
	 */
	private static final long serialVersionUID = -1393816010017456513L;

	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private Long id;

	@ManyToOne
	@NotNull
	private Member owner;

	@NotNull
	private String serialNumber;

	@NotNull
	private Float sizeInLiter;

	@NotNull
	private Date nextInspectionDate;

	@NotNull
	@Enumerated(EnumType.STRING)
	private CylinderType gasType;
	
	@OneToOne
	private Cylinder twinSetPartner;

	private String valveKit;

	private String note;

	private String name;

	public CylinderType getGasType() {
		return gasType;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Date getNextInspectionDate() {
		return nextInspectionDate;
	}

	public String getNote() {
		return note;
	}

	public Member getOwner() {
		return owner;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public Float getSizeInLiter() {
		return sizeInLiter;
	}

	public Cylinder getTwinSetPartner() {
		return twinSetPartner;
	}

	public String getValveKit() {
		return valveKit;
	}

	public void setGasType(CylinderType gasType) {
		this.gasType = gasType;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNextInspectionDate(Date nextInspectionDate) {
		this.nextInspectionDate = nextInspectionDate;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public void setOwner(Member owner) {
		this.owner = owner;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public void setSizeInLiter(Float sizeInLiter) {
		this.sizeInLiter = sizeInLiter;
	}

	public void setTwinSetPartner(Cylinder twinSetPartner) {
		this.twinSetPartner = twinSetPartner;
	}

	public void setValveKit(String valveKit) {
		this.valveKit = valveKit;
	}
}
