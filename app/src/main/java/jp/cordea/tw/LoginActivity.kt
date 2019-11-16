package jp.cordea.tw

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import javax.inject.Inject

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

        loginButton.callback = viewModel.loginCallback
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        loginButton.onActivityResult(requestCode, resultCode, data)
    }
}
