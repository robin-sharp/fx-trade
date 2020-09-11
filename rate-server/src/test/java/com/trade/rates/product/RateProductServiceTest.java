package com.trade.rates.product;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.trade.JUnitTag.UNIT_TEST;
import static com.trade.rates.RatesTestData.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag(UNIT_TEST)
class RateProductServiceTest {

	@Test
	public void testGetRateProduct() {
		RateProductService rateProductService = new RateProductService();

		RateProduct internalRateProduct = rateProductService.getRateProduct(INTERNAL_PRODUCT_NAME);
		assertEquals(INTERNAL_PRODUCT, internalRateProduct);

		RateProduct interbankRateProduct = rateProductService.getRateProduct(INTERBANK_PRODUCT_NAME);
		assertEquals(INTERBANK_PRODUCT, interbankRateProduct);
	}
}