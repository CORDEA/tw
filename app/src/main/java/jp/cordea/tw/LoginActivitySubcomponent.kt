package jp.cordea.tw

import dagger.Subcomponent

@Subcomponent
interface LoginActivitySubcomponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): LoginActivitySubcomponent
    }

    fun inject(activity: LoginActivity): LoginActivity
}
