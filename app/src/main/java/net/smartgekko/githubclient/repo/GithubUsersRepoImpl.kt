package net.smartgekko.githubclient.repo


import android.widget.Toast
import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.BehaviorSubject
import net.smartgekko.githubclient.App

class GithubUsersRepoImpl : GithubUsersRepo {
    private val repositories = arrayListOf<GithubUser>(
        GithubUser("login1"),
        GithubUser("login2"),
    )
    private val behaviorSubject = BehaviorSubject.createDefault<ArrayList<GithubUser>>(repositories)
    private val loginProducer = LoginProducer()

    init {
        loginProducer.getLogin().subscribeBy(
            onNext = {
                repositories.add(GithubUser(it))
                behaviorSubject.onNext(repositories)
            },
            onError = {
                Toast.makeText(App.instance, "Getting new login error", Toast.LENGTH_LONG).show()
            }
        )
    }

    override fun add(user: GithubUser) {
        repositories.add(user)
    }

    override val users: Observable<ArrayList<GithubUser>>
        get() = behaviorSubject
}

