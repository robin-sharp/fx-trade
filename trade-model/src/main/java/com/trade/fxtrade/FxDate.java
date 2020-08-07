package com.trade.fxtrade;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum FxDate {

	SPOT("SPOT"),
	ONE_WEEK("1W"),
	TWO_WEEK("2W"),
	THREE_WEEK("3W"),
	FOUR_WEEK("4W"),
	ONE_MONTH("1M"),
	TWO_MONTH("2M"),
	THREE_MONTH("3M"),
	FOUR_MONTH("4M"),
	SIX_MONTH("6M"),
	NONE_MONTH("9M"),
	ONE_YEAR("1Y");

	private static final Map<String, FxDate> SHORTCUT_MAP;

	static {
		Map<String, FxDate> map = new ConcurrentHashMap();
		for (FxDate instance : FxDate.values()) {
			map.put(instance.getShortcut(), instance);
		}
		SHORTCUT_MAP = Collections.unmodifiableMap(map);
	}

	private final String shortcut;

	FxDate(String shortcut) {
		this.shortcut = shortcut;
	}

	public static FxDate getDate(String shortcut) {
		return SHORTCUT_MAP.get(shortcut);
	}

	public String getShortcut() {
		return shortcut;
	}
}
