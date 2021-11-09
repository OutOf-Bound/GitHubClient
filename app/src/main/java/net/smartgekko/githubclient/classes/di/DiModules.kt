package net.smartgekko.githubclient.classes

import android.content.Context
import androidx.room.Room
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import dagger.Component
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import net.smartgekko.githubclient.repo.cache.database.CacheDatabase
import net.smartgekko.githubclient.ui.AndroidScreens
import net.smartgekko.githubclient.ui.main.MainActivity
import net.smartgekko.githubclient.ui.main.MainPresenter
import net.smartgekko.githubclient.ui.user.UserPresenter
import net.smartgekko.githubclient.ui.users.UsersPresenter
import javax.inject.Named
import javax.inject.Singleton

@Module
class RoomModule(private val context: Context) {
    @Provides
    @Singleton
    fun provideContext(): Context = context

    @Provides
    @Singleton
    fun providDbPath(): String = "database"


    @Provides
    @Singleton
    fun provideRoomDB(context: Context, dbPath: String): CacheDatabase =
        Room.databaseBuilder(context, CacheDatabase::class.java, dbPath)
            .allowMainThreadQueries()
            .build()
}

@Module
class CiceroneModule {

    @Provides
    @Singleton
    @Named("cicerone")
    fun provideCicerone(): Cicerone<Router> = Cicerone.create()

    @Provides
    @Singleton
    fun provideCiceroneRouter(@Named("cicerone") cicerone: Cicerone<Router>): Router =
        cicerone.router

    @Provides
    @Singleton
    fun provideCiceroneNavigatorHolder(@Named("cicerone") cicerone: Cicerone<Router>): NavigatorHolder =
        cicerone.getNavigatorHolder()
}

@Module
class MainModule {
    @Provides
    fun provideFndroidScreens(): AndroidScreens = AndroidScreens()
}

@Module
class UsersModule {
    @Provides
    fun provideAndroidSchedulersMainThread(): Scheduler = AndroidSchedulers.mainThread()
}

@Singleton
@Component(modules = [CiceroneModule::class, MainModule::class, UsersModule::class, RoomModule::class])
interface AppComponent {
    fun inject(activity: MainActivity)
    fun inject(mainPresenter: MainPresenter)
    fun inject(userPresenter: UserPresenter)
    fun inject(usersPresenter: UsersPresenter)
    fun getDb(): CacheDatabase
}

