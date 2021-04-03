package io.indrian16.appcent.di.builder

import dagger.Module
import dagger.android.ContributesAndroidInjector
import io.indrian16.appcent.ui.detail.DetailArticleActivity
import io.indrian16.appcent.ui.main.MainActivity
import io.indrian16.appcent.ui.search.SearchActivity
import io.indrian16.appcent.ui.splash.SplashActivity

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector
    abstract fun contributeSplashScreen(): SplashActivity

    @ContributesAndroidInjector(modules = [FragmentBuilder::class])
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun contributeSearchActivity(): SearchActivity

    @ContributesAndroidInjector
    abstract fun contributeDetailActivity(): DetailArticleActivity
}