package jp.cordea.tw.ui.profile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import jp.cordea.tw.R
import jp.cordea.tw.ViewModelFactory
import jp.cordea.tw.ViewModelInjectable
import jp.cordea.tw.ui.main.MainActivity
import javax.inject.Inject

class ProfileFragment : Fragment(), ViewModelInjectable<ProfileViewModel> {
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
}
