package io.indrian16.appcent.data.repository

import android.content.Context
import io.indrian16.appcent.data.model.Article
import io.indrian16.appcent.util.isConnAvailable
import io.reactivex.Single
import javax.inject.Inject

class Repository @Inject constructor(private val context: Context,
                                     private val localRepository: LocalRepository,
                                     private val remoteRepository: RemoteRepository) {

    fun getTopHeadlines(category: String): Single<List<Article>> {

        return if (context.isConnAvailable()) {

            remoteRepository.getTopHeadlines(category)
                .doOnNext { localRepository.saveToLocal(it) }
                .toList()

        } else {

            localRepository.getTopHeadlineFromLocal()
        }
    }

    fun getEverything(query: String): Single<List<Article>> {

        return remoteRepository.getEverything(query)
    }

    fun getFavoriteIsExist(url: String) = localRepository.getFavoriteIsExist(url)
}