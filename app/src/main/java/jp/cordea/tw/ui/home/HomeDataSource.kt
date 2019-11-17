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
) : ItemKeyedDataSource<Long, HomeListItemModel>(), CoroutineScope {
    val job = Job()
    override val coroutineContext: CoroutineContext = Dispatchers.Main + job

    override fun loadInitial(
        params: LoadInitialParams<Long>,
        callback: LoadInitialCallback<HomeListItemModel>
    ) {
        launch {
            repository.findAll(null)
                .map { it.map { tweet -> HomeListItemModel(tweet.id, tweet.text) } }
                .collect { callback.onResult(it) }
        }
    }

    override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<HomeListItemModel>) {
        launch {
            repository.findAll(params.key)
                .map { it.map { tweet -> HomeListItemModel(tweet.id, tweet.text) } }
                .collect { callback.onResult(it) }
        }
    }

    override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<HomeListItemModel>) {
        // do nothing
    }

    override fun getKey(item: HomeListItemModel): Long = item.id
}
