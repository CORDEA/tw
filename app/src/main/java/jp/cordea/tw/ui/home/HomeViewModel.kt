package jp.cordea.tw.ui.home

import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import jp.cordea.tw.StatusesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@ExperimentalCoroutinesApi
class HomeViewModel @Inject constructor(
    private val repository: StatusesRepository
) : ViewModel(), CoroutineScope {
    private val job = Job()
    override val coroutineContext: CoroutineContext = Dispatchers.Main + job

    val onData by lazy {
        LivePagedListBuilder(
            HomeDataSourceFactory(Job(parent = job), repository),
            PagedList.Config.Builder()
                .setPageSize(20)
                .setEnablePlaceholders(true)
                .build()
        ).build()
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}
