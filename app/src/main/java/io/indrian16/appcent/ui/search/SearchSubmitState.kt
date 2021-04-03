package io.indrian16.appcent.ui.search

import io.indrian16.appcent.data.model.Article

sealed class SearchSubmitState

data class GetDataState(val dataList: List<Article>): SearchSubmitState()
data class ErrorState(val errorMessage: String) : SearchSubmitState()
object LoadingState : SearchSubmitState()
object EmptyState : SearchSubmitState()