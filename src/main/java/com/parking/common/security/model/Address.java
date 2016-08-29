/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.parking.common.security.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.GenericGenerator;

public class Address implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id", length = 32)
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;

	@Column(name = "street1", length = 100)
	private String street1;

	@Column(name = "street2")
	private String street2;

	@Column(name = "street3")
	private String street3;

	@Column(name = "town")
	private String town;

	@Column(name = "post_code")
	private String postCode;

	@Column(name = "country")
	private String country;

	@Column(name = "phone1")
	private String phone1;

	@Column(name = "phone2")
	private String phone2;

	@Column(name = "mobile_phone")
	private String mobilePhone;

	@Column(name = "fax")
	private String fax;

	@Column(name = "is_active", nullable = true, length = 5, unique = false)
	private boolean isActive;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStreet1() {
		return this.street1;
	}

	public void setStreet1(String street1) {
		this.street1 = street1;
	}

	public String getStreet2() {
		return this.street2;
	}

	public void setStreet2(String street2) {
		this.street2 = street2;
	}

	public String getStreet3() {
		return this.street3;
	}

	public void setStreet3(String street3) {
		this.street3 = street3;
	}

	public String getTown() {
		return this.town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	public String getPostCode() {
		return this.postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPhone1() {
		return this.phone1;
	}

	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}

	public String getPhone2() {
		return this.phone2;
	}

	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	public String getMobilePhone() {
		return this.mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public boolean isActive() {
		return this.isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int hashCode() {
		int prime = 31;
		int result = 1;
		result = 31 * result + ((this.id == null) ? 0 : this.id.hashCode());
		return result;
	}

	public boolean equals(Object objAddress) {
		if (this == objAddress) {
			return true;
		}
		if ((objAddress != null) && (objAddress instanceof Address)) {
			Address address = (Address) objAddress;
			return ((address.getId() != null) && (address.getId().equals(getId())));
		}
		return false;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Address [id=").append(this.id);
		builder.append(", street1=").append(this.street1);
		builder.append(", street2=").append(this.street2);
		builder.append(", street3=").append(this.street3);
		builder.append(", town=").append(this.town);
		builder.append(", postCode=").append(this.postCode);
		builder.append(", country=").append(this.country);
		builder.append(", phone1=").append(this.phone1);
		builder.append(", phone2=").append(this.phone2);
		builder.append(", mobilePhone=").append(this.mobilePhone);
		builder.append(", fax=").append(this.fax);
		builder.append(", isActive=").append(this.isActive);
		builder.append(", user=").append(this.user);
		builder.append("]");
		return builder.toString();
	}
}