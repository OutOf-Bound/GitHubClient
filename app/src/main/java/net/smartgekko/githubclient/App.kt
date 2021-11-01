package net.smartgekko.githubclient

import android.app.Application
import android.widget.Toast
import androidx.room.Room
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import net.smartgekko.githubclient.repo.database.CacheDatabase
import net.smartgekko.githubclient.repo.network.AndroidNetworkStatus
import net.smartgekko.githubclient.repo.network.INetworkStatus

class App : Application() {

    companion object {
        lateinit var instance: App
        lateinit var db: CacheDatabase
        lateinit var networkStatus: INetworkStatus

    }

    //Временно до даггера положим это тут
    private val cicerone: Cicerone<Router> by lazy {
        Cicerone.create()
    }

    val navigatorHolder get() = cicerone.getNavigatorHolder()
    val router get() = cicerone.router

    fun getNetworkStatus() = networkStatus

    fun showMessage(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        db = Room.databaseBuilder(applicationContext, CacheDatabase::class.java, "database")
            .allowMainThreadQueries()
            .build()
        networkStatus = AndroidNetworkStatus(applicationContext)
    }
}

