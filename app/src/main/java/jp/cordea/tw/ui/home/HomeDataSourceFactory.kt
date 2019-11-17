package jp.cordea.tw.ui.home

import androidx.paging.DataSource
import jp.cordea.tw.StatusesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job

@ExperimentalCoroutinesApi
class HomeDataSourceFactory(
    private val job: Job,
    private val repository: StatusesRepository
) : DataSource.Factory<Long, HomeListItem>() {
    override fun create(): DataSource<Long, HomeListItem> = HomeDataSource(job, repository)
}
