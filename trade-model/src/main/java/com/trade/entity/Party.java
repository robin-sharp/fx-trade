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
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Data
@NoArgsConstructor
@Table("tradeparty")
public class Party implements Entity, Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull(message = "partyId is mandatory")
	@PrimaryKey
	private UUID partyId;

	@NotBlank(message = "partyCode is mandatory")
	@Column
	private String partyCode;

	@NotBlank(message = "fullName is mandatory")
	@Column
	private String fullName;

	@NotBlank(message = "countryCode is mandatory")
	@Column
	@Size(min = 2, max = 2)
	private String countryCode;

	@NotNull(message = "creationTime is mandatory")
	@Column
	private LocalDateTime creationTime;

	@NotNull(message = "entityStatus is mandatory")
	@Column
	private EntityStatus entityStatus;

	@NotNull(message = "statusChangeTime is mandatory")
	@Column
	private LocalDateTime statusChangeTime;

	@Column
	private List<@Email(message = "emails must be valid") String> emails;

	public Party(UUID partyId, String partyCode, String fullName, String countryCode, LocalDateTime creationTime,
				 EntityStatus entityStatus, LocalDateTime statusChangeTime, List<String> emails) {
		this.partyId = partyId;
		this.partyCode = partyCode;
		this.fullName = fullName;
		this.countryCode = countryCode;
		this.creationTime = creationTime;
		this.entityStatus = entityStatus;
		this.statusChangeTime = statusChangeTime;
		this.emails = emails;
	}

	public UUID getPartyId() {
		return partyId;
	}

	@JsonIgnore
	public UUID getEntityId() {
		return partyId;
	}

	@JsonIgnore
	public String getEntityType() {
		return Party.class.getSimpleName();
	}

	public String getPartyCode() {
		return partyCode;
	}

	@JsonIgnore
	public String getEntityCode() {
		return partyCode;
	}

	public String getFullName() {
		return fullName;
	}

	public String getCountryCode() {
		return countryCode;
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

	public List<String> getEmails() {
		return emails;
	}

	@Override
	public String toString() {
		return new StringBuilder("class=").
				append(Party.class.getSimpleName()).
				append(", partyId=").append(partyId).
				append(", partyCode=").append(partyCode).
				append(", fullName=").append(fullName).
				append(", countryCode=").append(countryCode).
				append(", creationTime=").append(creationTime).
				append(", entityStatus=").append(entityStatus).
				append(", statusChangeTime=").append(statusChangeTime).
				append(", emails=").append(emails).
				toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Party party = (Party) o;
		return Objects.equals(partyId, party.partyId);
	}

	@Override
	public int hashCode() {
		return partyId.hashCode();
	}
}