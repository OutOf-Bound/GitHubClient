package net.smartgekko.githubclient.repo

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.BehaviorSubject
import net.smartgekko.githubclient.App

class GithubUsersRepoImpl : GithubUsersRepo {
    private val repositories = arrayListOf<GithubUser>()
    private val behaviorSubject = BehaviorSubject.createDefault<ArrayList<GithubUser>>(repositories)
    private val loginProducer = LoginProducer()

    init {
        loginProducer.getLogin()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
            onNext = {
                repositories.add(GithubUser(it))
                behaviorSubject.onNext(repositories)
            },
            onComplete = {
                behaviorSubject.onComplete()
                App.instance.showMessage("Users loading complete")
            },
            onError = {
                App.instance.showMessage("Getting new login error")
            },

        )
    }

   // override fun addUsser(user: GithubUser) {
   //     repositories.add(user)
   // }

    override fun getUsersList(): Observable<ArrayList<GithubUser>> {
        return behaviorSubject
    }
}

