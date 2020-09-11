package com.trade;

import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import static java.lang.String.format;

@NoArgsConstructor
public class Spread implements Serializable {

	public final static int ADDITION = 0;
	public final static int MULTIPLICATION = 1;
	private static final long serialVersionUID = 1L;
	private int spreadType;

	private BigDecimal bidSpread;
	private BigDecimal offerSpread;

	public Spread(BigDecimal spread) {
		this(spread, ADDITION);
	}

	public Spread(BigDecimal spread, int spreadType) {
		this(spread, spread, spreadType);
	}

	public Spread(BigDecimal bidSpread, BigDecimal offerSpread) {
		this(bidSpread, offerSpread, ADDITION);
	}

	public Spread(BigDecimal bidSpread, BigDecimal offerSpread, int spreadType) {
		this.bidSpread = bidSpread;
		this.offerSpread = offerSpread;
		this.spreadType = spreadType;
	}

	public BigDecimal getBidSpread() {
		return bidSpread;
	}

	public BigDecimal getOfferSpread() {
		return offerSpread;
	}

	public int getSpreadType() {
		return spreadType;
	}

	public BidOffer newBidOffer(BidOffer bidOffer) {
		if (spreadType == ADDITION) {
			return new BidOffer(bidOffer.getBid().subtract(bidSpread),
					bidOffer.getOffer().add(offerSpread));
		} else if (spreadType == MULTIPLICATION) {
			return new BidOffer(bidOffer.getBid().subtract(bidOffer.getBid().multiply(bidSpread)),
					bidOffer.getOffer().add(bidOffer.getOffer().multiply(offerSpread)));
		}
		throw new IllegalStateException(format("Failed to create newBidOffer as unknown spreadType=%s", spreadType));
	}

	public BidOffer newBidOffer(BigDecimal value) {
		if (spreadType == ADDITION) {
			return new BidOffer(value.subtract(bidSpread), value.add(offerSpread));
		} else if (spreadType == MULTIPLICATION) {
			return new BidOffer(value.subtract(value.multiply(bidSpread)), value.add(value.multiply(offerSpread)));
		}
		throw new IllegalStateException(format("Failed to create newBidOffer as unknown spreadType=%s", spreadType));
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Spread spread = (Spread) o;
		return Objects.equals(bidSpread, spread.bidSpread) &&
				Objects.equals(offerSpread, spread.offerSpread);
	}

	@Override
	public int hashCode() {
		return Objects.hash(bidSpread, offerSpread);
	}

	@Override
	public String toString() {
		return new StringBuilder(100).append("Spread{")
				.append("bidSpread=").append(bidSpread)
				.append(", offerSpread=").append(offerSpread)
				.append(", spreadType=").append(spreadType)
				.append('}').toString();
	}
}