package com.freddominant.converter

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.freddominant.converter.models.CurrencyResponse
import com.freddominant.converter.network.CurrencyAPI
import com.freddominant.converter.network.NetworkService
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class CurrencyAPITest {

    @Rule
    @JvmField val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Rule
    @JvmField val taskExecutorRule = RxImmediateTestRule()


    private lateinit var networkService: NetworkService

    @Mock lateinit var currencyAPI: CurrencyAPI

    @Mock lateinit var currencyResponse: CurrencyResponse

    @Before
    fun setUp() {
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        MockitoAnnotations.initMocks(this)
        this.networkService = Mockito.mock(NetworkService::class.java)
    }

    @Test
    fun currencyApiShouldWorkAsExpected() {
        val api = CurrencyAPI()
        Assert.assertNotNull(api.client)
    }

    @Test
    fun getCurrenciesFromApiShouldWorkAsExpected() {
        val mockResponse = Observable.just(currencyResponse)
        `when`(this.currencyAPI.getCurrenciesFromAPI()).thenReturn(mockResponse)
        Assert.assertNotNull(this.currencyAPI.getCurrenciesFromAPI())
        verify(this.currencyAPI).getCurrenciesFromAPI()
        Assert.assertEquals(this.currencyAPI.getCurrenciesFromAPI(), mockResponse)
    }
}

