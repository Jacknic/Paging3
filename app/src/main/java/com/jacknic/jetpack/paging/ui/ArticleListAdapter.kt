package com.jacknic.jetpack.paging.ui

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.core.text.buildSpannedString
import androidx.core.text.color
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.jacknic.jetpack.paging.R
import com.jacknic.jetpack.paging.model.Article

/**
 * 文章列表适配器
 */
class ArticleListAdapter : PagingDataAdapter<Article, ViewHolder>(ARTICLE_COMPARATOR) {

    private enum class ViewType {
        Placeholder,
        Normal,
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        println("创建holder：$viewType")
        val viewHolder = ArticleViewHolder.create(parent)
        if (viewType == ViewType.Placeholder.ordinal) {
            viewHolder.itemView.setBackgroundResource(R.drawable.skeleton)
        }
        return viewHolder
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position) != null) ViewType.Normal.ordinal else {
            ViewType.Placeholder.ordinal
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = getItem(position)
        if (article != null) {
            (holder as ArticleViewHolder).bind(article)
        }
    }

    companion object {
        private val ARTICLE_COMPARATOR = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article) = oldItem == newItem

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean =
                oldItem.id == newItem.id
        }
    }
}

class ArticleViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val title: TextView = view.findViewById(android.R.id.text1)
    private val subtitle: TextView = view.findViewById(android.R.id.text2)

    fun bind(article: Article?) {
        article?.let {
            title.text = HtmlCompat.fromHtml(it.title, HtmlCompat.FROM_HTML_MODE_COMPACT)
            subtitle.text = buildSpannedString {
                color(Color.LTGRAY) { append(it.author) }
                append("   ")
                color(Color.LTGRAY) { append(it.niceDate) }
            }
        }
    }

    companion object {
        fun create(parent: ViewGroup): ArticleViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(android.R.layout.simple_list_item_2, parent, false)
            return ArticleViewHolder(view)
        }
    }
}
