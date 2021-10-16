package net.smartgekko.githubclient.repo

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import net.smartgekko.githubclient.ui.UserBehavoir


data class GithubUser(
    val login: String,
    var behavoir: UserBehavoir
)


