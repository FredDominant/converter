package com.freddominant.converter

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.freddominant.converter.models.Currency
import com.freddominant.converter.models.CurrencyResponse
import com.freddominant.converter.network.CurrencyAPI
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.junit.BeforeClass
import org.mockito.Mockito
import org.mockito.Mockito.verify


@RunWith(JUnit4::class)
class ViewModelTest {

    @Rule @JvmField val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Rule @JvmField val taskExecutorRule = RxImmediateTestRule()

    @Mock lateinit var currencyApi: CurrencyAPI

    @Mock lateinit var viewModel: CurrencyViewModel

    @Mock lateinit var observer: androidx.lifecycle.Observer<ArrayList<Currency>>


    @Before
    fun setUp() {
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun testFetchCurrencies() {
        currencyApi.getCurrenciesFromAPI()
        verify(currencyApi).getCurrenciesFromAPI()
        val mockApiResponse = Mockito.mock(CurrencyResponse::class.java)
        `when`(currencyApi.getCurrenciesFromAPI()).thenReturn(Observable.just(mockApiResponse))
        `when`(viewModel.compositeDisposable).thenReturn(CompositeDisposable())
        viewModel.fetchCurrencies("EUR")
        Assert.assertNotNull(viewModel.compositeDisposable)
    }

    @Test
    fun testGetCurrencies() {
        `when`(viewModel.getCurrencies()).thenReturn(MutableLiveData())
        viewModel.getCurrencies().observeForever(observer)
        verify(viewModel).currencies
        Assert.assertTrue(viewModel.getCurrencies().hasObservers())
    }

    @Test
    fun disposeDisposableShouldWorkProperly() {
        `when`(viewModel.compositeDisposable).thenReturn(CompositeDisposable())
        Assert.assertTrue(!viewModel.compositeDisposable.isDisposed)
        viewModel.disposeDisposable()
        verify(viewModel).disposeDisposable()
    }

}