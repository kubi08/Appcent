package io.indrian16.appcent.ui.favorite.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.indrian16.appcent.R
import io.indrian16.appcent.data.model.Favorite
import io.indrian16.appcent.di.module.GlideApp
import kotlinx.android.synthetic.main.article_item.view.*
import kotlinx.android.synthetic.main.favorite_item.view.*
import kotlinx.android.synthetic.main.favorite_item.view.imgArticle
import kotlinx.android.synthetic.main.favorite_item.view.tvRating
import kotlinx.android.synthetic.main.favorite_item.view.tvReleased
import kotlinx.android.synthetic.main.favorite_item.view.tvTitle

class RvFavorite(private val listener: OnFavoriteClickListener) : RecyclerView.Adapter<RvFavorite.FavoriteHolder>() {

    private var favoriteList: List<Favorite> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.favorite_item, parent, false)
        return FavoriteHolder(view)
    }

    override fun getItemCount(): Int = favoriteList.size

    override fun onBindViewHolder(holder: FavoriteHolder, position: Int) = holder.bind(favoriteList[position])

    fun add(dataList: List<Favorite>) {

        favoriteList = dataList
        notifyDataSetChanged()
    }

    fun clear() {

        favoriteList = emptyList()
    }

    inner class FavoriteHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(favorite: Favorite) {

            itemView.apply {

                tvTitle.text = favorite.name
                tvRating.text = favorite.rating.toString()+"  -"
                tvReleased.text = favorite.released
                GlideApp.with(this).load(favorite.background_image).into(imgArticle)

            }.setOnClickListener { listener.onClickItem(favorite) }
        }
    }

    interface OnFavoriteClickListener {

        fun onClickItem(favorite: Favorite)

        fun onClickShare(url: String)
    }
}