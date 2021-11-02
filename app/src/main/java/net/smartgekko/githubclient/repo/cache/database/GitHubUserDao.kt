package net.smartgekko.githubclient.repo.cache.database

import androidx.room.*
import net.smartgekko.githubclient.classes.GithubUser

@Dao
interface GitHubUserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: GithubUser)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg users: GithubUser)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(users: List<GithubUser>)

    @Update
    fun update(user: GithubUser)

    @Update
    fun update(vararg users: GithubUser)

    @Update
    fun update(users: List<GithubUser>)

    @Delete
    fun delete(user: GithubUser)

    @Delete
    fun delete(vararg users: GithubUser)

    @Delete
    fun delete(users: List<GithubUser>)

    @Query("DELETE FROM users")
    fun clear()

    @Query("SELECT * FROM users")
    fun getAll(): List<GithubUser>

    @Query("SELECT * FROM users WHERE login = :login LIMIT 1")
    fun findByLogin(login: String): GithubUser?
}