package com.trade;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum TradeStatus {

	EXECUTED("E"), ACCEPTED("A"), FAILED("F"), CANCELLED("C"), SUCCESS("S");

	private static final Map<String, TradeStatus> SHORTCUT_MAP;

	static {
		Map<String, TradeStatus> map = new ConcurrentHashMap();
		for (TradeStatus instance : TradeStatus.values()) {
			map.put(instance.getShortcut(), instance);
		}
		SHORTCUT_MAP = Collections.unmodifiableMap(map);
	}

	private final String shortcut;

	TradeStatus(String shortcut) {
		this.shortcut = shortcut;
	}

	public static TradeStatus getTradeStatus(String shortcut) {
		return SHORTCUT_MAP.get(shortcut);
	}

	public String getShortcut() {
		return shortcut;
	}
}
