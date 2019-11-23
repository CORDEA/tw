package jp.cordea.tw.ui.profile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import coil.api.load
import jp.cordea.tw.R
import jp.cordea.tw.ViewModelFactory
import jp.cordea.tw.ViewModelInjectable
import jp.cordea.tw.ui.main.MainActivity
import jp.cordea.tw.viewModel
import kotlinx.android.synthetic.main.profile_fragment.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfileFragment : Fragment(),
    CoroutineScope by MainScope(),
    ViewModelInjectable<ProfileViewModel> {
    @Inject
    override lateinit var viewModelFactory: ViewModelFactory<ProfileViewModel>

    override fun onAttach(context: Context) {
        (requireActivity() as MainActivity)
            .subcomponent
            .profileFragmentSubcomponent()
            .create()
            .inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.profile_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = viewModel()

        launch {
            for (e in viewModel.onModel) {
                image.load(e.imageUrl)
                name.text = e.name
                twitterId.text = e.getId(requireContext())
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }
}
