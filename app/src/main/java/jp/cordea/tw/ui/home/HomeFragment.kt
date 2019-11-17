package jp.cordea.tw.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import jp.cordea.tw.*
import javax.inject.Inject

class HomeFragment : Fragment(), ViewModelInjectable<HomeViewModel> {
    @Inject
    override lateinit var viewModelFactory: ViewModelFactory<HomeViewModel>

    private val viewModel by lazy { viewModel() }

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
}
