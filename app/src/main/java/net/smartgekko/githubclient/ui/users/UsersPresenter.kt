package net.smartgekko.githubclient.ui.users

import com.github.terrakok.cicerone.Router
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import moxy.MvpPresenter
import net.smartgekko.githubclient.App
import net.smartgekko.githubclient.SCREEN_STATE_IDLE
import net.smartgekko.githubclient.SCREEN_STATE_LOADING
import net.smartgekko.githubclient.presenters.IUserListPresenter
import net.smartgekko.githubclient.classes.GithubUser
import net.smartgekko.githubclient.ui.AndroidScreens
import net.smartgekko.githubclient.ui.GithubPresenter
import net.smartgekko.githubclient.ui.user.UserItemView
import javax.inject.Inject

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
    //@Inject
    //lateinit var uiScheduler: Scheduler
    val uiScheduler: Scheduler = AndroidSchedulers.mainThread()
   // @Inject
   // lateinit var screens: AndroidScreens
   val screens: AndroidScreens = AndroidScreens()
   // @Inject
   // lateinit var router:Router
   val router:Router = App.instance.router

    val usersListPresenter = UsersListPresenter()
    private var compositeDisposable: CompositeDisposable = CompositeDisposable()


    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
      //  App.instance.appComponent.inject(this)
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
