package jp.cordea.tw.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import jp.cordea.tw.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
class HomeFragment : Fragment(),
    CoroutineScope by MainScope(),
    ViewModelInjectable<HomeViewModel> {
    @Inject
    override lateinit var viewModelFactory: ViewModelFactory<HomeViewModel>

    private val viewModel by lazy { viewModel() }
    private val adapter = GroupAdapter<GroupieViewHolder>()

    override fun onAttach(context: Context) {
        (requireActivity() as MainActivity)
            .subcomponent
            .homeFragmentSubcomponent()
            .create()
            .inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_home, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.adapter = adapter

        launch {
            viewModel.onData
                .consumeEach { data ->
                    adapter.addAll(data.map { HomeListItem(it) })
                }
        }
    }
}
