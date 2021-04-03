package io.indrian16.appcent.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import io.indrian16.appcent.data.api.NewsService
import io.indrian16.appcent.data.db.ArticleDao
import io.indrian16.appcent.data.db.FavoriteDao
import io.indrian16.appcent.data.repository.LocalRepository
import io.indrian16.appcent.data.repository.RemoteRepository
import io.indrian16.appcent.data.repository.Repository
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    fun provideLocalRepository(articleDao: ArticleDao, favoriteDao: FavoriteDao) = LocalRepository(articleDao, favoriteDao)

    @Provides
    fun provideRemoteRepository(newsService: NewsService) = RemoteRepository(newsService)

    @Provides
    @Singleton
    fun provideRepository(context: Context,
                          localRepository: LocalRepository,
                          remoteRepository: RemoteRepository): Repository {

        return Repository(context, localRepository, remoteRepository)
    }
}