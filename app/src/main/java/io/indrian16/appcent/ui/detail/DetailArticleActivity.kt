package io.indrian16.appcent.ui.detail

import android.annotation.SuppressLint
import android.app.Activity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.github.ajalt.timberkt.d
import io.indrian16.appcent.R
import io.indrian16.appcent.customtab.CustomTabActivityHelper
import io.indrian16.appcent.data.model.Article
import io.indrian16.appcent.di.module.GlideApp
import io.indrian16.appcent.ui.base.BaseActivity
import io.indrian16.appcent.util.*
import kotlinx.android.synthetic.main.activity_detail_article.*
import javax.inject.Inject

class DetailArticleActivity : BaseActivity(),
      View.OnClickListener,
      CustomTabActivityHelper.CustomTabFallback {

    companion object {

        const val EXTRA_ARTICLE = "ARTICLE"
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: DetailViewModel

    private lateinit var articleUrl: String
    private lateinit var favoriteItem: MenuItem
    private var currentFavorite = false

    private val detailStateObserver = Observer<DetailState> { state ->

        when (state) {

            is DefaultState -> {

                d { "Default State" }
                setArticle(state.data)
            }

            is ErrorState -> {

                d { "Error State" }
                showToast(state.errorMessage)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_article)

        viewModel = obtainViewModel().apply {

            detailStateLiveData.observe(this@DetailArticleActivity, detailStateObserver)
        }
        val article = intent.getParcelableExtra<Article>(EXTRA_ARTICLE)
        viewModel.receivedArticle(article)

        setupToolbar()
        setListener()
    }

    private fun setupToolbar() {

        setSupportActionBar(detailToolbar).apply {

            title = getString(R.string.read_article_title)
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setArticle(article: Article?) {

        article?.let {

            GlideApp.with(this).load(it.background_image).into(imgDetail)
            tvTitleDetail.text = it.name
            tvContentDetail.text = it.slug ?: AppConstant.NO_CONTENT
            articleUrl = it.slug
        }
    }

    private fun favoriteArticle() {

        if (currentFavorite) {

            viewModel.deleteFavorite()
            showToast(resources.getString(R.string.del_favorite))

        } else {

            viewModel.addFavorite()
            showToast(resources.getString(R.string.add_favorite))
        }
    }

    private fun setListener() {


    }

    @SuppressLint("PrivateResource")
    private fun openChromeTab() {

        val uri = articleUrl.toUri()
        val intent = CustomTabsIntent.Builder()
            .setShowTitle(true)
            .build()
        CustomTabActivityHelper.openCustomTab(this, intent, uri, this)
    }

    override fun onSupportNavigateUp(): Boolean {

        finish()
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {

        viewModel.checkFavorite(articleUrl)
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.detail_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
    fun likeButtonClick(view: View){
        favoriteArticle()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        return when (item?.itemId) {

            android.R.id.home -> { finish(); true }


            R.id.likebutton -> {

                favoriteArticle()
                true
            }



            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onClick(v: View?) {

        when (v?.id) {

        }
    }

    override fun openUri(activity: Activity?, uri: Uri?) {

        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.detailStateLiveData.removeObserver(detailStateObserver)
    }



    private fun obtainViewModel() = obtainViewModel(viewModelFactory, DetailViewModel::class.java)
}
