package jp.cordea.tw.ui.home

import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import jp.cordea.tw.StatusesRepository
import jp.cordea.tw.StatusesUpdateResult
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
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

    val onNavigation = Channel<Navigation>()

    fun onItemClicked(urls: List<String>) {
        onNavigation.offer(
            if (urls.size > 1) {
                Navigation.ShowLinksBottomSheet(urls.map { HomeBottomSheetItemModel(it) })
            } else {
                Navigation.OpenLink(urls.first())
            }
        )
    }

    fun onFabClicked() {
        onNavigation.offer(Navigation.ShowTweetBottomSheet)
    }

    fun onTweet(status: String) {
        launch {
            repository.update(status)
                .flowOn(Dispatchers.IO)
                .collect {
                    onNavigation.offer(
                        when (it) {
                            StatusesUpdateResult.Success ->
                                Navigation.ShowTweetSuccessToast
                            is StatusesUpdateResult.Failure ->
                                Navigation.ShowTweetFailureToast(it.error)
                        }
                    )
                }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    sealed class Navigation {
        class OpenLink(val url: String) : Navigation()
        class ShowLinksBottomSheet(
            val itemViewModels: List<HomeBottomSheetItemModel>
        ) : Navigation()

        object ShowTweetBottomSheet : Navigation()
        object ShowTweetSuccessToast : Navigation()
        class ShowTweetFailureToast(val error: String) : Navigation()
    }
}
