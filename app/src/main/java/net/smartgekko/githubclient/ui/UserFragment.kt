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

class UserFragment(val user: GithubUser) : MvpAppCompatFragment(), BackButtonListener, UserView {

    companion object {
        fun newInstance(user: GithubUser) = UserFragment(user)
    }


    val presenter: UserPresenter by moxyPresenter {
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
        vb?.userNameTV?.text = user.login
    }

    override fun backPressed() = presenter.backPressed()
}