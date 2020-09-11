package com.trade.rates;

import com.trade.BidOffer;
import com.trade.Spread;
import com.trade.fwd.FwdDate;
import org.springframework.data.cassandra.core.mapping.Column;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.NavigableMap;
import java.util.Objects;
import java.util.SortedMap;

public class FwdRate extends AbstractRate {

	private static final long serialVersionUID = 1L;

	@NotBlank(message = "currency is mandatory")
	@Size(min = 3, max = 3)
	private String currency;

	@NotNull(message = "fwdDate is mandatory")
	@Column
	private FwdDate fwdDate;

	public FwdRate(String currency, FwdDate fwdDate, long timestamp, boolean stale, BidOffer fwdRate) {
		this(currency, fwdDate, timestamp, stale, singleNavigableMap(fwdRate));
	}

	public FwdRate(String currency, FwdDate fwdDate, long timestamp, boolean stale, NavigableMap<BigDecimal, BidOffer> fwdRateStructure) {
		super(currency + "/" + fwdDate.name(), timestamp, stale, fwdRateStructure);
		this.currency = currency;
		this.fwdDate = fwdDate;
	}

	public String getCurrency() {
		return currency;
	}

	public FwdDate getFwdDate() {
		return fwdDate;
	}

	public FwdRate spread(SortedMap<BigDecimal, Spread> spreadStructure) {
		return new FwdRate(currency, fwdDate, System.currentTimeMillis(), isStale(), newRateStructure(spreadStructure));
	}

	@Override
	public String toString() {
		return new StringBuilder(100).
				append("class=").append(FwdRate.class.getSimpleName()).
				append(", currency=").append(currency).
				append(", fwdDate=").append(fwdDate).
				append(", timestamp=").append(getTimestamp()).
				append(", stale=").append(isStale()).
				append(", fwdRateStructure=").append(getRateStructure()).
				toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		FwdRate fwdRate = (FwdRate) o;
		return Objects.equals(getSource(), fwdRate.getSource());
	}
}