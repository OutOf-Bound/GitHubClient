package net.smartgekko.githubclient.repo.database

import androidx.room.*
import net.smartgekko.githubclient.classes.GitHubUserRepository

@Dao
interface RepositoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: GitHubUserRepository)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg users: GitHubUserRepository)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(users: List<GitHubUserRepository>)

    @Update
    fun update(user: GitHubUserRepository)

    @Update
    fun update(vararg users: GitHubUserRepository)

    @Update
    fun update(users: List<GitHubUserRepository>)

    @Delete
    fun delete(user: GitHubUserRepository)

    @Delete
    fun delete(vararg users: GitHubUserRepository)

    @Delete
    fun delete(users: List<GitHubUserRepository>)

    @Query("SELECT * FROM GitHubUserRepository")
    fun getAll(): List<GitHubUserRepository>

    @Query("SELECT * FROM GitHubUserRepository WHERE userId = :userId")
    fun findForUser(userId: String): List<GitHubUserRepository>
}