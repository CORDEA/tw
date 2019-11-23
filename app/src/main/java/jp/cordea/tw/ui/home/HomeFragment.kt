package jp.cordea.tw.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import jp.cordea.tw.R
import jp.cordea.tw.ViewModelFactory
import jp.cordea.tw.ViewModelInjectable
import jp.cordea.tw.ui.main.MainActivity
import jp.cordea.tw.viewModel
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.ldralighieri.corbind.view.clicks
import javax.inject.Inject

@ExperimentalCoroutinesApi
class HomeFragment : Fragment(),
    CoroutineScope by MainScope(),
    ViewModelInjectable<HomeViewModel>,
    HomeListItem.OnItemClickListener {
    @Inject
    override lateinit var viewModelFactory: ViewModelFactory<HomeViewModel>

    private val viewModel by lazy { viewModel() }
    private val adapter = HomeAdapter()
    private val bottomSheetAdapter = GroupAdapter<GroupieViewHolder>()

    override fun onAttach(context: Context) {
        (requireActivity() as MainActivity)
            .subcomponent
            .homeFragmentSubcomponent()
            .create(this)
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
        bottomSheetRecyclerView.adapter = bottomSheetAdapter
        val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetRecyclerView)

        viewModel.onData
            .observe(viewLifecycleOwner, Observer { data ->
                adapter.submitList(data)
            })

        fab.clicks()
            .onEach { viewModel.onFabClicked() }
            .launchIn(this)
        launch {
            viewModel.onShowBottomSheet
                .consumeEach { models ->
                    bottomSheetAdapter.clear()
                    bottomSheetAdapter.addAll(models.map { HomeBottomSheetItem(it) })
                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                }
        }
    }

    override fun onItemClick(urls: List<String>) {
        viewModel.onItemClicked(urls)
    }
}
