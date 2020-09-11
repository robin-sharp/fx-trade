package com.trade.rates.product;

import com.trade.Rate;
import com.trade.Spread;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.temporal.TemporalUnit;
import java.util.Objects;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Rate Product determines the spread and throttling
 * <p>
 * Example rate products :-
 * - Internal - every 500 ms
 * - Interbank - every 1 second
 * - Corporate - every 1 minute
 * - Company- every 1 minute, higher spread
 * - Retail - every day, large spread
 */
@Data
@NoArgsConstructor
public class RateProduct<T extends Rate> {

	private String productName;
	private int throttleDuration;
	private TemporalUnit throttleTimeUnit;
	private SortedMap<BigDecimal, Spread> spreadStructure;

	public RateProduct(String productName, int throttleDuration, TemporalUnit throttleTimeUnit, Spread spread) {
		this(productName, throttleDuration, throttleTimeUnit, singleSpreadStructure(spread));
	}

	public RateProduct(String productName, int throttleDuration, TemporalUnit throttleTimeUnit, SortedMap<BigDecimal, Spread> spreadStructure) {
		this.productName = productName;
		this.throttleDuration = throttleDuration;
		this.throttleTimeUnit = throttleTimeUnit;
		this.spreadStructure = spreadStructure;
	}

	static SortedMap<BigDecimal, Spread> singleSpreadStructure(Spread spread) {
		TreeMap map = new TreeMap<>();
		map.put(BigDecimal.ZERO, spread);
		return map;
	}

	public String getProductName() {
		return productName;
	}

	public SortedMap<BigDecimal, Spread> getSpreadStructure() {
		return spreadStructure;
	}

	public int getThrottleDuration() {
		return throttleDuration;
	}

	public TemporalUnit getThrottleTimeUnit() {
		return throttleTimeUnit;
	}

	@Override
	public String toString() {
		return new StringBuilder("RateProduct{").
				append("productName=").append(productName).
				append(", throttleDuration=").append(throttleDuration).
				append(", throttleTimeUnit=").append(throttleTimeUnit).
				append(", spreadStructure=").append(spreadStructure).
				append('}').toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		RateProduct that = (RateProduct) o;
		return Objects.equals(productName, that.productName);
	}

	@Override
	public int hashCode() {
		return Objects.hash(productName);
	}
}
