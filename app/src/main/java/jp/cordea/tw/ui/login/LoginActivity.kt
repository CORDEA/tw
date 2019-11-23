package jp.cordea.tw.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import jp.cordea.tw.*
import jp.cordea.tw.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
class LoginActivity : AppCompatActivity(),
    ViewModelInjectable<LoginViewModel>,
    CoroutineScope by MainScope() {
    @Inject
    override lateinit var viewModelFactory: ViewModelFactory<LoginViewModel>

    private val viewModel by lazy { viewModel() }

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as App)
            .appComponent
            .loginActivitySubcomponentFactory()
            .create()
            .inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setSupportActionBar(toolbar)

        val intent = Intent(this, MainActivity::class.java)
        launch {
            viewModel.onLoggedIn
                .consumeEach { startActivity(intent) }
        }

        loginButton.callback = viewModel.loginCallback
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        loginButton.onActivityResult(requestCode, resultCode, data)
    }
}
