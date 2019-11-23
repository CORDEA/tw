package jp.cordea.tw.ui.profile

import dagger.Subcomponent

@Subcomponent
interface ProfileFragmentSubcomponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): ProfileFragmentSubcomponent
    }

    fun inject(fragment: ProfileFragment): ProfileFragment
}
