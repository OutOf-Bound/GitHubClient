package net.smartgekko.githubclient.ui.users

import com.github.terrakok.cicerone.Router
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import moxy.MvpPresenter
import net.smartgekko.githubclient.SCREEN_STATE_IDLE
import net.smartgekko.githubclient.SCREEN_STATE_LOADING
import net.smartgekko.githubclient.presenters.IUserListPresenter
import net.smartgekko.githubclient.classes.GithubUser
import net.smartgekko.githubclient.ui.AndroidScreens
import net.smartgekko.githubclient.ui.GithubPresenter
import net.smartgekko.githubclient.ui.user.UserItemView
import org.koin.java.KoinJavaComponent
import org.koin.java.KoinJavaComponent.inject

class UsersPresenter(

) : MvpPresenter<UsersView>(), GithubPresenter {


    class UsersListPresenter : IUserListPresenter {
        val users = mutableListOf<GithubUser>()

        override var itemClickListener: ((UserItemView) -> Unit)? = null

        override fun getCount() = users.size

        override fun bindView(view: UserItemView, lPos: Int) {
            val user = users[view.pos]
            view.setLogin(user.login)
        }
    }
    private val uiScheduler: Scheduler by inject(Scheduler::class.java)
    private val screens: AndroidScreens by inject(AndroidScreens::class.java)
    val router:Router by inject(Router::class.java)
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
            mainRepo.getUsers()
                .observeOn(uiScheduler)
                .subscribeBy(
                    onSuccess = {
                        usersListPresenter.users.clear()
                        usersListPresenter.users.addAll(it)
                        viewState.updateList()
                        viewState.setScreenState(SCREEN_STATE_IDLE)
                    },
                    onError = {
                        println("Error: ${it.message}")
                    })

        )
    }

    fun backPressed(): Boolean {
        router.exit()
        return true
    }
}
