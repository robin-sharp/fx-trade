package com.trade.entity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static java.util.Collections.EMPTY_LIST;

public class PartyTestData {

	public final static String ENTITY_STATUS_C = "C";
	public final static String ENTITY_STATUS_P = "P";
	public final static String ENTITY_STATUS_A = "A";
	public final static String ENTITY_STATUS_S = "S";
	public final static String ENTITY_STATUS_T = "T";

	public final static UUID PARTY_UUID = UUID.randomUUID();
	public final static String PARTY_CODE = "rsl";
	public final static String PARTY_FULL_NAME = "Robin Sharp Ltd";
	public final static String PARTY_COUNTRY_CODE = "GB";
	public final static LocalDateTime PARTY_CREATION_TIME = LocalDateTime.now();
	public final static EntityStatus PARTY_ENTITIY_STATUS = EntityStatus.ACTIVE;
	public final static LocalDateTime PARTY_ENTITY_STATUS_CHANGE_TIME = LocalDateTime.now();
	public final static List<String> PARTY_EMAILS = Arrays.asList("robin.sharp@robinsharp.com", "robin.sharp@robinsharp.co.uk");
	public final static List<String> PARTY_USERS = Arrays.asList("robin.sharp");
	public final static List<String> PARTY_EMAILS_INVALID = Arrays.asList("-", "?", "x");
	public final static List<String> PARTY_USERS_INVALID = Arrays.asList("", "");

	public final static Party PARTY = new Party(PARTY_UUID, PARTY_CODE, PARTY_FULL_NAME, PARTY_COUNTRY_CODE,
			PARTY_CREATION_TIME, PARTY_ENTITIY_STATUS, PARTY_ENTITY_STATUS_CHANGE_TIME, PARTY_EMAILS, PARTY_USERS);
	public final static Party PARTY_INVALID = new Party(PARTY_UUID, PARTY_CODE, PARTY_FULL_NAME, PARTY_COUNTRY_CODE,
			PARTY_CREATION_TIME, PARTY_ENTITIY_STATUS, PARTY_ENTITY_STATUS_CHANGE_TIME, PARTY_EMAILS_INVALID, PARTY_USERS_INVALID);
	public final static Party PARTY_BLANK = new Party(PARTY_UUID, "", "", "",
			PARTY_CREATION_TIME, PARTY_ENTITIY_STATUS, PARTY_ENTITY_STATUS_CHANGE_TIME, EMPTY_LIST, EMPTY_LIST);
	public final static Party PARTY_NULL = new Party(PARTY_UUID, null, null, null, null, null, null, null, null);
	public final static UUID COUNTERPARTY_UUID = UUID.randomUUID();
	public final static String COUNTERPARTY_CODE = "bank1";
	public final static String COUNTERPARTY_FULL_NAME = "Other Bank Ltd";
	public final static LocalDateTime COUNTERPARTY_CREATION_TIME = LocalDateTime.now();
	public final static EntityStatus COUNTERPARTY_ENTITIY_STATUS = EntityStatus.ACTIVE;
	public final static LocalDateTime COUNTERPARTY_ENTITY_STATUS_CHANGE_TIME = LocalDateTime.now();
	public final static List<String> COUNTERPARTY_EMAILS = Arrays.asList("trader@otherbank.com", "trader@otherbank.co.uk");
	public final static List<String> COUNTERPARTY_USERS = Arrays.asList("trader");

	public final static Party COUNTERPARTY = new Party(COUNTERPARTY_UUID, COUNTERPARTY_CODE, COUNTERPARTY_FULL_NAME, PARTY_COUNTRY_CODE,
			COUNTERPARTY_CREATION_TIME, COUNTERPARTY_ENTITIY_STATUS, COUNTERPARTY_ENTITY_STATUS_CHANGE_TIME, COUNTERPARTY_EMAILS, COUNTERPARTY_USERS);

	public final static String PARTY_SUPPORT_FIND_ROLE_NAME = "Party-Support-Find-Role";
	public final static String PARTY_SUPPORT_SAVE_ROLE_NAME = "Party-Support-Save-Role";
	public final static String PARTY_SUPPORT_DELETE_ROLE_NAME = "Party-Support-Delete-Role";
	public final static String PARTY_INTERNAL_FIND_ROLE_NAME = "Party-Internal-Find-Role";
	public final static String PARTY_INTERNAL_SAVE_ROLE_NAME = "Party-Internal-Save-Role";
	public final static String PARTY_EXTERNAL_FIND_ROLE_NAME = "Party-External-Find-Role";

	public final static Party NEW_PARTY() {
		return new Party(UUID.randomUUID(), PARTY_CODE, PARTY_FULL_NAME, PARTY_COUNTRY_CODE,
				PARTY_CREATION_TIME, PARTY_ENTITIY_STATUS, PARTY_ENTITY_STATUS_CHANGE_TIME, PARTY_EMAILS, PARTY_USERS);
	}
}
