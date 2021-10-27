package net.smartgekko.githubclient.presenters

import com.github.terrakok.cicerone.Router
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import moxy.MvpPresenter
import net.smartgekko.githubclient.*
import net.smartgekko.githubclient.repo.GithubUser
import net.smartgekko.githubclient.repo.IGithubUsersRepo
import net.smartgekko.githubclient.ui.IScreens
import net.smartgekko.githubclient.ui.UserItemView
import net.smartgekko.githubclient.ui.UsersView

class UsersPresenter(
    val uiScheduler: Scheduler,
    private val usersRepo: IGithubUsersRepo,
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
        viewState.setScreenState(SCREEN_STATE_LOADING)

        compositeDisposable.add(
            usersRepo.getUsers()
                .observeOn(uiScheduler)
                .subscribeBy(
                    onNext = {
                        usersListPresenter.users.clear()
                        usersListPresenter.users.addAll(it)
                        viewState.updateList()
                    },
                    onComplete = {
                        viewState.setScreenState(SCREEN_STATE_IDLE)
                    },
                    onError = {
                        println("Error: ${it.message}")
                    })

        )
    }


    private fun userListUpdate(usersList: ArrayList<GithubUser>) {
        usersListPresenter.users.clear()
        usersListPresenter.users.addAll(usersList)
        viewState.updateList()
    }


    fun backPressed(): Boolean {
        router.exit()
        return true
    }
}
