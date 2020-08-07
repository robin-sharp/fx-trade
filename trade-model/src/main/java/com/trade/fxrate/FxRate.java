package com.trade.fxrate;

import com.trade.entity.Party;
import com.trade.fxtrade.FxDate;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.Column;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.NavigableMap;
import java.util.Objects;

/**
 * http://www.londonfx.co.uk/trading.html
 */
@Data
@NoArgsConstructor
public class FxRate implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotBlank(message = "currencyPair is mandatory")
	@Size(min = 6, max = 6)
	private String currencyPair;

	@NotNull(message = "date is mandatory")
	@Column
	private FxDate date;

	private boolean stale;

	@NotNull(message = "fxRateStructure is mandatory")
	private NavigableMap<BigDecimal, BigDecimal> fxRateStructure;

	public FxRate(String currencyPair, FxDate date, boolean stale, NavigableMap<BigDecimal, BigDecimal> fxRateStructure) {
		this.currencyPair = currencyPair;
		this.date = date;
		this.stale = stale;
		this.fxRateStructure = fxRateStructure;
	}

	public String getCurrencyPair() {
		return currencyPair;
	}

	public boolean isStale() {
		return stale;
	}

	public BigDecimal getRate(BigDecimal amount) {
		final BigDecimal rateKey = fxRateStructure.floorKey(amount);
		return rateKey != null ? fxRateStructure.get(rateKey) : null;
	}

	@Override
	public String toString() {
		return new StringBuilder("class=").
				append(Party.class.getSimpleName()).
				append(", currencyPair=").append(currencyPair).
				append(", date=").append(date).
				append(", stale=").append(stale).
				append(", fxRateStructure=").append(fxRateStructure).
				toString();
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		FxRate fxRate = (FxRate) o;
		return Objects.equals(currencyPair, fxRate.currencyPair) &&
				date == fxRate.date;
	}

	@Override
	public int hashCode() {
		return Objects.hash(currencyPair, date);
	}
}