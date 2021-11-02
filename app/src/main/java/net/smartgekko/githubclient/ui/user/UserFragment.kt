package net.smartgekko.githubclient.ui.user

import android.os.Bundle
import android.transition.Fade
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import net.smartgekko.githubclient.repo.api.ApiHolder
import net.smartgekko.githubclient.App
import net.smartgekko.githubclient.SCREEN_STATE_IDLE
import net.smartgekko.githubclient.SCREEN_STATE_LOADING
import net.smartgekko.githubclient.databinding.FragmentUserBinding
import net.smartgekko.githubclient.classes.GithubUser
import net.smartgekko.githubclient.repo.api.RetrofitGithubUsersRepo
import net.smartgekko.githubclient.ui.AndroidScreens
import net.smartgekko.githubclient.ui.BackButtonListener

class UserFragment : MvpAppCompatFragment(), BackButtonListener,
    UserView {
    private lateinit var user: GithubUser

    companion object {
        fun newInstance(gitUser: GithubUser): UserFragment {
            val args = Bundle()
            args.putSerializable("gitUser", gitUser)
            val f = UserFragment()
            f.setArguments(args)
            return f
        }
    }

    private val presenter: UserPresenter by moxyPresenter {
        UserPresenter(
            RetrofitGithubUsersRepo(ApiHolder.apiRetrofit, App.networkStatus, App.db),
            App.instance.router,
            AndroidScreens()
        )
    }

    private var adapter: RepoRVAdapter? = null
    private var _vb: FragmentUserBinding? = null
    private val vb get() = _vb!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) =
        FragmentUserBinding.inflate(inflater, container, false).also {
            _vb = it
        }.root

    override fun onDestroyView() {
        super.onDestroyView()
        _vb = null
    }

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

    override fun init() {
        user = arguments?.getSerializable("gitUser") as GithubUser
        vb.userNameTv.text = user.login
        vb.userReposRv.layoutManager = LinearLayoutManager(context)
        adapter = RepoRVAdapter(presenter.repoListPresenter)
        vb.userReposRv.adapter = adapter
        presenter.setCurrentUser(user)
    }

    override fun updateUserReposList() {
        adapter?.notifyDataSetChanged()
    }

    override fun backPressed() = presenter.backPressed()
}