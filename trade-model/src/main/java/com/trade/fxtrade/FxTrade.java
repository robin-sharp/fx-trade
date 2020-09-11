package com.trade.fxtrade;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.trade.BuySell;
import com.trade.Trade;
import com.trade.TradeStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import static com.trade.Version.FX_TRADE_V1;

/**
 * Class Represents a Fx Spot or Forward
 * <p>
 * All Dates in UTC
 */

@Data
@NoArgsConstructor
@Table("fxtrade")
public class FxTrade implements Trade, Serializable {

	private static final long serialVersionUID = FX_TRADE_V1;

	@NotNull(message = "tradeId is mandatory")
	@PrimaryKey
	private UUID tradeId;

	@NotBlank(message = "trader is mandatory")
	@Column
	private String trader;

	@NotBlank(message = "entity is mandatory")
	@Column
	private String party;

	@NotBlank(message = "counterparty is mandatory")
	@Column
	private String counterparty;

	@NotNull(message = "tradeStatus is mandatory")
	@Column
	private TradeStatus tradeStatus;

	@NotNull(message = "executionTime is mandatory")
	@Column
	private LocalDateTime executionTime;

	@NotNull(message = "buySell is mandatory")
	@Column
	private BuySell buySell;

	@NotNull(message = "amount is mandatory")
	@Positive(message = "amount must be positive")
	private BigDecimal amount;

	@NotNull(message = "forwardDate is mandatory")
	@Column
	private LocalDate forwardDate;

	@NotBlank(message = "currencyPair is mandatory")
	@Size(min = 6, max = 6)
	@Column
	private String currencyPair;

	@NotNull(message = "rates is mandatory")
	@Positive(message = "rates must be positive")
	@Column
	private BigDecimal rate;

	public FxTrade(UUID tradeId, String trader, String party, String counterparty, TradeStatus tradeStatus, LocalDateTime executionTime,
				   BuySell buySell, BigDecimal amount, String currencyPair, LocalDate forwardDate, BigDecimal rate) {
		this.tradeId = tradeId;
		this.trader = trader;
		this.party = party;
		this.counterparty = counterparty;
		this.tradeStatus = tradeStatus;
		this.executionTime = executionTime;
		this.buySell = buySell;
		this.amount = amount;
		this.currencyPair = currencyPair;
		this.forwardDate = forwardDate;
		this.rate = rate;
	}

	public UUID getTradeId() {
		return tradeId;
	}

	@JsonIgnore
	public String getTradeType() {
		return "Fx";
	}

	public String getTrader() {
		return trader;
	}

	public String getParty() {
		return party;
	}

	public String getCounterparty() {
		return counterparty;
	}

	public TradeStatus getTradeStatus() {
		return tradeStatus;
	}

	public LocalDateTime getExecutionTime() {
		return executionTime;
	}

	public BuySell getBuySell() {
		return buySell;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	@JsonIgnore
	public String getCurrency() {
		return currencyPair != null ? currencyPair.substring(0, 3) : null;
	}

	public String getCurrencyPair() {
		return currencyPair;
	}

	public LocalDate getForwardDate() {
		return forwardDate;
	}

	public BigDecimal getRate() {
		return rate;
	}

	@JsonIgnore
	public String getDescription() {
		return new StringBuilder().
				append(getTradeType()).append(" ").
				append(getAmount()).append(" ").
				append(getCurrencyPair()).append(" ").
				append(getForwardDate()).append(" @").
				append(getRate()).toString();
	}

	@Override
	public String toString() {
		return new StringBuilder("class=").
				append(FxTrade.class.getSimpleName()).
				append(", tradeId=").append(tradeId).
				append(", trader=").append(trader).
				append(", party=").append(party).
				append(", counterparty=").append(counterparty).
				append(", tradeStatus=").append(tradeStatus).
				append(", executionTime=").append(executionTime).
				append(", buySell=").append(buySell).
				append(", amount=").append(amount).
				append(", currencyPair=").append(currencyPair).
				append(", forwardDate=").append(forwardDate).
				append(", rates=").append(rate).
				toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		FxTrade fxTrade = (FxTrade) o;
		return Objects.equals(tradeId, fxTrade.tradeId);
	}

	@Override
	public int hashCode() {
		return tradeId.hashCode();
	}
}
