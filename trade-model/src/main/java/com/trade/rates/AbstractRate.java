package com.trade.rates;

import com.trade.BidOffer;
import com.trade.Rate;
import com.trade.Spread;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.NavigableMap;
import java.util.SortedMap;
import java.util.TreeMap;

import static java.math.BigDecimal.ZERO;

@Data
@NoArgsConstructor
public abstract class AbstractRate implements Rate, Serializable {

	private static final long serialVersionUID = 1L;

	private String source;
	private boolean stale;
	private long timestamp;

	@NotNull(message = "rateStructure is mandatory")
	private NavigableMap<BigDecimal, BidOffer> rateStructure;

	public AbstractRate(String source, long timestamp, boolean stale, NavigableMap<BigDecimal, BidOffer> rateStructure) {
		this.source = source;
		this.timestamp = timestamp;
		this.stale = stale;
		this.rateStructure = rateStructure;
	}

	static NavigableMap<BigDecimal, BidOffer> singleNavigableMap(BidOffer fwdRate) {
		final TreeMap<BigDecimal, BidOffer> rateStructure = new TreeMap<>();
		rateStructure.put(ZERO, fwdRate);
		return rateStructure;
	}

	@Override
	public String getSource() {
		return source;
	}

	@Override
	public long getTimestamp() {
		return timestamp;
	}

	@Override
	public boolean isStale() {
		return stale;
	}

	@Override
	public BidOffer getRate(BigDecimal amount) {
		if (rateStructure.size() == 1) {
			return rateStructure.firstEntry().getValue();
		} else {
			final BigDecimal rateKey = (BigDecimal) ((NavigableMap) rateStructure).floorKey(amount);
			return rateKey != null ? rateStructure.get(rateKey) : null;
		}
	}

	@Override
	public int hashCode() {
		return source.hashCode();
	}

	NavigableMap<BigDecimal, BidOffer> newRateStructure(SortedMap<BigDecimal, Spread> spreadStructure) {
		final TreeMap<BigDecimal, BidOffer> newRateStructure = new TreeMap<>();
		final BidOffer bidOffer = rateStructure.firstEntry().getValue();
		spreadStructure.forEach((k,v) -> newRateStructure.put(k, v.newBidOffer(bidOffer)));
		return newRateStructure;
	}
}
