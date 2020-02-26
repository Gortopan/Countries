package app.test.countries.schedulers

import io.reactivex.Scheduler

interface SchedulersProvider {

    val backgroundScheduler: Scheduler

    val mainScheduler: Scheduler

    val singleScheduler: Scheduler

}