package jp.cordea.tw.ui.home

import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import jp.cordea.tw.StatusesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
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

    fun onItemClicked(urls: List<String>) {
        onShowBottomSheet.offer(urls.map { HomeBottomSheetItemModel(it) })
    }

    fun onFabClicked() {
        onShowTweetBottomSheet.offer(Unit)
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}
