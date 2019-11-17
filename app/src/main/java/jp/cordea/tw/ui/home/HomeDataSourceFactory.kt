package jp.cordea.tw.ui.home

import androidx.paging.DataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject
import javax.inject.Provider

@ExperimentalCoroutinesApi
class HomeDataSourceFactory @Inject constructor(
    private val dataSource: Provider<HomeDataSource>
) : DataSource.Factory<Long, HomeListItem>() {
    override fun create(): DataSource<Long, HomeListItem> = dataSource.get()
}
