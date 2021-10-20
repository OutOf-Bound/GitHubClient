package net.smartgekko.githubclient.repo

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.parcelize.Parcelize
import net.smartgekko.githubclient.ActionEvent
import net.smartgekko.githubclient.AnalyticsEvent
import net.smartgekko.githubclient.App
import net.smartgekko.githubclient.ui.UserBehavoir
import java.io.Serializable


class GithubUser(
    val login: String,
    var behavoir: UserBehavoir
) : ControlableUser,Serializable {
    init {
        subscribeOnActionBus()
    }

    override fun subscribeOnActionBus() {
        val disposable = CompositeDisposable()
        disposable.add(
            App.actionBus.get()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { event ->
                    if (event is ActionEvent.DoVactinate) {
                        val rndState = kotlin.random.Random.nextInt(0, 4)
                        behavoir.userState = rndState
                        sendAnalytics(rndState)
                    } else {

                    }
                })
    }

    override fun sendAnalytics(userState: Int) {
        App.analyticsBus.post(AnalyticsEvent(behavoir.userState))
    }

}


