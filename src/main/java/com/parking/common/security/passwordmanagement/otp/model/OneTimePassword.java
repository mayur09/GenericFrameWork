/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.parking.common.security.passwordmanagement.otp.model;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.parking.common.security.model.User;
import com.parking.common.security.passwordmanagement.otp.OTPStatus;

@NamedQueries({
		@javax.persistence.NamedQuery(name = "checkUserAlreadyRequestedForOTP", query = "from OneTimePassword OTP where OTP.status = :status and OTP.user.id = :userId and OTP.createdDate >= :otpCreatedDate") })
@Entity
@Table(name = "one_time_password")
public class OneTimePassword implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String UPDATE_OTP_STATUS_BY_ID = "update OneTimePassword otp set otp.status = :status where otp.id = :otpId";
	public static final String CHECK_USER_ALREADY_REQUESTED_FOR_OTP = "checkUserAlreadyRequestedForOTP";
	static final String CHECK_USER_ALREADY_REQUESTED_FOR_OTP_QUERY = "from OneTimePassword OTP where OTP.status = :status and OTP.user.id = :userId and OTP.createdDate >= :otpCreatedDate";

	@Id
	@Column(name = "id", length = 32)
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;

	@Column(name = "otp", nullable = false)
	private String otp;

	@Column(name = "created_date")
	private Calendar createdDate;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@Column(name = "status", nullable = true, length = 5, unique = false)
	private OTPStatus status;
	private transient String plainOTP;

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOtp() {
		return this.otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public Calendar getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Calendar createdDate) {
		this.createdDate = createdDate;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public OTPStatus getStatus() {
		return this.status;
	}

	public void setStatus(OTPStatus status) {
		this.status = status;
	}

	public String getPlainOTP() {
		return this.plainOTP;
	}

	public void setPlainOTP(String plainOTP) {
		this.plainOTP = plainOTP;
	}

	public int hashCode() {
		int prime = 31;
		int result = 1;
		result = 31 * result + ((this.id == null) ? 0 : this.id.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if ((obj != null) && (obj instanceof OneTimePassword)) {
			OneTimePassword otpObj = (OneTimePassword) obj;
			return ((otpObj.getId() != null) && (otpObj.getId().equals(getId())));
		}
		return false;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OneTimePassword [id=").append(this.id);
		builder.append(", otp=").append(this.otp);
		builder.append(", createdDate=").append(this.createdDate);
		builder.append(", user=").append(this.user.getUserName());
		builder.append(", status=").append(this.status);
		builder.append("]");
		return builder.toString();
	}
}