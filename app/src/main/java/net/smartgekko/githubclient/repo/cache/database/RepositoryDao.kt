package net.smartgekko.githubclient.repo.cache.database

import androidx.room.*
import net.smartgekko.githubclient.classes.GithubUserRepository

@Dao
interface RepositoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: GithubUserRepository)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg users: GithubUserRepository)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(users: List<GithubUserRepository>)

    @Update
    fun update(user: GithubUserRepository)

    @Update
    fun update(vararg users: GithubUserRepository)

    @Update
    fun update(users: List<GithubUserRepository>)

    @Delete
    fun delete(user: GithubUserRepository)

    @Delete
    fun delete(vararg users: GithubUserRepository)

    @Delete
    fun delete(users: List<GithubUserRepository>)

    @Query("SELECT * FROM repos")
    fun getAll(): List<GithubUserRepository>

    @Query("SELECT * FROM repos WHERE userId = :userId")
    fun findForUser(userId: String): List<GithubUserRepository>
}