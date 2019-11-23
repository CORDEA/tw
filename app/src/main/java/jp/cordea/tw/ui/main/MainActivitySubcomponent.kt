package jp.cordea.tw.ui.main

import dagger.Subcomponent
import jp.cordea.tw.ui.home.HomeFragmentSubcomponent
import jp.cordea.tw.ui.profile.ProfileFragmentSubcomponent

@Subcomponent
interface MainActivitySubcomponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): MainActivitySubcomponent
    }

    fun inject(activity: MainActivity): MainActivity

    fun homeFragmentSubcomponent(): HomeFragmentSubcomponent.Factory
    fun profileFragmentSubcomponent(): ProfileFragmentSubcomponent.Factory
}
