package jp.cordea.tw

import com.twitter.sdk.android.core.TwitterCore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StatusesRepository @Inject constructor() {
    private val service by lazy {
        TwitterCore.getInstance()
            .apiClient
            .statusesService
    }

    @ExperimentalCoroutinesApi
    fun findAll(count: Int, maxId: Long?) =
        flow {
            emit(
                service
                    .homeTimeline(count, null, maxId, false, false, false, true)
                    .execute()
            )
        }
            .map {
                if (it.body() == null) {
                    StatusesResult.Failure(it.errorBody()!!.string())
                } else {
                    StatusesResult.Success(it.body()!!)
                }
            }

    @ExperimentalCoroutinesApi
    fun update(status: String) =
        flow {
            emit(
                service
                    .update(
                        status,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null
                    )
                    .execute()
            )
        }
            .map {
                if (it.body() == null) {
                    StatusesUpdateResult.Failure(it.errorBody()!!.string())
                } else {
                    StatusesUpdateResult.Success
                }
            }
}
