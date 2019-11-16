package jp.cordea.tw

import dagger.Subcomponent

@Subcomponent
interface MainActivitySubcomponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): MainActivitySubcomponent
    }

    fun inject(activity: MainActivity): MainActivity
}
