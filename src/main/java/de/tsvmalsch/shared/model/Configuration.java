package de.tsvmalsch.shared.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;

/**
 * This is a data object representing a application configuration done at a
 * specific time.
 * 
 * @author Oliver Probst
 *
 */
@Entity
public class Configuration implements Serializable {

	/**
	 * sUID
	 */
	private static final long serialVersionUID = -1789829022454853247L;

	/**
	 * All members who are allowed to configure the application.
	 */
	private String administrators;

	/**
	 * All members who are allowed to provide briefings for the blending
	 * facility, separated by comma
	 */
	private String blendingInstructors;

	/** DB generated uuid. */
	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private Long id;

	/**
	 * All invoice notifications are not only send to the blending member, but
	 * also to the accounting. This property lists all members to be notified.
	 */
	private String invoiceNotificationMails;

	/** The Oxygen percentage of current cascade gas. */
	@NotNull
	private Double nxCascadeOxygen = 39.0d;

	/** The price per barL Helium. */
	@NotNull
	private Double pricePerBarLHe = 0.0175d;

	/** The price per barL o2. */
	@NotNull
	private Double pricePerBarLO2 = 0.0055d;

	/** Person who saved the new configuration. */
	@OneToOne
	private Member storedBy;

	/** The time stamp when the configuration applied. */
	@NotNull
	private Date timeStamp;

	/** Information text shown on login dialog. */
	private String welcomeText;
 
	public String getAdministrators() {
		return administrators;
	}
 
	public String getBlendingInstructors() {
		return blendingInstructors;
	}

	/**
	 * Gets the dB generated uuid.
	 *
	 * @return the dB generated uuid
	 */
	public Long getId() {
		return id;
	}

	public String getInvoiceNotificationMails() {
		return invoiceNotificationMails;
	}

	/**
	 * Gets the Oxygen percentage of current cascade gas.
	 *
	 * @return the Oxygen percentage of current cascade gas
	 */
	public Double getNxCascadeOxygen() {
		return nxCascadeOxygen;
	}

	/**
	 * Gets the price per barL Helium.
	 *
	 * @return the price per barL Helium
	 */
	public Double getPricePerBarLHe() {
		return pricePerBarLHe;
	}

	/**
	 * Gets the price per barL o2.
	 *
	 * @return the price per barL o2
	 */
	public Double getPricePerBarLO2() {
		return pricePerBarLO2;
	}

	 
	/**
	 * Gets the person who saved the new configuration.
	 *
	 * @return the person who saved the new configuration
	 */
	public Member getStoredBy() {
		return storedBy;
	}

	 

	/**
	 * Gets the time stamp when the configuration applied.
	 *
	 * @return the time stamp when the configuration applied
	 */
	public Date getTimeStamp() {
		return timeStamp;
	}

	/**
	 * Gets the information text shown on login dialog.
	 *
	 * @return the information text shown on login dialog
	 */
	public String getWelcomeText() {
		return welcomeText;
	}

	public void setAdministrators(String administrators) {
		this.administrators = administrators;
	}

	public void setBlendingInstructors(String blendingInstructors) {
		this.blendingInstructors = blendingInstructors;
	}

	/**
	 * Sets the dB generated uuid.
	 *
	 * @param id
	 *            the new dB generated uuid
	 */
	public void setId(Long id) {
		this.id = id;
	}

	public void setInvoiceNotificationMails(String invoiceNotificationMails) {
		this.invoiceNotificationMails = invoiceNotificationMails;
	}

	/**
	 * Sets the Oxygen percentage of current cascade gas.
	 *
	 * @param nxCascadeOxygen
	 *            the new Oxygen percentage of current cascade gas
	 */
	public void setNxCascadeOxygen(Double nxCascadeOxygen) {
		this.nxCascadeOxygen = nxCascadeOxygen;
	}

	/**
	 * Sets the price per barL Helium.
	 *
	 * @param pricePerBarLHe
	 *            the new price per barL Helium
	 */
	public void setPricePerBarLHe(Double pricePerBarLHe) {
		this.pricePerBarLHe = pricePerBarLHe;
	}

	/**
	 * Sets the price per barL o2.
	 *
	 * @param pricePerBarLO2
	 *            the new price per barL o2
	 */
	public void setPricePerBarLO2(Double pricePerBarLO2) {
		this.pricePerBarLO2 = pricePerBarLO2;
	}

	/**
	 * Sets the person who saved the new configuration.
	 *
	 * @param storedBy
	 *            the new person who saved the new configuration
	 */
	public void setStoredBy(Member storedBy) {
		this.storedBy = storedBy;
	}

	/**
	 * Sets the time stamp when the configuration applied.
	 *
	 * @param timeStamp
	 *            the new time stamp when the configuration applied
	 */
	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	/**
	 * Sets the information text shown on login dialog.
	 *
	 * @param welcomeText
	 *            the new information text shown on login dialog
	 */
	public void setWelcomeText(String welcomeText) {
		this.welcomeText = welcomeText;
	}
}
