package com.trade;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Data
@NoArgsConstructor
public class BidOffer implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull(message = "bid is mandatory")
	@Positive(message = "bid must be positive")
	private BigDecimal bid;

	@NotNull(message = "offer is mandatory")
	@Positive(message = "offer must be positive")
	private BigDecimal offer;

	public BidOffer(BigDecimal bid, BigDecimal offer) {
		this.bid = bid;
		this.offer = offer;
	}

	public BigDecimal getBid() {
		return bid;
	}

	public BigDecimal getOffer() {
		return offer;
	}

	@JsonIgnore
	public BigDecimal getSpread() {
		return offer.subtract(bid);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		BidOffer bidOffer = (BidOffer) o;
		return Objects.equals(bid, bidOffer.bid) &&
				Objects.equals(offer, bidOffer.offer);
	}

	@Override
	public int hashCode() {
		return Objects.hash(bid, offer);
	}

	@Override
	public String toString() {
		return new StringBuilder(50)
				.append("BidOffer{")
				.append("bid=").append(bid)
				.append(", offer=").append(offer)
				.append('}').toString();
	}
}
