package com.freddominant.converter

import io.reactivex.Scheduler
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class RxImmediateTestRule: TestRule {
    private val immediateScheduler = object : Scheduler() {
        override fun createWorker() = ExecutorScheduler.ExecutorWorker { it.run() }
    }

    override fun apply(base: Statement?, description: Description?): Statement {
        return object: Statement() {
            override fun evaluate() {
                RxJavaPlugins.setInitIoSchedulerHandler { immediateScheduler }
                RxJavaPlugins.setInitComputationSchedulerHandler { immediateScheduler }
                RxJavaPlugins.setInitNewThreadSchedulerHandler { immediateScheduler }
                RxJavaPlugins.setInitSingleSchedulerHandler { immediateScheduler }
                RxAndroidPlugins.setMainThreadSchedulerHandler { immediateScheduler }

                try {
                    base?.evaluate()
                } finally {
                    RxJavaPlugins.reset()
                    RxAndroidPlugins.reset()
                }
            }

        }
    }
}