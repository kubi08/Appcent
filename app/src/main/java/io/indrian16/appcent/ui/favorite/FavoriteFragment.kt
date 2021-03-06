package io.indrian16.appcent.ui.favorite

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.github.ajalt.timberkt.d

import io.indrian16.appcent.R
import io.indrian16.appcent.data.model.Article
import io.indrian16.appcent.data.model.Favorite
import io.indrian16.appcent.ui.base.BaseFragment
import io.indrian16.appcent.ui.detail.DetailArticleActivity
import io.indrian16.appcent.ui.favorite.adapter.RvFavorite
import io.indrian16.appcent.util.CommnonUtil
import io.indrian16.appcent.util.obtainViewModel
import io.indrian16.appcent.util.toGone
import io.indrian16.appcent.util.toVisible
import kotlinx.android.synthetic.main.fragment_favorite.*
import javax.inject.Inject

class FavoriteFragment : BaseFragment(), SwipeRefreshLayout.OnRefreshListener, RvFavorite.OnFavoriteClickListener {

    companion object {

        fun newInstance() = FavoriteFragment()
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: FavoriteViewModel

    private val mAdapter = RvFavorite(this)

    private val favoriteListStateObserver = Observer<FavoriteListState> { state ->

        when (state) {

            is DefaultState -> {

                d { "Default State" }
                defaultLayoutState()
                favoriteLayout.isRefreshing = false
                mAdapter.clear()
                mAdapter.add(state.dataList)
            }

            is LoadingState -> {

                defaultLayoutState()
                favoriteLayout.isRefreshing = true
            }

            is EmptyState -> {

                emptyLayoutState()
                mAdapter.clear()
            }

            is ErrorState -> {

                errorLayoutState()
                mAdapter.clear()
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = obtainViewModel().apply {

            favoriteListStateLiveData.observe(this@FavoriteFragment, favoriteListStateObserver)
        }
        viewModel.updateFavorite()
    }

    private fun initView() {

        favoriteLayout.setOnRefreshListener(this)
        btnRefresh.setOnClickListener { onRefresh() }

        rvFavorite.apply {

            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.favoriteListStateLiveData.removeObserver(favoriteListStateObserver)
    }

    override fun onRefresh() {

        mAdapter.clear()
        viewModel.updateFavorite()
    }

    override fun onClickItem(favorite: Favorite) {

        val currentArticle = Article(
            saveTime = favorite.saveTime,
            slug = favorite.slug,
            name = favorite.name,
            description = favorite.description,
            released = favorite.released,
            background_image = favorite.background_image,
            rating = favorite.rating

        )

        val intent = Intent(context, DetailArticleActivity::class.java).apply {

            putExtra(DetailArticleActivity.EXTRA_ARTICLE, currentArticle)
        }
        startActivity(intent)
    }

    override fun onClickShare(url: String) {

        CommnonUtil.shareArticle(activity!!, url)
    }

    override fun onResume() {
        super.onResume()

        viewModel.updateFavorite()
    }

    private fun defaultLayoutState() {

        favoriteLayout.toVisible()
        favoriteLayoutEmpty.toGone()
        favoriteLayoutError.toGone()
    }

    private fun emptyLayoutState() {

        favoriteLayout.toGone()
        favoriteLayoutEmpty.toVisible()
        favoriteLayoutError.toGone()
    }

    private fun errorLayoutState() {

        favoriteLayout.toGone()
        favoriteLayoutEmpty.toGone()
        favoriteLayoutError.toVisible()
    }

    private fun obtainViewModel() = obtainViewModel(viewModelFactory, FavoriteViewModel::class.java)
}