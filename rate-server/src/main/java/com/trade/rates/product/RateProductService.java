package com.trade.rates.product;

import com.trade.Spread;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Faux Service to produce a number of profiles for Rate Products.
 * <p>
 * TODO: get RateProduct from Db for User->Party->Rates.
 */
@Service
public class RateProductService {

	private static ConcurrentHashMap<String, RateProduct> rateProducts = new ConcurrentHashMap<>();

	//TODO
	static {
		newRateProduct("Internal", 2, ChronoUnit.SECONDS, "1000000", "0.000005", "2000000", "0.000002", "3000000", "0.000001");
		newRateProduct("Interbank", 1, ChronoUnit.SECONDS, "1000000", "0.000007", "2000000", "0.000006", "3000000", "0.000005");
		newRateProduct("Corporate", 1, ChronoUnit.MINUTES, "10000", "0.004", "200000", "0.003", "3000000", "0.002", "40000000", "0.001");
		newRateProduct("Company", 1, ChronoUnit.MINUTES, "1000", "0.005", "10000", "0.004", "100000", "0.003", "1000000", "0.002");
		newRateProduct("Retail", 1, ChronoUnit.HOURS, "100", "0.05", "1000", "0.04");
	}

	static void newRateProduct(String productName, int throttleDuration, TemporalUnit throttleTimeUnit, String... spreads) {
		SortedMap<BigDecimal, Spread> spreadStructure = new TreeMap<>();
		for (int index = 0; index < spreads.length; index += 2) {
			spreadStructure.put(new BigDecimal(spreads[index]), new Spread(new BigDecimal(spreads[index + 1])));
		}
		RateProduct rateProduct = new RateProduct(productName, throttleDuration, throttleTimeUnit, spreadStructure);
		rateProducts.put(productName, rateProduct);
	}

	public RateProduct getRateProduct(String source) {
		return rateProducts.get(source);
	}
}
