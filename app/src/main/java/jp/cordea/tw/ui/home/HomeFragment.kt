package jp.cordea.tw.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import jp.cordea.tw.R
import jp.cordea.tw.ViewModelFactory
import jp.cordea.tw.ViewModelInjectable
import jp.cordea.tw.ui.home.HomeViewModel.Navigation
import jp.cordea.tw.ui.main.MainActivity
import jp.cordea.tw.ui.tweet.TweetBottomSheetDialogFragment
import jp.cordea.tw.viewModel
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.ldralighieri.corbind.view.clicks
import javax.inject.Inject

@ExperimentalCoroutinesApi
class HomeFragment : Fragment(),
    CoroutineScope by MainScope(),
    ViewModelInjectable<HomeViewModel>,
    HomeListItem.OnItemClickListener,
    TweetBottomSheetDialogFragment.OnTweetListener {
    @Inject
    override lateinit var viewModelFactory: ViewModelFactory<HomeViewModel>

    lateinit var subcomponent: HomeFragmentSubcomponent

    private val viewModel by lazy { viewModel() }
    private val adapter = HomeAdapter()
    private val bottomSheetAdapter = GroupAdapter<GroupieViewHolder>()

    override fun onAttach(context: Context) {
        subcomponent =
            (requireActivity() as MainActivity)
                .subcomponent
                .homeFragmentSubcomponent()
                .create(this)
        subcomponent.inject(this)
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
            for (navigation in viewModel.onNavigation) {
                when (navigation) {
                    is Navigation.OpenLink -> TODO()
                    is Navigation.ShowLinksBottomSheet -> {
                        bottomSheetAdapter.clear()
                        bottomSheetAdapter.addAll(
                            navigation.itemViewModels.map { HomeBottomSheetItem(it) }
                        )
                        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                    }
                    Navigation.ShowTweetBottomSheet ->
                        TweetBottomSheetDialogFragment.newInstance().show(childFragmentManager)
                    Navigation.ShowTweetSuccessToast ->
                        Toast.makeText(
                            requireContext(),
                            R.string.home_tweet_succeeded,
                            Toast.LENGTH_SHORT
                        ).show()
                    is Navigation.ShowTweetFailureToast ->
                        Toast.makeText(
                            requireContext(),
                            R.string.home_tweet_failed,
                            Toast.LENGTH_SHORT
                        ).show()
                }
            }
        }
    }

    override fun onItemClick(urls: List<String>) {
        viewModel.onItemClicked(urls)
    }

    override fun onTweet(text: String) {
        viewModel.onTweet(text)
    }

    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }
}
