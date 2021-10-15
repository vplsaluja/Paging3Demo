package com.vipulsaluja.paging3demo.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.vipulsaluja.paging3demo.R


/**
 * Created by Vipul Saluja on 14-10-2021.
 */
class RepoLoadStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<RepoLoadStateAdapter.LoadStateViewHolder>() {

    class LoadStateViewHolder(itemView: View, retry: () -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        val errorMsg: TextView = itemView.findViewById(R.id.txtErrorMsg)
        val progressBar: ProgressBar = itemView.findViewById(R.id.progressBar)
        val retryButton: Button = itemView.findViewById(R.id.btnRetry)

        init {
            retryButton.setOnClickListener { retry() }
        }
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        if (loadState is LoadState.Error)
            holder.errorMsg.text = loadState.error.localizedMessage
        holder.progressBar.isVisible = loadState is LoadState.Loading
        holder.retryButton.isVisible = loadState is LoadState.Error
        holder.errorMsg.isVisible = loadState is LoadState.Error
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_loading_footer, parent, false)
        return LoadStateViewHolder(view, retry)
    }
}