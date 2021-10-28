package net.smartgekko.githubclient.ui.main

import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter
import net.smartgekko.githubclient.ui.IScreens
import net.smartgekko.githubclient.ui.main.MainView

class MainPresenter(private val router: Router, private val screens: IScreens) :
    MvpPresenter<MainView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        router.replaceScreen(screens.users())
    }

    fun backClicked() {
        router.exit()
    }
}

