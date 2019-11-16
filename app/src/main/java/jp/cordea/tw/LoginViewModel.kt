package jp.cordea.tw

import androidx.lifecycle.ViewModel
import com.twitter.sdk.android.core.Callback
import com.twitter.sdk.android.core.Result
import com.twitter.sdk.android.core.TwitterException
import com.twitter.sdk.android.core.TwitterSession
import timber.log.Timber
import javax.inject.Inject

class LoginViewModel @Inject constructor() : ViewModel() {

    val loginCallback = object : Callback<TwitterSession>() {
        override fun success(result: Result<TwitterSession>?) {
        }

        override fun failure(exception: TwitterException?) {
            Timber.w(exception)
        }
    }
}
