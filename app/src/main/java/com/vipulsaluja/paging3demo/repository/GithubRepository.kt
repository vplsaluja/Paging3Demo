package com.vipulsaluja.paging3demo.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.vipulsaluja.paging3demo.data.source.GithubPagingSource
import com.vipulsaluja.paging3demo.network.NetworkApi


/**
 * Created by Vipul Saluja on 14-10-2021.
 */
class GithubRepository(private val api: NetworkApi) {
    fun getRepos(username: String) = Pager(
        pagingSourceFactory = { GithubPagingSource(api, username) },
        config = PagingConfig(pageSize = 20)
    ).flow
}