package jp.cordea.tw.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import jp.cordea.tw.App
import jp.cordea.tw.R
import jp.cordea.tw.ViewModelFactory
import jp.cordea.tw.ViewModelInjectable
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), ViewModelInjectable<MainViewModel> {
    @Inject
    override lateinit var viewModelFactory: ViewModelFactory<MainViewModel>

    lateinit var subcomponent: MainActivitySubcomponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subcomponent = (application as App)
            .appComponent
            .mainActivitySubcomponentFactory()
            .create()
        subcomponent.inject(this)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_dashboard,
                R.id.navigation_profile
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
}
