package jp.cordea.tw.ui.profile

import android.content.Context
import androidx.lifecycle.ViewModel
import jp.cordea.tw.AccountRepository
import jp.cordea.tw.AccountResult
import jp.cordea.tw.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class ProfileViewModel @Inject constructor(
    private val repository: AccountRepository
) : ViewModel(), CoroutineScope {
    private val job = Job()
    override val coroutineContext: CoroutineContext = Dispatchers.Main + job

    private val _onModel by lazy {
        val channel = ConflatedBroadcastChannel<Model>()
        repository.find()
            .filter { it is AccountResult.Success }
            .map { (it as AccountResult.Success).user }
            .flowOn(Dispatchers.IO)
            .onEach {
                channel.offer(Model(it.profileImageUrl, it.name, it.screenName))
            }
            .launchIn(this)
        channel
    }
    val onModel get() = _onModel.openSubscription()

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    class Model(
        val imageUrl: String,
        val name: String,
        private val screenName: String
    ) {
        fun getId(context: Context) = context.getString(R.string.screen_name_format, screenName)
    }
}
