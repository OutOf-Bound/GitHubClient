package net.smartgekko.githubclient.presenters

import android.widget.Toast
import com.github.terrakok.cicerone.Router
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import moxy.MvpPresenter
import net.smartgekko.githubclient.App
import net.smartgekko.githubclient.repo.GithubUser
import net.smartgekko.githubclient.repo.GithubUsersRepoImpl
import net.smartgekko.githubclient.ui.IScreens
import net.smartgekko.githubclient.ui.UserItemView
import net.smartgekko.githubclient.ui.UsersView

class UsersPresenter(
    private val usersRepo: GithubUsersRepoImpl,
    private val router: Router,
    private val screens: IScreens
) : MvpPresenter<UsersView>() {

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
    private var compositeDisposable: CompositeDisposable = CompositeDisposable()


    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        loadData()
        usersListPresenter.itemClickListener = { itemView ->
            router.navigateTo(screens.user(usersListPresenter.users[itemView.pos]))
        }
    }

    private fun loadData() {
        compositeDisposable.add(usersRepo.users
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { listUsers -> userListUpdate(listUsers)},
                { thr -> Toast.makeText(App.instance, thr.message, Toast.LENGTH_SHORT).show() }
            )
        )
    }

    private fun userListUpdate(usersList:ArrayList<GithubUser>) {
        usersListPresenter.users.clear()
        usersListPresenter.users.addAll(usersList)
        viewState.updateList()
    }

    fun backPressed(): Boolean {
        router.exit()
        return true
    }
}
