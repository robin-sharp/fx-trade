package com.trade.fxtrade;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum FxTradeRole {

	FXTRADE_SUPPORT_FIND_ROLE("FxTrade-Support-Find-Role"),
	FXTRADE_SUPPORT_SAVE_ROLE("FxTrade-Support-Save-Role"),
	FXTRADE_SUPPORT_DELETE_ROLE("FxTrade-Support-Delete-Role"),
	FXTRADE_INTERNAL_FIND_ROLE("FxTrade-Internal-Find-Role"),
	FXTRADE_INTERNAL_SAVE_ROLE("FxTrade-Internal-Save-Role"),
	FXTRADE_EXTERNAL_FIND_ROLE("FxTrade-External-Find-Role"),
	FXTRADE_EXTERNAL_SAVE_ROLE("FxTrade-External-Save-Role");

	private static final Map<String, FxTradeRole> NAME_MAP;

	static {
		Map<String, FxTradeRole> map = new ConcurrentHashMap();
		for (FxTradeRole instance : FxTradeRole.values()) {
			map.put(instance.getRoleName(), instance);
		}
		NAME_MAP = Collections.unmodifiableMap(map);
	}

	private final String roleName;

	FxTradeRole(String roleName) {
		this.roleName = roleName;
	}

	public static FxTradeRole getFxTradeRole(String roleName) {
		return NAME_MAP.get(roleName);
	}

	public String getRoleName() {
		return roleName;
	}
}
