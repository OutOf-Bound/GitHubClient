package net.smartgekko.githubclient.ui

import com.github.terrakok.cicerone.androidx.FragmentScreen
import net.smartgekko.githubclient.classes.GithubUser
import net.smartgekko.githubclient.ui.user.UserFragment
import net.smartgekko.githubclient.ui.users.UsersFragment

class AndroidScreens : IScreens {
    override fun users() = FragmentScreen { UsersFragment.newInstance() }
    override fun user(user: GithubUser) = FragmentScreen { UserFragment.newInstance(user) }
}