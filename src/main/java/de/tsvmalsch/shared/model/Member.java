package de.tsvmalsch.shared.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;

@Entity
// @javax.persistence.Table(name = "MEMBER")
public class Member implements Serializable {

	/**
	 * suid
	 */
	private static final long serialVersionUID = 3113539902485969147L;

	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private Long id;

	@OneToMany(mappedBy = "owner")
	private Set<Cylinder> cylinders;

	@NotNull
	@Size(min = 1, max = 25)
	@Pattern(regexp = "[A-Za-z -]*", message = "must contain only letters and spaces")
	private String firstName;

	@NotNull
	@Size(min = 1, max = 25)
	@Pattern(regexp = "[A-Za-z -]*", message = "must contain only letters and spaces")
	private String lastName;

	@NotNull
	private int memberNumber;

	@NotNull
	private String email;

	@NotNull
	private String encodedPassword;

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

	public Long getId() {

		return id;
	}

	public String getLastName() {
		return lastName;
	}

	public int getMemberNumber() {
		return memberNumber;
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

	public void setId(Long id) {
		this.id = id;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setMemberNumber(int memberNumber) {
		this.memberNumber = memberNumber;
	}

}
