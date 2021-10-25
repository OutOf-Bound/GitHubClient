package net.smartgekko.githubclient.ui

import com.github.terrakok.cicerone.Screen
import net.smartgekko.githubclient.repo.GithubUser

interface IScreens {
    fun users(): Screen
    fun user(user: GithubUser): Screen
}