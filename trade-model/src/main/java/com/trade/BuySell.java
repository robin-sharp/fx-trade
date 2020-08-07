package com.trade;

public enum BuySell {
	BUY("B"), SELL("S");

	private final String shortcut;

	BuySell(String shortcut) {
		this.shortcut = shortcut;
	}

	public static BuySell getBuySell(final String shortcut) {
		switch (shortcut) {
			case "B":
				return BUY;
			case "S":
				return SELL;
			default:
				return null;
		}
	}

	public String getShortcut() {
		return shortcut;
	}
}
