package net.smartgekko.githubclient.ui.main


import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter
import net.smartgekko.githubclient.App
import net.smartgekko.githubclient.ui.AndroidScreens
import javax.inject.Inject

class MainPresenter() :
    MvpPresenter<MainView>() {
    @Inject
    lateinit var router: Router

    @Inject
    lateinit var screens: AndroidScreens

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        App.instance.appComponent.inject(this)

        router.replaceScreen(screens.users())
    }

    fun backClicked() {
        router.exit()
    }


}

