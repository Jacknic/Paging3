package com.jacknic.jetpack.paging.ui

import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jacknic.jetpack.paging.R
import kotlinx.android.synthetic.main.loading_state.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest


class MainActivity : AppCompatActivity() {

    private val vm by viewModels<MainViewModel>()
    private val articleListAdapter = ArticleListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val rvArticleList = findViewById<RecyclerView>(R.id.rvArticleList)
        val btnPageNum = findViewById<Button>(R.id.btnPageNum)
        val withLoadStateAdapter = articleListAdapter.withLoadStateHeaderAndFooter(
            LoadStateAdapter { articleListAdapter.retry() },
            LoadStateAdapter { articleListAdapter.retry() }
        )
        btnRetry.setOnClickListener { articleListAdapter.refresh() }
        rvArticleList.adapter = withLoadStateAdapter
        rvArticleList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                (rvArticleList.layoutManager as? LinearLayoutManager)?.apply {
                    findFirstCompletelyVisibleItemPosition()
                    val pageNum = findLastVisibleItemPosition() / 20
                    btnPageNum.text = "$pageNum"
//                    println("当前总数：${itemCount} -> $pageNum")
                }
            }
        })
        btnPageNum.setOnClickListener {
            rvArticleList.scrollToPosition(20 * 400)
        }
        articleListAdapter.addLoadStateListener {
            with(it.source) {
                rvArticleList.isVisible = refresh is LoadState.NotLoading
                llLoading.isVisible = refresh is LoadState.Loading
                btnRetry.isVisible = refresh is LoadState.Error
            }
        }
        @OptIn(ExperimentalPagingApi::class, ExperimentalCoroutinesApi::class)
        lifecycleScope.launchWhenCreated {
            vm.articlePageFlow().collectLatest {
                articleListAdapter.submitData(it)
            }
        }
    }
}
