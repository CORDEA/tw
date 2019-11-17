package jp.cordea.tw

import com.twitter.sdk.android.core.TwitterCore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StatusesRepository @Inject constructor() {
    @ExperimentalCoroutinesApi
    fun findAll(maxId: Long?) =
        flow {
            emit(
                TwitterCore.getInstance()
                    .apiClient
                    .statusesService
                    .homeTimeline(200, null, null, false, false, false, true)
                    .execute()
            )
        }
            .flowOn(Dispatchers.IO)
            .map { it.body()!! }
}
