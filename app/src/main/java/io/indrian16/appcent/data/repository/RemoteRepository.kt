package io.indrian16.appcent.data.repository

import io.indrian16.appcent.data.api.ArticleModel
import io.indrian16.appcent.data.api.NewsService
import io.indrian16.appcent.data.model.Article
import io.indrian16.appcent.util.AppConstant
import io.reactivex.Observable
import io.reactivex.Single
import java.util.*
import javax.inject.Inject

class RemoteRepository @Inject constructor(private val newsService: NewsService) {

    fun getTopHeadlines(category: String): Observable<Article> {

        return newsService.getTopHeadlines( page = AppConstant.PAGE)
            .toObservable()
            .flatMapIterable { it.results }
            .map { articleModelToArticle(it) }
    }

    fun getEverything(query:String): Single<List<Article>> {

        return newsService.getEverything(query = query)
            .toObservable()
            .flatMapIterable { it.results }
            .map { articleModelToArticle(it) }
            .toList()
    }

    private fun articleModelToArticle(model: ArticleModel): Article {

        return Article(
            saveTime = Date(System.currentTimeMillis()),
            slug = model.slug,
            name = model.name,
            description = model.description,
            released = model.released,
            background_image = model.background_image,
            rating = model.rating

        )
    }
}