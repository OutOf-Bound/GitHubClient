package net.smartgekko.githubclient.repo

import com.google.gson.annotations.Expose
import java.io.Serializable


class GitHubUserRepository(
    @Expose val id: String? = null,
    @Expose val name: String,
    @Expose val description: String? = null,
    @Expose var selected: Boolean = true
) : Serializable
