package net.smartgekko.githubclient.ui.main

import android.os.Bundle
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.androidx.AppNavigator
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter
import net.smartgekko.githubclient.R
import net.smartgekko.githubclient.databinding.ActivityMainBinding
import net.smartgekko.githubclient.ui.BackButtonListener
import org.koin.android.ext.android.inject

class MainActivity : MvpAppCompatActivity(), MainView {

    private val navigatorHolder:NavigatorHolder by inject()
    private lateinit var navigator:AppNavigator

    private val presenter by moxyPresenter { MainPresenter() }


    private lateinit var vb: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vb = ActivityMainBinding.inflate(layoutInflater)
        setContentView(vb.root)
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

