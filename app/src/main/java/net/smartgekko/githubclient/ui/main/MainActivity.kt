package net.smartgekko.githubclient.ui.main

import android.os.Bundle
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.androidx.AppNavigator
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter
import net.smartgekko.githubclient.R
import net.smartgekko.githubclient.app
import net.smartgekko.githubclient.databinding.ActivityMainBinding
import net.smartgekko.githubclient.ui.BackButtonListener
import javax.inject.Inject

class MainActivity : MvpAppCompatActivity(), MainView {

    private lateinit var navigator: AppNavigator

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    private val presenter by moxyPresenter { MainPresenter() }

    private lateinit var vb: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        vb = ActivityMainBinding.inflate(layoutInflater)
        setContentView(vb.root)
        app.appComponent.inject(this)


        navigator = AppNavigator(this, R.id.rootContainer)
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }

    override fun onBackPressed() {
        supportFragmentManager.fragments.forEach {
            if (it is BackButtonListener && it.backPressed()) {
                return
            }
        }
        presenter.backClicked()
    }
}

