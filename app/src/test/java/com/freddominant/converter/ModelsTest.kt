package com.freddominant.converter

import com.freddominant.converter.models.Currency
import org.junit.Assert
import org.junit.Test

class ModelsTest {

    @Test
    fun modelsShouldWorkProperly() {
        val dollar = Currency("USD", 300.0)
        Assert.assertEquals(300.0, dollar.value, 0.0)
        Assert.assertEquals("USD", dollar.currencyCode)
    }
}