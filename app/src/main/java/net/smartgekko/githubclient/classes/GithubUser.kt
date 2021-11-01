package net.smartgekko.githubclient.classes

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import java.io.Serializable

@Entity (tableName = "users")
class GithubUser(
    @PrimaryKey  @ColumnInfo(name = "id") @NonNull
    @Expose val id: String,

    @ColumnInfo(name = "login")
    @Expose val login: String,

    @ColumnInfo(name = "avatarUrl")
    @Expose val avatarUrl: String? = null,

    @ColumnInfo(name = "reposUrl")
    @Expose val reposUrl: String? = null
) : Serializable


