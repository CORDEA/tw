package jp.cordea.tw

import com.twitter.sdk.android.core.models.Tweet

sealed class StatusesResult {
    class Success(val tweets: List<Tweet>) : StatusesResult()
    class Failure(val error: String) : StatusesResult()
}

sealed class StatusesUpdateResult {
    object Success : StatusesUpdateResult()
    class Failure(val error: String) : StatusesUpdateResult()
}
