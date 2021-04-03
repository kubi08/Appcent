package io.indrian16.appcent.data.api

data class GetNewsApiResponse(
    val results: List<ArticleModel>,
    val count: Int,
    val next: String,
    val previous: String,
    val description:String
)