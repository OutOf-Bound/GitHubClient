package net.smartgekko.githubclient

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.room.Room
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import net.smartgekko.githubclient.classes.AppComponent
import net.smartgekko.githubclient.classes.DaggerAppComponent
import net.smartgekko.githubclient.classes.RoomModule
import net.smartgekko.githubclient.repo.cache.database.CacheDatabase
import net.smartgekko.githubclient.repo.network.AndroidNetworkStatus
import net.smartgekko.githubclient.repo.network.INetworkStatus
import javax.inject.Inject

class App : Application() {

    companion object {
        lateinit var instance: App
        lateinit var networkStatus: INetworkStatus
/*
        val db = Room.databaseBuilder(App.instance, CacheDatabase::class.java, "database")
            .allowMainThreadQueries()
            .build()
*/
        //Теперь это Dagger2:
        //@Inject
      //  lateinit var db:CacheDatabase
    }

    val appComponent: AppComponent by lazy {
        DaggerAppComponent
            .builder().roomModule(RoomModule(this)).build()
    }

    //Временно до даггера положим это тут
    //Пока что это Koin
   // private val cicerone: Cicerone<Router> by inject()
    val cicerone: Cicerone<Router> = Cicerone.create()
    val navigatorHolder get() = cicerone.getNavigatorHolder()
    val router get() = cicerone.router

    //Теперь это Dagger2:
    /*
    @Inject
    lateinit var cicerone: Cicerone<Router>

    @Inject
    lateinit var navigatorHolder:NavigatorHolder

    @Inject
    lateinit var router: Router
*/
    fun getNetworkStatus() = networkStatus

    fun showMessage(msg: String) {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        networkStatus = AndroidNetworkStatus(applicationContext)
    }
}

val Context.app:App
    get() = applicationContext as App