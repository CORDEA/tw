package jp.cordea.tw

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get
import javax.inject.Inject
import javax.inject.Provider

class ViewModelFactory<VM : ViewModel> @Inject constructor(
    private val viewModel: Provider<VM>
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = viewModel.get() as T
}

interface ViewModelInjectable<VM : ViewModel> {
    val viewModelFactory: ViewModelFactory<VM>
}

inline fun <T, reified VM> T.viewModel(): VM
        where T : ViewModelInjectable<VM>,
              T : Fragment,
              VM : ViewModel =
    ViewModelProviders.of(this, viewModelFactory).get()

inline fun <T, reified VM> T.viewModel(): VM
        where T : ViewModelInjectable<VM>,
              T : FragmentActivity,
              VM : ViewModel =
    ViewModelProviders.of(this, viewModelFactory).get()
