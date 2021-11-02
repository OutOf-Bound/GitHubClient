package net.smartgekko.githubclient.ui.users

import android.os.Bundle
import android.transition.Fade
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.android.schedulers.AndroidSchedulers
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import net.smartgekko.githubclient.App
import net.smartgekko.githubclient.SCREEN_STATE_IDLE
import net.smartgekko.githubclient.SCREEN_STATE_LOADING
import net.smartgekko.githubclient.databinding.FragmentUsersBinding
import net.smartgekko.githubclient.ui.AndroidScreens
import net.smartgekko.githubclient.ui.BackButtonListener

class UsersFragment : MvpAppCompatFragment(), UsersView, BackButtonListener {
    companion object {
        fun newInstance() = UsersFragment()
    }

    private val presenter: UsersPresenter by moxyPresenter {
        UsersPresenter(
            AndroidSchedulers.mainThread(),
            App.instance.router,
            AndroidScreens()
        )
    }

    private var adapter: UsersRVAdapter? = null
    private var _vb: FragmentUsersBinding? = null
    private val vb get() = _vb!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _vb = FragmentUsersBinding.inflate(inflater, container, false)

        return vb.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _vb = null
    }

    override fun init() {
        vb.usersRv.layoutManager = LinearLayoutManager(context)
        adapter = UsersRVAdapter(presenter.usersListPresenter)
        vb.usersRv.adapter = adapter
    }

    override fun updateList() {
        adapter?.notifyDataSetChanged()
    }

    override fun backPressed() = presenter.backPressed()

    override fun setScreenState(state: Int) {
        val fade = Fade()
        fade.duration = 200

        when (state) {
            SCREEN_STATE_IDLE -> {
                TransitionManager.beginDelayedTransition(vb.loadingLayout, fade)
                vb.loadingLayout.visibility = View.GONE
            }
            SCREEN_STATE_LOADING -> {
                vb.loadingLayout.visibility = View.VISIBLE
            }
        }
    }
}