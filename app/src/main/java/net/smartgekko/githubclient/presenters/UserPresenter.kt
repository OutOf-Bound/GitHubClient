package net.smartgekko.githubclient.presenters

import com.github.terrakok.cicerone.Router
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import moxy.MvpPresenter
import net.smartgekko.githubclient.repo.GitHubUserRepository
import net.smartgekko.githubclient.repo.GithubUser
import net.smartgekko.githubclient.repo.IGithubUsersRepo
import net.smartgekko.githubclient.ui.IScreens
import net.smartgekko.githubclient.ui.RepoItemView
import net.smartgekko.githubclient.ui.UserView

class UserPresenter(
    private val userRepo: IGithubUsersRepo,
    private val router: Router,
    private val screens: IScreens
) :
    MvpPresenter<UserView>() {

    private lateinit var currUser: GithubUser
    private var compositeDisposable: CompositeDisposable = CompositeDisposable()


    class ReposListPresenter : IUserReposListPresenter {
        val userRepos = mutableListOf<GitHubUserRepository>()

        override var itemClickListener: ((RepoItemView) -> Unit)? = null

        override fun getCount() = userRepos.size

        override fun bindView(view: RepoItemView) {

            val repo = userRepos[view.pos]

            view.setName(repo.name)
            repo.description?.let { view.setDesc(repo.description) }
        }
    }

    val repoListPresenter = UserPresenter.ReposListPresenter()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        loadRepos()
        repoListPresenter.itemClickListener = { itemView ->
            itemView.textToggle()
        }
    }

    fun setCurrentUser(user: GithubUser) {
        currUser = user
    }

    fun backPressed(): Boolean {
        router.backTo(screens.users())
        return true
    }

    private fun loadRepos() {
        //  viewState.setScreenState(SCREEN_STATE_LOADING)

        if (currUser.reposUrl != null) {
            compositeDisposable.add(
                userRepo.getUserRepository(currUser.reposUrl!!)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeBy(
                        onNext = {
                            repoListPresenter.userRepos.clear()
                            repoListPresenter.userRepos.addAll(it)
                            viewState.updateUserReposList()
                        },
                        onComplete = {
                            // viewState.setScreenState(SCREEN_STATE_IDLE)
                        },
                        onError = {
                            println("Error: ${it.message}")
                        })
            )
        }
    }
}