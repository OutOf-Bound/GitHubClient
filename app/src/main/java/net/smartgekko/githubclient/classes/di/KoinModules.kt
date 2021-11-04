package net.smartgekko.githubclient.classes

import androidx.room.Room
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import io.reactivex.android.schedulers.AndroidSchedulers
import net.smartgekko.githubclient.repo.cache.database.CacheDatabase
import net.smartgekko.githubclient.ui.AndroidScreens
import net.smartgekko.githubclient.ui.main.MainPresenter
import org.koin.dsl.module


val roomModule = module {
    val dbPath = "database"

    single {
        Room.databaseBuilder(get(), CacheDatabase::class.java, dbPath)
            .allowMainThreadQueries()
            .build()
    }
}

val ciceroneModule = module {
    single { Cicerone.create() }
    single { get<Cicerone<Router>>().getNavigatorHolder() }
    single { get<Cicerone<Router>>().router }
}

val mainModule = module {
    factory { AndroidScreens() }
    factory { MainPresenter() }
}
val usersModule = module{
    single{ AndroidSchedulers.mainThread()}
}
