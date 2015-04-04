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
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;

/**
 * Data model for a scuba cylinder.
 * 
 * Records all relevant data, like size, next inspection, owner, etc.
 * 
 * @author Oliver Probst
 *
 */
@Entity
public class Cylinder implements Serializable {

	/**
	 * suid
	 */
	private static final long serialVersionUID = -1393816010017456513L;

	/**
	 * Type of gas to be filled. Marks e.g. if tank is oxygen clean or dedicated
	 * to argon usage.
	 */
	@NotNull
	@Enumerated(EnumType.STRING)
	private CylinderType gasType;

	/**
	 * DB generated key
	 */
	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private Long id;

	/**
	 * The maximum gas pressure the cylinder is build for.
	 */
	@Min(200)
	@Max(300)
	private int maximumPreasure;

	/**
	 * An owner defined name for the cylinder. For users with many cylinders
	 * very useful.
	 */
	private String name;

	/**
	 * Date of next required inspection by TÜV.
	 */
	@NotNull
	private Date nextInspectionDate;

	/**
	 * A generic note of the owner for this cylinder
	 */
	private String note;

	/**
	 * The owner of the cylinder.
	 */
	@ManyToOne
	@NotNull
	private Member owner;

	/**
	 * The cylinders fabrication serial number
	 */
	@NotNull
	private String serialNumber;

	/**
	 * Size of the cylinder
	 */
	@NotNull
	private Double sizeInLiter;

	/**
	 * Null for mono cylinder. If this cylinder is used in a twin set, the other
	 * cylinder of the set is referenced here.
	 */
	@OneToOne
	private Cylinder twinSetPartner;

	/**
	 * Free text field for the owner to name the valve kit. Useful to determine
	 * spare parts.
	 */
	private String valveKit;

	public CylinderType getGasType() {
		return gasType;
	}

	public Long getId() {
		return id;
	}

	public int getMaximumPreasure() {
		return maximumPreasure;
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

	public Double getSizeInLiter() {
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

	public void setMaximumPreasure(int maximumPreasure) {
		this.maximumPreasure = maximumPreasure;
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

	public void setSizeInLiter(Double sizeInLiter) {
		this.sizeInLiter = sizeInLiter;
	}

	public void setTwinSetPartner(Cylinder twinSetPartner) {
		this.twinSetPartner = twinSetPartner;
	}

	public void setValveKit(String valveKit) {
		this.valveKit = valveKit;
	}
}
