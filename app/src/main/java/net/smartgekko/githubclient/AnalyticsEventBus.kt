package net.smartgekko.githubclient

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class AnalyticsEventBus {

    private val bus = PublishSubject.create<AnalyticsEvent>()

    fun post(event: AnalyticsEvent) {
        bus.onNext(event)
    }

    fun get(): Observable<AnalyticsEvent> = bus
}