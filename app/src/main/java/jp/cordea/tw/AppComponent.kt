package jp.cordea.tw

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import jp.cordea.tw.ui.login.LoginActivitySubcomponent
import jp.cordea.tw.ui.main.MainActivitySubcomponent
import javax.inject.Singleton

@Singleton
@Component
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): AppComponent
    }

    fun loginActivitySubcomponentFactory(): LoginActivitySubcomponent.Factory
    fun mainActivitySubcomponentFactory(): MainActivitySubcomponent.Factory
}
