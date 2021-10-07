package net.smartgekko.githubclient.ui

import com.github.terrakok.cicerone.androidx.FragmentScreen
import net.smartgekko.githubclient.repo.GithubUser

class AndroidScreens : IScreens {
    override fun users() = FragmentScreen { UsersFragment.newInstance() }
    override fun user(user: GithubUser) = FragmentScreen { UserFragment.newInstance(user) }
}