package de.tsvmalsch.shared.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;

/**
 * This is a data object representing a club member.
 * 
 * @author Oliver Probst
 *
 */
@Entity
public class Member implements Serializable {

	/**
	 * suid
	 */
	private static final long serialVersionUID = 3113539902485969147L;

	/**
	 * Cylinders owned by this member
	 */
	@OneToMany(mappedBy = "owner", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<Cylinder> cylinders;

	/**
	 * The email address of this member
	 */
	@NotNull
	private String email;

	/**
	 * The crypted password
	 */
	@NotNull
	private String encodedPassword;

	/**
	 * Well... The members first name.
	 */
	@NotNull
	@Size(min = 1, max = 25)
	@Pattern(regexp = "[A-Za-z -]*", message = "must contain only letters and spaces")
	private String firstName;

	/**
	 * DB generated uuid
	 */
	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private Long id;

	/**
	 * Flag indicating if the person is still an active club member
	 */
	private Boolean isActive = true;

	/**
	 * Flag indicating if the user has a gas blenber brevet and is allowed to
	 * fill with the partitial blending method in general.
	 */
	private Boolean hasGasblenderBrevet = false;

	/**
	 * You might have guessed it already: This is the members last name. :-)
	 */
	@NotNull
	@Size(min = 1, max = 25)
	@Pattern(regexp = "[A-Za-z -]*", message = "must contain only letters and spaces")
	private String lastName;

	/**
	 * The invoice will be sent every year or at a fixed amount of debt
	 * configured global. If a member wish, he can define a personal maximum
	 * debt amount lower than the global one.
	 */
	private int maximumDebit;

	/**
	 * Clubs member number
	 */
	@NotNull
	private int memberNumber;

	/**
	 * The user hasn't changed the default password so far.
	 */
	private Boolean useDefaultPassword = true;

	/**
	 * Defines which rights the user has. Depends no last briefing and on users
	 * brevets.
	 */
	@Embedded
	private UserRights rights;

	public Set<Cylinder> getCylinders() {
		return cylinders;
	}

	public String getEmail() {
		return email;
	}

	public String getEncodedPassword() {
		return encodedPassword;
	}

	public String getFirstName() {
		return firstName;
	}

	public Boolean getHasGasblenderBrevet() {
		return hasGasblenderBrevet;
	}

	public Long getId() {

		return id;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public String getLastName() {
		return lastName;
	}

	public int getMaximumDebit() {
		return maximumDebit;
	}

	public int getMemberNumber() {
		return memberNumber;
	}

	public UserRights getRights() {
		return rights;
	}

	public Boolean getUseDefaultPassword() {
		return useDefaultPassword;
	}

	public void setCylinders(Set<Cylinder> cylinders) {
		this.cylinders = cylinders;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setEncodedPassword(String encodedPassword) {
		this.encodedPassword = encodedPassword;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setHasGasblenderBrevet(Boolean hasGasblenderBrevet) {
		this.hasGasblenderBrevet = hasGasblenderBrevet;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setMaximumDebit(int maximumDebit) {
		this.maximumDebit = maximumDebit;
	}

	public void setMemberNumber(int memberNumber) {
		this.memberNumber = memberNumber;
	}

	public void setRights(UserRights rights) {
		this.rights = rights;
	}

	public void setUseDefaultPassword(Boolean useDefaultPassword) {
		this.useDefaultPassword = useDefaultPassword;
	}

}
