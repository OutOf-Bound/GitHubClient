package net.smartgekko.githubclient.classes

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import java.io.Serializable

@Entity(
    foreignKeys = [ForeignKey(
        entity = GithubUser::class,
        parentColumns = ["id"],
        childColumns = ["userId"],
        onDelete = ForeignKey.CASCADE
    )],
    tableName = "repos"
)
class GitHubUserRepository(
    @PrimaryKey  @ColumnInfo(name = "id") @NonNull
    @Expose val id: String,

    @ColumnInfo(name = "userId")
    @Expose var userId: String ="",

    @ColumnInfo(name = "name")
    @Expose val name: String,

    @ColumnInfo(name = "description")
    @Expose val description: String? = null,

    @ColumnInfo(name = "selected", defaultValue = "false")
    @Expose var selected: Boolean = false
) : Serializable
