package jp.cordea.tw.ui.home

import androidx.paging.ItemKeyedDataSource
import jp.cordea.tw.StatusesRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlin.coroutines.CoroutineContext

@ExperimentalCoroutinesApi
class HomeDataSource(
    job: Job,
    private val repository: StatusesRepository
) : ItemKeyedDataSource<Long, HomeListItem>(), CoroutineScope {
    override val coroutineContext: CoroutineContext = Dispatchers.Main + job

    override fun loadInitial(
        params: LoadInitialParams<Long>,
        callback: LoadInitialCallback<HomeListItem>
    ) {
        launch {
            load(params.requestedLoadSize, null)
                .collect { callback.onResult(it) }
        }
    }

    override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<HomeListItem>) {
        launch {
            load(params.requestedLoadSize, params.key)
                .map { it.drop(1) }
                .collect { callback.onResult(it) }
        }
    }

    override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<HomeListItem>) {
        // do nothing
    }

    override fun getKey(item: HomeListItem): Long = item.id

    private fun load(size: Int, key: Long?) =
        repository.findAll(size, key)
            .map {
                it.map { tweet ->
                    HomeListItem(HomeListItemModel(tweet.id, tweet.text))
                }
            }
}
