package net.smartgekko.githubclient

import android.app.Application
import android.widget.Toast
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import net.smartgekko.githubclient.classes.ciceroneModule
import net.smartgekko.githubclient.classes.mainModule
import net.smartgekko.githubclient.classes.roomModule
import net.smartgekko.githubclient.classes.usersModule
import net.smartgekko.githubclient.repo.network.AndroidNetworkStatus
import net.smartgekko.githubclient.repo.network.INetworkStatus
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext

class App : Application() {

    companion object {
        lateinit var instance: App
        lateinit var networkStatus: INetworkStatus

    }

    //Временно до даггера положим это тут
    //Пока что это Koin
    private val cicerone: Cicerone<Router> by inject()

   // val navigatorHolder get() = cicerone.getNavigatorHolder()
    //val router get() = cicerone.router

    fun getNetworkStatus() = networkStatus

    fun showMessage(msg: String) {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        networkStatus = AndroidNetworkStatus(applicationContext)

        GlobalContext.startKoin {
            androidLogger()
            //inject Android context
            androidContext(this@App)
            // use modules
            modules(ciceroneModule)
            modules(roomModule)
            modules(mainModule)
            modules(usersModule)

        }
    }
}

