package io.indrian16.appcent.ui.news

import io.indrian16.appcent.data.model.Article

sealed class NewsListState {

    abstract val dataList: List<Article>
}
data class DefaultState(override val dataList: List<Article>): NewsListState()
data class LoadingState(override val dataList: List<Article>): NewsListState()
data class EmptyState(override val dataList: List<Article>): NewsListState()
data class ErrorState(val errorMessage: String, override val dataList: List<Article>): NewsListState()