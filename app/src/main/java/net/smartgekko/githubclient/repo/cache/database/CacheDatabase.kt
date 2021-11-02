package net.smartgekko.githubclient.repo.cache.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import net.smartgekko.githubclient.classes.GithubUserRepository
import net.smartgekko.githubclient.classes.GithubUser

@androidx.room.Database(entities = [GithubUser::class, GithubUserRepository::class], version = 1)
abstract class CacheDatabase : RoomDatabase() {
    abstract val userDao: GitHubUserDao
    abstract val repositoryDao: RepositoryDao

    companion object {
        private const val DB_NAME = "database.db"
        private var instance: CacheDatabase? = null

        fun getInstance() = instance ?: throw RuntimeException("Database has not been created. Please call create(context)")

        fun create(context: Context?) {
            if (instance == null) {
                instance = Room.databaseBuilder(context!!, CacheDatabase::class.java, DB_NAME)
                    .allowMainThreadQueries()
                    .build()
            }
        }
    }
}