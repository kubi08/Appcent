package io.indrian16.appcent.ui.news.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import io.indrian16.appcent.R
import io.indrian16.appcent.data.model.Article
import io.indrian16.appcent.di.module.GlideApp
import kotlinx.android.synthetic.main.article_item.view.*

class RvNewsArticle(private val listener
                    : OnNewsArticleOnClickListener)
    : RecyclerView.Adapter<RvNewsArticle.NewsHolder>(), Filterable {

    private var articleList: List<Article> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.article_item, parent, false)
        return NewsHolder(view)
    }

    override fun getItemCount(): Int = articleList.size

    override fun onBindViewHolder(holder: NewsHolder, position: Int) = holder.bind(articleList[position])

    fun add(dataList: List<Article>) {

        articleList = dataList
        notifyDataSetChanged()
    }

    fun clear() {

        articleList = emptyList()
        notifyDataSetChanged()
    }

    inner class NewsHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(article: Article) {

            itemView.apply {

                tvTitle.text = article.name
                tvRating.text = article.rating.toString()+"  -"
                tvReleased.text = article.released
                GlideApp.with(this).load(article.background_image).into(imgArticle)


            }.setOnClickListener { listener.onClickNews(article) }
        }
    }

    interface OnNewsArticleOnClickListener {

        fun onClickNews(article: Article)

        fun onClickShare(url: String)

        fun onClickBookmark(article: Article)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    articleList
                } else {
                    val resultList = ArrayList<Article>()
                    for (row in articleList) {
                        if (row.slug.toLowerCase().contains(constraint.toString().toLowerCase())) {
                            resultList.add(row)
                        }
                    }
                    articleList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = articleList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                articleList = results?.values as ArrayList<Article>
                notifyDataSetChanged()
            }
        }
    }

}