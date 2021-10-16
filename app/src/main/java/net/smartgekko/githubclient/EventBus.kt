package net.smartgekko.githubclient

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import net.smartgekko.githubclient.repo.GithubUser

class EventBus {
    open class Event

    private val bus = PublishSubject.create<Event>()
    private var user: GithubUser? = null

    fun post(event: Event) {
        bus.onNext(event)
    }

    fun get(): Observable<Event> = bus
}