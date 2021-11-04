package net.smartgekko.githubclient.ui.main

import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter
import net.smartgekko.githubclient.ui.AndroidScreens
import org.koin.java.KoinJavaComponent

class MainPresenter() :
    MvpPresenter<MainView>() {
    private val router:Router by KoinJavaComponent.inject(Router::class.java)
    private val screens: AndroidScreens by KoinJavaComponent.inject(AndroidScreens::class.java)

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        router.replaceScreen(screens.users())
    }

    fun backClicked() {
        router.exit()
    }
}

