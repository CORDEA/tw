package jp.cordea.tw.ui.home

import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import jp.cordea.tw.StatusesRepository
import jp.cordea.tw.StatusesUpdateResult
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@ExperimentalCoroutinesApi
class HomeViewModel @Inject constructor(
    private val repository: StatusesRepository,
    private val listItemFactory: HomeListItem.Factory
) : ViewModel(), CoroutineScope {
    private val job = Job()
    override val coroutineContext: CoroutineContext = Dispatchers.Main + job

    val onData by lazy {
        LivePagedListBuilder(
            HomeDataSourceFactory(Job(parent = job), repository, listItemFactory),
            PagedList.Config.Builder()
                .setPageSize(200)
                .setEnablePlaceholders(true)
                .build()
        ).build()
    }

    val onShowBottomSheet = Channel<List<HomeBottomSheetItemModel>>()
    val onShowTweetBottomSheet = Channel<Unit>()
    val onSucceededTweet = Channel<Unit>()
    val onFailedTweet = Channel<String>()

    fun onItemClicked(urls: List<String>) {
        onShowBottomSheet.offer(urls.map { HomeBottomSheetItemModel(it) })
    }

    fun onFabClicked() {
        onShowTweetBottomSheet.offer(Unit)
    }

    fun onTweet(status: String) {
        launch {
            repository.update(status)
                .collect {
                    when (it) {
                        StatusesUpdateResult.Success ->
                            onSucceededTweet.offer(Unit)
                        is StatusesUpdateResult.Failure ->
                            onFailedTweet.offer(it.error)
                    }
                }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}
