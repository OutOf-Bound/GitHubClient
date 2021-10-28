package net.smartgekko.githubclient.classes

import com.google.gson.annotations.Expose
import java.io.Serializable


class GithubUser(
    @Expose val id: String? = null,
    @Expose val login: String,
    @Expose val avatarUrl: String? = null,
    @Expose val reposUrl: String? = null
) : Serializable


