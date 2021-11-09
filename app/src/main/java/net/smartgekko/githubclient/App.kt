package net.smartgekko.githubclient

import android.app.Application
import android.content.Context
import android.widget.Toast
import net.smartgekko.githubclient.classes.AppComponent
import net.smartgekko.githubclient.classes.DaggerAppComponent
import net.smartgekko.githubclient.classes.RoomModule
import net.smartgekko.githubclient.repo.network.AndroidNetworkStatus
import net.smartgekko.githubclient.repo.network.INetworkStatus

class App : Application() {

    companion object {
        lateinit var instance: App
        lateinit var networkStatus: INetworkStatus
    }

    val appComponent: AppComponent by lazy {
        DaggerAppComponent
            .builder().roomModule(RoomModule(this)).build()
    }

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

val Context.app: App
    get() = applicationContext as App