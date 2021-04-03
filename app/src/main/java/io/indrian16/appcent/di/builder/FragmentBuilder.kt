package io.indrian16.appcent.di.builder

import dagger.Module
import dagger.android.ContributesAndroidInjector
import io.indrian16.appcent.ui.favorite.FavoriteFragment
import io.indrian16.appcent.ui.news.NewsFragment

@Module
abstract class FragmentBuilder {

    @ContributesAndroidInjector
    abstract fun contributeNewsFragment(): NewsFragment

    @ContributesAndroidInjector
    abstract fun contributeFavoriteFragment(): FavoriteFragment
}