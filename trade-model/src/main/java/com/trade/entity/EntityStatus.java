package com.trade.entity;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum EntityStatus {
	CREATED("C"), PENDING("P"), ACTIVE("A"), SUSPENDED("S"), TERMINATED("T");

	private static final Map<String, EntityStatus> SHORTCUT_MAP;

	static {
		Map<String, EntityStatus> map = new ConcurrentHashMap();
		for (EntityStatus instance : EntityStatus.values()) {
			map.put(instance.getShortcut(), instance);
		}
		SHORTCUT_MAP = Collections.unmodifiableMap(map);
	}

	private final String shortcut;

	EntityStatus(String shortcut) {
		this.shortcut = shortcut;
	}

	public static EntityStatus getEntityStatus(String shortcut) {
		return SHORTCUT_MAP.get(shortcut);
	}

	public String getShortcut() {
		return shortcut;
	}
}
