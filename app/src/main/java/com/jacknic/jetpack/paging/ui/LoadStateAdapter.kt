package com.jacknic.jetpack.paging.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jacknic.jetpack.paging.databinding.LoadingStateBinding

/**
 * 数据加载状态显示
 *
 * @author Jacknic
 */
class LoadStateAdapter(private val retry: () -> Unit) : LoadStateAdapter<LoadStateViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): LoadStateViewHolder {
        return LoadStateViewHolder.create(parent, retry)
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        println("底部：$loadState")
        holder.binding.apply {
            llLoading.isVisible = loadState is LoadState.Loading
            btnRetry.isVisible = loadState !is LoadState.Loading
        }
    }
}

class LoadStateViewHolder(val binding: LoadingStateBinding, retry: () -> Unit) :
    RecyclerView.ViewHolder(binding.root) {

    init {
        binding.btnRetry.setOnClickListener { retry() }
    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): LoadStateViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = LoadingStateBinding.inflate(inflater, parent, false)
            return LoadStateViewHolder(binding, retry)
        }
    }
}

