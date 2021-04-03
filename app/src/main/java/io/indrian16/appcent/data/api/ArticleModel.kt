package io.indrian16.appcent.data.api

data class ArticleModel(
    val slug: String,
    val name: String,
    val released: String,
    val description:String?,
    val background_image: String,
    val rating: Double
)