package com.trade.rates;

import com.trade.BidOffer;
import com.trade.Spread;
import com.trade.fxtrade.FxDate;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.Column;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.NavigableMap;
import java.util.Objects;
import java.util.SortedMap;

/**
 * http://www.londonfx.co.uk/trading.html
 */
@Data
@NoArgsConstructor
public class FxRate extends AbstractRate {

	private static final long serialVersionUID = 1L;

	@NotBlank(message = "currencyPair is mandatory")
	@Size(min = 6, max = 6)
	private String currencyPair;

	@NotNull(message = "fxDate is mandatory")
	@Column
	private FxDate fxDate;

	public FxRate(String currency, FxDate fxDate, long timestamp, boolean stale, BidOffer fwdRate) {
		this(currency, fxDate, timestamp, stale, singleNavigableMap(fwdRate));
	}

	public FxRate(String currencyPair, FxDate fxDate, long timestamp, boolean stale, NavigableMap<BigDecimal, BidOffer> fxRateStructure) {
		super(currencyPair + "/" + fxDate.name(), timestamp, stale, fxRateStructure);
		this.currencyPair = currencyPair;
		this.fxDate = fxDate;
	}

	public String getCurrencyPair() {
		return currencyPair;
	}

	public FxDate getFxDate() {
		return fxDate;
	}

	public FxRate spread(SortedMap<BigDecimal, Spread> spreadStructure) {
		return new FxRate(currencyPair, fxDate, getTimestamp(), isStale(), newRateStructure(spreadStructure));
	}

	@Override
	public String toString() {
		return new StringBuilder(100).
				append("class=").
				append(FxRate.class.getSimpleName()).
				append(", currencyPair=").append(currencyPair).
				append(", fxDate=").append(fxDate).
				append(", timestamp=").append(getTimestamp()).
				append(", stale=").append(isStale()).
				append(", fxRateStructure=").append(getRateStructure()).
				toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		FxRate fxRate = (FxRate) o;
		return Objects.equals(getSource(), fxRate.getSource());
	}
}