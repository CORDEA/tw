package jp.cordea.tw.ui.home

import androidx.lifecycle.ViewModel
import jp.cordea.tw.StatusesRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@ExperimentalCoroutinesApi
class HomeViewModel @Inject constructor(
    private val repository: StatusesRepository
) : ViewModel(), CoroutineScope {
    private val job = Job()
    override val coroutineContext: CoroutineContext = Dispatchers.Main + job

    private val _onData by lazy {
        val channel = ConflatedBroadcastChannel<List<HomeListItemModel>>()
        launch {
            repository.findAll(null)
                .map { it.map { tweet -> HomeListItemModel(tweet.id, tweet.text) } }
                .collect { channel.send(it) }
        }
        channel
    }
    val onData get() = _onData.openSubscription()

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}
