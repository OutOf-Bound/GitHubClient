package net.smartgekko.githubclient.repo

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import net.smartgekko.githubclient.ui.UserBehavoir
import io.reactivex.android.schedulers.AndroidSchedulers

import io.reactivex.internal.util.NotificationLite.disposable
import io.reactivex.disposables.CompositeDisposable
import net.smartgekko.githubclient.ActionEvent
import net.smartgekko.githubclient.AnalyticsEvent
import net.smartgekko.githubclient.App


class GithubUser(
    val login: String,
    var behavoir: UserBehavoir
): ControlableUser
{
  init{
      subscribeOnActionBus()
      subscribeOnAnalyticsBus()
  }

    override fun subscribeOnActionBus() {
        val disposable = CompositeDisposable()
        disposable.add(
            App.actionBus.get()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { event ->
                    if (event is ActionEvent.DoVactinate) {
                       behavoir.userState = kotlin.random.Random.nextInt(0, 4)
                    } else {

                    }
                })
    }

    override fun subscribeOnAnalyticsBus() {
      //  TODO("Not yet implemented")
    }

}


