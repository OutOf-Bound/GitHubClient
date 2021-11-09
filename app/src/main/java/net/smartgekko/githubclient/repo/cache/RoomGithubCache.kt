package net.smartgekko.githubclient.repo.cache

import net.smartgekko.githubclient.App
import net.smartgekko.githubclient.repo.cache.database.CacheDatabase

interface RoomGithubCache<T> {

    val cacheDB: CacheDatabase
        get() = App.instance.appComponent.getDb()

    fun saveData(data: List<T>)
    fun loadDataAll(): List<T>
    fun loadDataForOne(id: String): List<T>
}