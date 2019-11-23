package jp.cordea.tw

import com.twitter.sdk.android.core.TwitterCore
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AccountRepository @Inject constructor() {
    private val service by lazy {
        TwitterCore.getInstance()
            .apiClient
            .accountService
    }

    fun find() =
        flow {
            emit(
                service.verifyCredentials(false, true, true)
                    .execute()
            )
        }
            .map {
                if (it.body() == null) {
                    AccountResult.Failure(it.errorBody()!!.string())
                } else {
                    AccountResult.Success(it.body()!!)
                }
            }
}
