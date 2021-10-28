package net.smartgekko.githubclient.ui.main

import android.os.Bundle
import com.github.terrakok.cicerone.androidx.AppNavigator
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter
import net.smartgekko.githubclient.App
import net.smartgekko.githubclient.databinding.ActivityMainBinding
import net.smartgekko.githubclient.ui.AndroidScreens
import net.smartgekko.githubclient.ui.BackButtonListener

class MainActivity : MvpAppCompatActivity(), MainView {

    private val navigator = AppNavigator(this, net.smartgekko.githubclient.R.id.rootContainer)

    private val presenter by moxyPresenter { MainPresenter(App.instance.router, AndroidScreens()) }

    private lateinit var vb: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vb = ActivityMainBinding.inflate(layoutInflater)
        setContentView(vb.root)
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        App.instance.navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        App.instance.navigatorHolder.removeNavigator()
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

