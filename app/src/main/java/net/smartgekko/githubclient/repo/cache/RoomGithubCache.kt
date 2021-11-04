package net.smartgekko.githubclient.repo.cache

import net.smartgekko.githubclient.repo.cache.database.CacheDatabase
import org.koin.java.KoinJavaComponent.getKoin

interface RoomGithubCache<T> {

    val cacheDB: CacheDatabase
    get() =  getKoin().get()

    fun saveData(data:List<T>)
    fun loadDataAll():List<T>
    fun loadDataForOne(id:String):List<T>
}