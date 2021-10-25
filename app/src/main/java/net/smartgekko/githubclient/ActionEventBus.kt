package net.smartgekko.githubclient

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class ActionEventBus {

    open class Event

    private val bus = PublishSubject.create<Event>()

    fun post(event:Event) {
        bus.onNext(event)
    }

    fun get(): Observable<Event> = bus
}