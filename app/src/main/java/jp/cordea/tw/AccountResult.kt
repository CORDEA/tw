package jp.cordea.tw

import com.twitter.sdk.android.core.models.User

sealed class AccountResult {
    class Success(val user: User) : AccountResult()
    class Failure(val error: String) : AccountResult()
}
