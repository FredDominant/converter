package com.freddominant.converter

import com.freddominant.converter.models.Currency
import com.freddominant.converter.models.CurrencyRate
import com.freddominant.converter.models.CurrencyResponse
import org.junit.Assert
import org.junit.Test

class ModelsTest {

    @Test
    fun modelsShouldWorkProperly() {
        val dollar = Currency("USD", 300.0)
        Assert.assertNotNull(dollar.currencyCode)
        Assert.assertNotNull(dollar.value)
        Assert.assertEquals(1.0, dollar.userAmount, 0.0)
        Assert.assertEquals(300.0, dollar.value, 0.0)
        Assert.assertEquals("USD", dollar.currencyCode)
    }

    @Test
    fun currencyNameShouldBeCorrect() {
        val euro = Currency("EUR", 300.0)
        Assert.assertNotNull(euro.getCurrencyName())
        Assert.assertNotEquals(-1, euro.getFlag())
    }

    @Test
    fun currencyResponseShouldWorkAppropriately() {
        val currencyRates = CurrencyRate().apply {
            this.australianDollar = 400.0
            this.canadianDollar = 250.0
            this.euro = 350.0
        }

        val currencyResponse = CurrencyResponse("EUR", "10-09-2020", currencyRates)

        Assert.assertNotNull(currencyResponse)
        Assert.assertNotNull(currencyRates)
        Assert.assertNotNull(currencyResponse.rates)
        Assert.assertNotNull(currencyResponse.base)
        Assert.assertNotNull(currencyResponse.date)
        Assert.assertEquals("EUR", currencyResponse.base)
        Assert.assertEquals("10-09-2020", currencyResponse.date)
        Assert.assertTrue(currencyResponse.getCurrencyRate().isNotEmpty())
    }
}