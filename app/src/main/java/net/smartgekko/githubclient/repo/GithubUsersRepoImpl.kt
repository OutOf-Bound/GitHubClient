package net.smartgekko.githubclient.repo

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.BehaviorSubject
import net.smartgekko.githubclient.App
import net.smartgekko.githubclient.ui.UserBehavoir

class GithubUsersRepoImpl : GithubUsersRepo {
    private val repositories = arrayListOf<GithubUser>()
    private val behaviorSubject = BehaviorSubject.createDefault(repositories)
    private val loginProducer = LoginProducer()

    init {
        loginProducer.getLogin()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
                    repositories.add(
                        GithubUser(
                            it,
                            UserBehavoir(kotlin.random.Random.nextInt(0, 4))
                        )
                    )
                    behaviorSubject.onNext(repositories)
                },
                onComplete = {
                    behaviorSubject.onComplete()
                },
                onError = {
                    App.instance.showMessage("Getting new login error")
                }
            )
    }

    override fun getUsersList(): Observable<ArrayList<GithubUser>> {
        return behaviorSubject
    }
}

