package net.smartgekko.githubclient.repo.cache

import net.smartgekko.githubclient.classes.GithubUserRepository

class RoomGithubRepositoriesCache : RoomGithubCache<GithubUserRepository> {

    override fun saveData(data: List<GithubUserRepository>) {
        cacheDB.repositoryDao.insert(data)
    }

    override fun loadDataAll(): List<GithubUserRepository> {
        TODO("Not yet implemented")
    }

    override fun loadDataForOne(id: String): List<GithubUserRepository> =
        cacheDB.repositoryDao.findForUser(id)
}