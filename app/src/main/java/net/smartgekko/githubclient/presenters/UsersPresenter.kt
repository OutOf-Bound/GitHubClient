package net.smartgekko.githubclient.presenters

import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter
import net.smartgekko.githubclient.repo.GithubUser
import net.smartgekko.githubclient.repo.GithubUsersRepo
import net.smartgekko.githubclient.ui.IScreens
import net.smartgekko.githubclient.ui.UserItemView
import net.smartgekko.githubclient.ui.UsersView

class UsersPresenter(val usersRepo: GithubUsersRepo, val router: Router, val screens: IScreens) : MvpPresenter<UsersView>() {

    class UsersListPresenter : IUserListPresenter {
        val users = mutableListOf<GithubUser>()

        override var itemClickListener: ((UserItemView) -> Unit)? = null

        override fun getCount() = users.size

        override fun bindView(view: UserItemView) {
            val user = users[view.pos]
            view.setLogin(user.login)
        }
    }

    val usersListPresenter = UsersListPresenter()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        loadData()
        usersListPresenter.itemClickListener = { itemView ->
            router.navigateTo(screens.user(usersListPresenter.users[itemView.pos]))
        }
    }

    fun loadData() {
        val users = usersRepo.getUsers()
        usersListPresenter.users.addAll(users)
        viewState.updateList()
    }

    fun backPressed(): Boolean {
        router.exit()
        return true
    }

}
