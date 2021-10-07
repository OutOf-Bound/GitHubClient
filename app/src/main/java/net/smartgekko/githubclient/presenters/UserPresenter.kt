package net.smartgekko.githubclient.presenters

import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter
import net.smartgekko.githubclient.ui.IScreens
import net.smartgekko.githubclient.ui.UserView

class UserPresenter(val router: Router, val screens: IScreens) : MvpPresenter<UserView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
    }

    fun backPressed(): Boolean {
        router.backTo(screens.users())
        return true
    }
}