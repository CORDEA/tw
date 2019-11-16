package jp.cordea.tw

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

class LoginActivity : AppCompatActivity(), ViewModelInjectable<LoginViewModel> {
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
    }
}
