package com.trade.entity;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum PartyRole {

	PARTY_SUPPORT_FIND_ROLE("Party-Support-Find-Role"),
	PARTY_SUPPORT_SAVE_ROLE("Party-Support-Save-Role"),
	PARTY_SUPPORT_DELETE_ROLE("Party-Support-Delete-Role"),
	PARTY_INTERNAL_FIND_ROLE("Party-Internal-Find-Role"),
	PARTY_INTERNAL_SAVE_ROLE("Party-Internal-Save-Role"),
	PARTY_EXTERNAL_FIND_ROLE("Party-External-Find-Role");

	private static final Map<String, PartyRole> NAME_MAP;

	static {
		Map<String, PartyRole> map = new ConcurrentHashMap();
		for (PartyRole instance : PartyRole.values()) {
			map.put(instance.getRoleName(), instance);
		}
		NAME_MAP = Collections.unmodifiableMap(map);
	}

	private final String roleName;

	PartyRole(String roleNname) {
		this.roleName = roleNname;
	}

	public static PartyRole getPartyRole(String roleName) {
		return NAME_MAP.get(roleName);
	}

	public String getRoleName() {
		return roleName;
	}

	public String toString() {
		return roleName;
	}
}
