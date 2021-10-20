package net.smartgekko.githubclient.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import net.smartgekko.githubclient.App
import net.smartgekko.githubclient.databinding.FragmentUserBinding
import net.smartgekko.githubclient.presenters.UserPresenter
import net.smartgekko.githubclient.repo.GithubUser

class UserFragment : MvpAppCompatFragment(), BackButtonListener,
    UserView {

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
            App.instance.router,
            AndroidScreens()
        )
    }
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

    override fun init() {
        val user: GithubUser = arguments?.getSerializable("gitUser") as GithubUser
        vb.userNameTV.text = user.login
    }

    override fun backPressed() = presenter.backPressed()
}