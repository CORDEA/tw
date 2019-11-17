package jp.cordea.tw.ui.home

import androidx.paging.ItemKeyedDataSource
import jp.cordea.tw.StatusesRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@ExperimentalCoroutinesApi
class HomeDataSource @Inject constructor(
    private val repository: StatusesRepository
) : ItemKeyedDataSource<Long, HomeListItem>(), CoroutineScope {
    val job = Job()
    override val coroutineContext: CoroutineContext = Dispatchers.Main + job

    override fun loadInitial(
        params: LoadInitialParams<Long>,
        callback: LoadInitialCallback<HomeListItem>
    ) {
        launch {
            load(null, callback)
        }
    }

    override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<HomeListItem>) {
        launch {
            load(params.key, callback)
        }
    }

    override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<HomeListItem>) {
        // do nothing
    }

    override fun getKey(item: HomeListItem): Long = item.id

    private suspend fun load(key: Long?, callback: LoadCallback<HomeListItem>) =
        repository.findAll(key)
            .map {
                it.map { tweet ->
                    HomeListItem(HomeListItemModel(tweet.id, tweet.text))
                }
            }
            .collect { callback.onResult(it) }
}
