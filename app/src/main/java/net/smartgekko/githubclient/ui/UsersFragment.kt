package net.smartgekko.githubclient.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.android.schedulers.AndroidSchedulers
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import net.smartgekko.githubclient.*
import net.smartgekko.githubclient.databinding.FragmentUsersBinding
import net.smartgekko.githubclient.presenters.UsersPresenter
import net.smartgekko.githubclient.presenters.UsersRVAdapter
import net.smartgekko.githubclient.repo.RetrofitGithubUsersRepo

class UsersFragment : MvpAppCompatFragment(), UsersView, BackButtonListener {
    companion object {
        fun newInstance() = UsersFragment()
    }

    private val presenter: UsersPresenter by moxyPresenter {
        UsersPresenter(
            AndroidSchedulers.mainThread(),
            RetrofitGithubUsersRepo(ApiHolder.api),
            App.instance.router, AndroidScreens()
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
                vb.loadingLayout.visibility = View.GONE
            }
            SCREEN_STATE_LOADING -> {
                vb.loadingLayout.visibility = View.VISIBLE
            }
        }
    }
}