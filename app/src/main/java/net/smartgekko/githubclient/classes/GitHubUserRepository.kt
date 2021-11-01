package net.smartgekko.githubclient.classes

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
    )]
)
class GitHubUserRepository(
    @PrimaryKey @ColumnInfo(name = "userId")
    @Expose val userId: String,

    @ColumnInfo(name = "name")
    @Expose val name: String,

    @ColumnInfo(name = "description")
    @Expose val description: String? = null,

    @Expose var selected: Boolean = true
) : Serializable
