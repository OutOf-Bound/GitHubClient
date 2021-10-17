package net.smartgekko.githubclient.presenters

import com.github.terrakok.cicerone.Router
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import moxy.MvpPresenter
import net.smartgekko.githubclient.*
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
            view.setUserState(user.behavoir.userState)

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

        App.actionBus.get()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { event ->
                if (event is ActionEvent.DoVactinate) {
                    viewState.updateList()
                } else {

                }
            }

        App.analyticsBus.get()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { event ->
                if (event is AnalyticsEvent) {
                    showAnalytics()
                } else {

                }
            }

    }

    private fun showAnalytics() {
        val tmpArray: IntArray = intArrayOf(0, 0, 0, 0)
        for (i in 0 until usersListPresenter.users.size) {
            tmpArray[usersListPresenter.users[i].behavoir.userState]++
        }
        viewState.updateAnalytics(tmpArray)
    }

    private fun loadData() {
        viewState.setScreenState(SCREEN_STATE_LOADING)
        compositeDisposable.add(
            usersRepo.getUsersList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onNext = {
                        userListUpdate(it)
                    },
                    onComplete = {
                        viewState.setScreenState(SCREEN_STATE_IDLE)
                        showAnalytics()
                    },
                    onError = {
                        App.instance.showMessage("Getting users list error")
                    },

                    )
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
