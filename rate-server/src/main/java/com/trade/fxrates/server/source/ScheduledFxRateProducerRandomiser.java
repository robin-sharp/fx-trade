package com.trade.fxrates.server.source;

import com.trade.Spread;
import com.trade.rates.FxRate;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Random;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Class designed to add some random drift to source rates, to simulate a moving market.
 */
@NoArgsConstructor
public class ScheduledFxRateProducerRandomiser extends ScheduledFxRateProducer {

	private final Map<String, BigDecimal> randomDeltaRates = new ConcurrentHashMap<>();

	private final Random random = new Random();

	public ScheduledFxRateProducerRandomiser(FxRateSource fxRateSource) {
		super(fxRateSource);
	}

	protected FxRate newFxRate(String currencyPair, FxRate fxRate) {
		final BigDecimal deltaRate = randomDeltaRates.computeIfAbsent(currencyPair, k -> BigDecimal.ZERO);
		final BigDecimal randomDelta = new BigDecimal("0.00000" + random.nextInt(5));
		final BigDecimal newDeltaRate = random.nextBoolean() ? deltaRate.add(randomDelta) : deltaRate.subtract(randomDelta);
		randomDeltaRates.put(currencyPair, newDeltaRate);
		SortedMap<BigDecimal, Spread> spreadStructure = new TreeMap<>();
		spreadStructure.put(BigDecimal.ONE, new Spread(newDeltaRate, newDeltaRate));
		return fxRate.spread(spreadStructure);
	}
}
