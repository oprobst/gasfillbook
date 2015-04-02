package de.tsvmalsch.shared.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Member implements Serializable {

	/**
	 * suid
	 */
	private static final long serialVersionUID = 3113539902485969147L;

	@OneToMany(mappedBy = "owner")
	private Set<Cylinder> cylinders;

	@NotNull
	private String email;

	@NotNull
	private String encodedPassword;

	@NotNull
	@Size(min = 1, max = 25)
	@Pattern(regexp = "[A-Za-z -]*", message = "must contain only letters and spaces")
	private String firstName;

	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private Long id;

	private Boolean isActive = true;

	private Boolean hasGasblenderBrevet = false;

	@NotNull
	@Size(min = 1, max = 25)
	@Pattern(regexp = "[A-Za-z -]*", message = "must contain only letters and spaces")
	private String lastName;

	private int maximumDebit;

	@NotNull
	private int memberNumber;

	private Boolean useDefaultPassword = true;

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
