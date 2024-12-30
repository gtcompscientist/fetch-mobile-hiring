package co.csadev.fetchhiring

import android.app.Application
import co.csadev.fetchhiring.networking.FetchHiringApi
import co.csadev.fetchhiring.networking.FetchHiringApiImpl

class App: Application() {
    companion object {
        lateinit var Api: FetchHiringApi
    }

    override fun onCreate() {
        super.onCreate()
        Api = FetchHiringApiImpl()
    }
}