package jp.cordea.tw.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import jp.cordea.tw.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.MainScope
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

        viewModel.onData
            .observe(viewLifecycleOwner, Observer { data ->
                adapter.submitList(data)
            })
    }

    override fun onItemClick(urls: List<String>) {
    }
}
