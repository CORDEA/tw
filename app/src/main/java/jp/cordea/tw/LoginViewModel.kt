package jp.cordea.tw

import androidx.lifecycle.ViewModel
import com.twitter.sdk.android.core.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import timber.log.Timber
import javax.inject.Inject

@ExperimentalCoroutinesApi
class LoginViewModel @Inject constructor() : ViewModel() {
    private val _onLoggedIn by lazy {
        val channel = ConflatedBroadcastChannel<Unit>()
        val session = TwitterCore.getInstance().sessionManager.activeSession
        if (session != null) {
            channel.offer(Unit)
        }
        channel
    }
    val onLoggedIn get() = _onLoggedIn.openSubscription()

    val loginCallback = object : Callback<TwitterSession>() {
        override fun success(result: Result<TwitterSession>?) {
            _onLoggedIn.offer(Unit)
        }

        override fun failure(exception: TwitterException?) {
            Timber.w(exception)
        }
    }
}
