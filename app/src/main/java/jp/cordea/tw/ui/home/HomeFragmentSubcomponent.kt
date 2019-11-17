package jp.cordea.tw.ui.home

import dagger.Subcomponent

@Subcomponent
interface HomeFragmentSubcomponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): HomeFragmentSubcomponent
    }

    fun inject(fragment: HomeFragment): HomeFragment
}
