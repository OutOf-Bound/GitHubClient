package net.smartgekko.githubclient.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import net.smartgekko.githubclient.App
import net.smartgekko.githubclient.SCREEN_STATE_IDLE
import net.smartgekko.githubclient.SCREEN_STATE_LOADING
import net.smartgekko.githubclient.databinding.FragmentUsersBinding
import net.smartgekko.githubclient.presenters.UsersPresenter
import net.smartgekko.githubclient.presenters.UsersRVAdapter
import net.smartgekko.githubclient.repo.GithubUsersRepoImpl

class UsersFragment : MvpAppCompatFragment(), UsersView, BackButtonListener {
    companion object {
        fun newInstance() = UsersFragment()
    }

    val presenter: UsersPresenter by moxyPresenter {
        UsersPresenter(
            GithubUsersRepoImpl(),
            App.instance.router,
            AndroidScreens()
        )
    }
    var adapter: UsersRVAdapter? = null
    private var _vb: FragmentUsersBinding? = null
    private val vb get() = _vb!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) =
        FragmentUsersBinding.inflate(inflater, container, false).also {
            _vb = it
        }.root

    override fun onDestroyView() {
        super.onDestroyView()
        _vb = null
    }

    override fun init() {
        vb.rvUsers.layoutManager = LinearLayoutManager(context)
        adapter = UsersRVAdapter(presenter.usersListPresenter)
        vb.rvUsers.adapter = adapter
    }

    override fun updateList() {
        adapter?.notifyDataSetChanged()
    }

    override fun backPressed() = presenter.backPressed()

    override fun setScreenState(state: Int) {
        when (state) {
            SCREEN_STATE_IDLE -> {
                vb.usersLoadingLayout.visibility = View.GONE
            }
            SCREEN_STATE_LOADING -> {
                vb.usersLoadingLayout.visibility = View.VISIBLE
            }
        }
    }
}