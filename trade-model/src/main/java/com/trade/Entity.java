package com.trade;

import com.trade.entity.EntityStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface Entity {

	UUID getEntityId();

	String getEntityType();

	String getEntityCode();

	String getFullName();

	LocalDateTime getCreationTime();

	EntityStatus getEntityStatus();

	LocalDateTime getStatusChangeTime();

	List<String> getEmails();
}
