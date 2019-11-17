package jp.cordea.tw

import dagger.Subcomponent
import jp.cordea.tw.ui.home.HomeFragmentSubcomponent

@Subcomponent
interface MainActivitySubcomponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): MainActivitySubcomponent
    }

    fun inject(activity: MainActivity): MainActivity

    fun homeFragmentSubcomponent(): HomeFragmentSubcomponent.Factory
}
