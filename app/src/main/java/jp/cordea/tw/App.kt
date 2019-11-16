package jp.cordea.tw

import android.app.Application
import android.util.Log
import com.twitter.sdk.android.core.DefaultLogger
import com.twitter.sdk.android.core.Twitter
import com.twitter.sdk.android.core.TwitterAuthConfig
import com.twitter.sdk.android.core.TwitterConfig

class App : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent =
            DaggerAppComponent
                .factory()
                .create(this)

        Twitter.initialize(
            TwitterConfig
                .Builder(this)
                .twitterAuthConfig(
                    TwitterAuthConfig(
                        BuildConfig.CONSUMER_KEY,
                        BuildConfig.CONSUMER_SECRET
                    )
                )
                .logger(DefaultLogger(Log.DEBUG))
                .debug(true)
                .build()
        )
    }
}
