package net.smartgekko.githubclient.repo.api

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import net.smartgekko.githubclient.App
import net.smartgekko.githubclient.repo.network.INetworkStatus

class RetrofitGithubUsersRepo(
    val api: IDataSource,
    val networkStatus: INetworkStatus
) : IGithubUsersRepo {

    override fun getUsers() = networkStatus.isOnlineSingle().flatMap { isOnline ->
        if (isOnline) {
            Single.fromObservable(api.getUsers()).doOnSuccess {
                cacheUsers.saveData(it)
            }

        } else {
            Single.fromObservable(Observable.fromArray(cacheUsers.loadDataAll())).doAfterSuccess {
                App.instance.showMessage("No network. Data from cache")
            }.subscribeOn(AndroidSchedulers.mainThread())
        }
    }.subscribeOn(Schedulers.io())

    override fun getUserRepositoriesList(userId: String, url: String) =
        networkStatus.isOnlineSingle().flatMap { isOnline ->
            if (isOnline) {
                Single.fromObservable(api.getUserRepository(url)).doOnSuccess {
                    it.forEach {
                        it.userId = userId
                    }
                    cacheUsersRepositories.saveData(it)
                }
            } else {
                Single.fromObservable(
                    Observable.fromArray(
                        cacheUsersRepositories.loadDataForOne(
                            userId
                        )
                    )
                ).doAfterSuccess {
                    App.instance.showMessage("No network. Data from cache")
                }.subscribeOn(AndroidSchedulers.mainThread())
            }
        }.subscribeOn(Schedulers.io())
}