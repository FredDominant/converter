package com.freddominant.converter

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.freddominant.converter.models.Currency
import io.reactivex.Observer
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
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

@RunWith(JUnit4::class)
class ViewModelTest {

    @get:Rule val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock lateinit var observer: Observer<ArrayList<Currency>>

    lateinit var viewModel: CurrencyViewModel

    companion object {
        @ClassRule @JvmField val scheduler = RxImmediateTestRule()
    }

    @Before
    fun setUp() {
        RxAndroidPlugins.setMainThreadSchedulerHandler { Schedulers.trampoline() }
        MockitoAnnotations.initMocks(this)
        this.viewModel = CurrencyViewModel()
    }

    @Test
    fun testFetchCurrencies() {
        `when`(viewModel.startSubscription()).thenReturn(null)
        Assert.assertTrue(viewModel.getCurrencies().hasObservers())
    }

    @Test
    fun pass() {
        Assert.assertTrue(true)
    }
}