package net.smartgekko.githubclient.repo.cache

import net.smartgekko.githubclient.classes.GithubUser

class RoomGithubUsersCache : RoomGithubCache<GithubUser> {

    override fun saveData(data: List<GithubUser>) {
        cacheDB.userDao.insert(data)
    }

    override fun loadDataAll(): List<GithubUser> = cacheDB.userDao.getAll()

    override fun loadDataForOne(id: String): List<GithubUser> {
        TODO("Not yet implemented")
    }
}