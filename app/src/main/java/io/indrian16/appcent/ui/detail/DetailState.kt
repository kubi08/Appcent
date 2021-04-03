package io.indrian16.appcent.ui.detail

import io.indrian16.appcent.data.model.Article

sealed class DetailState {

    abstract val data: Article
}

data class DefaultState(override val data: Article): DetailState()
data class ChangeIconState(override val data: Article, val isFavorite: Boolean): DetailState()
data class ErrorState(override val data: Article, val errorMessage: String): DetailState()