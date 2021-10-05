package net.smartgekko.githubclient.ui

import com.github.terrakok.cicerone.androidx.FragmentScreen
import net.smartgekko.githubclient.ui.IScreens
import net.smartgekko.githubclient.ui.UsersFragment

class AndroidScreens : IScreens {
    override fun users() = FragmentScreen { UsersFragment.newInstance() }
}