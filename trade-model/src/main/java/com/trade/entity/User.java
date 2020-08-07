package com.trade.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.trade.Entity;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Data
@NoArgsConstructor
@Table("tradeuser")
public class User implements Entity, Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull(message = "userId is mandatory")
	@PrimaryKey
	private UUID userId;

	@NotBlank(message = "loginName is mandatory")
	@Column
	private String loginName;

	@NotBlank(message = "fullName is mandatory")
	@Column
	private String fullName;

	@NotNull(message = "creationTime is mandatory")
	@Column
	private LocalDateTime creationTime;

	@NotNull(message = "entityStatus is mandatory")
	@Column
	private EntityStatus entityStatus;

	@NotNull(message = "statusChangeTime is mandatory")
	@Column
	private LocalDateTime statusChangeTime;

	@Email(message = "email must be valid")
	@Column
	private String email;

	public User(UUID userId, String loginName, String fullName, LocalDateTime creationTime,
				EntityStatus entityStatus, LocalDateTime statusChangeTime, String email) {
		this.userId = userId;
		this.loginName = loginName;
		this.fullName = fullName;
		this.creationTime = creationTime;
		this.entityStatus = entityStatus;
		this.statusChangeTime = statusChangeTime;
		this.email = email;
	}

	public UUID getUserId() {
		return userId;
	}

	@JsonIgnore
	public UUID getEntityId() {
		return userId;
	}

	@JsonIgnore
	public String getEntityType() {
		return User.class.getSimpleName();
	}

	public String getLoginName() {
		return loginName;
	}

	@JsonIgnore
	public String getEntityCode() {
		return loginName;
	}

	public String getFullName() {
		return fullName;
	}

	public LocalDateTime getCreationTime() {
		return creationTime;
	}

	public EntityStatus getEntityStatus() {
		return entityStatus;
	}

	public LocalDateTime getStatusChangeTime() {
		return statusChangeTime;
	}

	public String getEmail() {
		return email;
	}

	@JsonIgnore
	public List<String> getEmails() {
		return Collections.singletonList(email);
	}

	@Override
	public String toString() {
		return new StringBuilder("class=").
				append(User.class.getSimpleName()).
				append(", userId=").append(userId).
				append(", loginName=").append(loginName).
				append(", fullName=").append(fullName).
				append(", creationTime=").append(creationTime).
				append(", entityStatus=").append(entityStatus).
				append(", statusChangeTime=").append(statusChangeTime).
				append(", email=").append(email).
				toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		User user = (User) o;
		return Objects.equals(userId, user.userId);
	}

	@Override
	public int hashCode() {
		return userId.hashCode();
	}
}