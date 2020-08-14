package com.trade.entity;

import org.junit.jupiter.api.Test;
import org.springframework.validation.BeanPropertyBindingResult;

import static com.trade.entity.UserTestData.*;
import static com.trade.util.TestUtil.*;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

	@Test
	public void testIndividualConstructor() {
		User user = USER;
		assertEquals(USER_UUID, user.getUserId());
		assertEquals(LOGIN_NAME, user.getLoginName());
		assertEquals(USER_FULL_NAME, user.getFullName());
		assertEquals(USER_CREATION_TIME, user.getCreationTime());
		assertEquals(USER_ENTITIY_STATUS, user.getEntityStatus());
		assertEquals(USER_ENTITY_STATUS_CHANGE_TIME, user.getStatusChangeTime());
		assertEquals(USER_EMAIL, user.getEmail());

		assertEquals(USER_UUID, user.getEntityId());
		assertEquals(User.class.getSimpleName(), user.getEntityType());
		assertEquals(LOGIN_NAME, user.getEntityCode());
	}

	@Test
	public void testUserIsInvalidValues() {
		BeanPropertyBindingResult result = getValidationResult(USER_INVALID);
		assertEquals(1, result.getAllErrors().size());
	}

	@Test
	public void testUserIsInvalidBlankValues() {
		BeanPropertyBindingResult result = getValidationResult(USER_BLANK);
		assertEquals(2, result.getAllErrors().size());
	}

	@Test
	public void testUserIsInvalidNullValues() {
		BeanPropertyBindingResult result = getValidationResult(USER_NULL);
		assertEquals(6, result.getAllErrors().size());
	}

	@Test
	public void testIndividualSerializable() {
		assertEquals(assertSerializable(USER), USER);
	}

	@Test
	public void testJsonUser() {
		assertJson(USER, USER.getClass());
	}

	@Test
	public void testUserToString() {
		assertTrue(USER.toString().matches("class=User, userId=(.*?), loginName=robin.sharp, fullName=Robin Sharp, creationTime=(.*?), entityStatus=ACTIVE, statusChangeTime=(.*?), email=robin.sharp@robinsharp.com"));
	}

	@Test
	public void testPartyEquals() {
		assertEquals(USER, USER);
		assertNotEquals(USER, OTHER_USER);
		assertNotEquals(OTHER_USER, USER);
	}

	@Test
	public void testHashCode() {
		assertEquals(USER.getUserId().hashCode(), USER.hashCode());
	}

	@Test
	public void testJson() {
		assertJson(USER, User.class);
	}

	@Test
	public void testUserIsValid() {
		assertValid(USER);
	}

}
