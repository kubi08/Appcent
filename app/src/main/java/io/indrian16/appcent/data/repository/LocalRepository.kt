package io.indrian16.appcent.data.repository

import com.github.ajalt.timberkt.d
import io.indrian16.appcent.data.db.ArticleDao
import io.indrian16.appcent.data.db.FavoriteDao
import io.indrian16.appcent.data.model.Article
import io.indrian16.appcent.data.model.Favorite
import javax.inject.Inject

class LocalRepository @Inject constructor(private val articleDao: ArticleDao,
                                          private val favoriteDao: FavoriteDao) {

    fun getTopHeadlineFromLocal() = articleDao.getTopHeadlines()

    fun saveToLocal(article: Article?) {

        if (article != null) {

            d { "Insert article --> ${article.name}" }
            articleDao.insertArticle(article)
        } else {

            d { "article is null" }
        }
    }

    fun getFavoriteList() = favoriteDao.getFavorites()

    fun addFavorite(favorite: Favorite) = favoriteDao.insertFavorite(favorite)

    fun deleteFavorite(url: String) = favoriteDao.deleteFavorite(url)

    fun getFavoriteIsExist(url: String) = favoriteDao.getFavoriteIsExist(url)
}