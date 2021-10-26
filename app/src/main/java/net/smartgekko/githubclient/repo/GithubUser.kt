package net.smartgekko.githubclient.repo

import com.google.gson.annotations.Expose
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.parcelize.Parcelize
import net.smartgekko.githubclient.ActionEvent
import net.smartgekko.githubclient.AnalyticsEvent
import net.smartgekko.githubclient.App
import net.smartgekko.githubclient.ui.UserBehavoir
import java.io.Serializable


class GithubUser(
    @Expose val id: String? = null,
    @Expose  val login: String,
    @Expose val avatarUrl: String? = null
) : Serializable


