package io.indrian16.appcent

import android.app.Activity
import android.app.Application
import com.github.ajalt.timberkt.Timber
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import io.indrian16.appcent.di.component.DaggerAppComponent
import javax.inject.Inject

class Appcent : Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()

        DaggerAppComponent.builder()
            .application(this)
            .build()
            .inject(this)

        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }

    override fun activityInjector(): AndroidInjector<Activity> = dispatchingAndroidInjector
}