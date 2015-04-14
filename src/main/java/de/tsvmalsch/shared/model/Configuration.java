package de.tsvmalsch.shared.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
	@OneToMany(mappedBy = "administrators", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Member> administrators;

	/**
	 * All members who are allowed to provide briefings for the blending
	 * facility.
	 */
	@OneToMany(mappedBy = "blendingInstructors", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Member> blendingInstructors;

	/** DB generated uuid. */
	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private Long id;

	/**
	 * All invoice notifications are not only send to the blending member, but
	 * also to the accounting. This property lists all members to be notified.
	 */
	@OneToMany(mappedBy = "emailNotifications", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Member> invoiceNotificationMails;

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

	/**
	 * Gets the all members who are allowed to configure the application.
	 *
	 * @return the all members who are allowed to configure the application
	 */
	public List<Member> getAdministrators() {
		return administrators;
	}

	/**
	 * Gets the all members who are allowed to provide briefings for the
	 * blending facility.
	 *
	 * @return the all members who are allowed to provide briefings for the
	 *         blending facility
	 */
	public List<Member> getBlendingInstructors() {
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

	/**
	 * Gets the all invoice notifications are not only send to the blending
	 * member, but also to the accounting.
	 *
	 * @return the all invoice notifications are not only send to the blending
	 *         member, but also to the accounting
	 */
	public List<Member> getInvoiceNotificationMails() {
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

	/**
	 * Sets the all members who are allowed to configure the application.
	 *
	 * @param administrators
	 *            the new all members who are allowed to configure the
	 *            application
	 */
	public void setAdministrators(List<Member> administrators) {
		this.administrators = administrators;
	}

	/**
	 * Sets the all members who are allowed to provide briefings for the
	 * blending facility.
	 *
	 * @param blendingInstructors
	 *            the new all members who are allowed to provide briefings for
	 *            the blending facility
	 */
	public void setBlendingInstructors(List<Member> blendingInstructors) {
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

	/**
	 * Sets the all invoice notifications are not only send to the blending
	 * member, but also to the accounting.
	 *
	 * @param invoiceNotificationMails
	 *            the new all invoice notifications are not only send to the
	 *            blending member, but also to the accounting
	 */
	public void setInvoiceNotificationMails(
			List<Member> invoiceNotificationMails) {
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
