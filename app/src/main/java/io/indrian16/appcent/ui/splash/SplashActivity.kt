package io.indrian16.appcent.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import io.indrian16.appcent.R
import io.indrian16.appcent.ui.base.BaseActivity
import io.indrian16.appcent.ui.main.MainActivity
import io.indrian16.appcent.util.AppConstant

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({ openMainActivity() }, AppConstant.LENGTH_SPLASH_SCREEN)
    }

    private fun openMainActivity() {

        startActivity(Intent(this, MainActivity::class.java))
        this.finish()
    }
}
