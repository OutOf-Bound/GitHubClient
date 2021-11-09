package net.smartgekko.githubclient.ui.user

import com.github.terrakok.cicerone.Router
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import moxy.MvpPresenter
import net.smartgekko.githubclient.App
import net.smartgekko.githubclient.SCREEN_STATE_IDLE
import net.smartgekko.githubclient.SCREEN_STATE_LOADING
import net.smartgekko.githubclient.classes.GithubUser
import net.smartgekko.githubclient.classes.GithubUserRepository
import net.smartgekko.githubclient.presenters.IUserReposListPresenter
import net.smartgekko.githubclient.ui.AndroidScreens
import net.smartgekko.githubclient.ui.GithubPresenter
import javax.inject.Inject

class UserPresenter() : MvpPresenter<UserView>(), GithubPresenter {
    @Inject
    lateinit var router: Router

    @Inject
    lateinit var screens: AndroidScreens

    private lateinit var currUser: GithubUser
    private var compositeDisposable: CompositeDisposable = CompositeDisposable()


    class ReposListPresenter : IUserReposListPresenter {
        val userRepos = mutableListOf<GithubUserRepository>()

        override var itemClickListener: ((RepoItemView) -> Unit)? = null

        override fun getCount() = userRepos.size

        override fun bindView(view: RepoItemView, lPos: Int) {
            val repo = userRepos[view.pos]

            if (repo.selected) {
                view.showDesc()
            } else {
                view.hideDesc()
            }

            view.setName(repo.name)

            if (repo.description != null) {
                view.setDesc(repo.description)
            } else {
                view.setDesc("")
            }

            itemClickListener = {

                userRepos[it.pos].selected = !userRepos[it.pos].selected
                view.noteItemChanged(it.pos)
            }
        }
    }

    val repoListPresenter = ReposListPresenter()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        App.instance.appComponent.inject(this)
        loadRepos()
    }

    fun setCurrentUser(user: GithubUser) {
        currUser = user
    }

    fun backPressed(): Boolean {
        router.backTo(screens.users())
        return true
    }

    private fun loadRepos() {
        viewState.setScreenState(SCREEN_STATE_LOADING)

        if (currUser.reposUrl != null) {
            compositeDisposable.add(
                mainRepo.getUserRepositoriesList(currUser.id, currUser.reposUrl!!)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeBy(
                        onSuccess = {
                            repoListPresenter.userRepos.clear()
                            repoListPresenter.userRepos.addAll(it)
                            viewState.updateUserReposList()
                            viewState.setScreenState(SCREEN_STATE_IDLE)
                        },
                        onError = {
                            println("Error: ${it.message}")
                        })
            )
        }
    }
}