package com.trade.entity;

import java.time.LocalDateTime;
import java.util.UUID;

public class UserTestData {

	public final static UUID USER_UUID = UUID.randomUUID();
	public final static String LOGIN_NAME = "robin.sharp";
	public final static String USER_FULL_NAME = "Robin Sharp";
	public final static LocalDateTime USER_CREATION_TIME = LocalDateTime.now();
	public final static EntityStatus USER_ENTITIY_STATUS = EntityStatus.ACTIVE;
	public final static LocalDateTime USER_ENTITY_STATUS_CHANGE_TIME = LocalDateTime.now();
	public final static String USER_EMAIL = "robin.sharp@robinsharp.com";
	public final static String USER_EMAIL_INVALID = "---";

	public final static User USER = new User(USER_UUID, LOGIN_NAME, USER_FULL_NAME,
			USER_CREATION_TIME, USER_ENTITIY_STATUS, USER_ENTITY_STATUS_CHANGE_TIME, USER_EMAIL);
	public final static User USER_INVALID = new User(USER_UUID, LOGIN_NAME, USER_FULL_NAME,
			USER_CREATION_TIME, USER_ENTITIY_STATUS, USER_ENTITY_STATUS_CHANGE_TIME, USER_EMAIL_INVALID);
	public final static User USER_BLANK = new User(USER_UUID, "", "",
			USER_CREATION_TIME, USER_ENTITIY_STATUS, USER_ENTITY_STATUS_CHANGE_TIME, "");
	public final static User USER_NULL = new User(null, null, null, null, null, null, null);
	public final static UUID OTHER_USER_UUID = UUID.randomUUID();
	public final static String OTHER_LOGIN_NAME = "robin.sharp";
	public final static String OTHER_USER_FULL_NAME = "Robin Sharp";
	public final static LocalDateTime OTHER_USER_CREATION_TIME = LocalDateTime.now();
	public final static EntityStatus OTHER_USER_ENTITIY_STATUS = EntityStatus.ACTIVE;
	public final static LocalDateTime OTHER_USER_ENTITY_STATUS_CHANGE_TIME = LocalDateTime.now();
	public final static String OTHER_USER_EMAIL = "robin.sharp@robinsharp.com";
	public final static User OTHER_USER = new User(OTHER_USER_UUID, OTHER_LOGIN_NAME, OTHER_USER_FULL_NAME,
			OTHER_USER_CREATION_TIME, OTHER_USER_ENTITIY_STATUS, OTHER_USER_ENTITY_STATUS_CHANGE_TIME, OTHER_USER_EMAIL);

	public final static User NEW_USER() {
		return new User(UUID.randomUUID(), LOGIN_NAME, USER_FULL_NAME,
				USER_CREATION_TIME, USER_ENTITIY_STATUS, USER_ENTITY_STATUS_CHANGE_TIME, USER_EMAIL);
	}
}
