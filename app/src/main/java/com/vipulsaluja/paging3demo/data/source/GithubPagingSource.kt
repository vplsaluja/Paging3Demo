package com.vipulsaluja.paging3demo.data.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.vipulsaluja.paging3demo.data.models.Repository
import com.vipulsaluja.paging3demo.network.NetworkApi
import java.lang.Exception


/**
 * Created by Vipul Saluja on 14-10-2021.
 */


private const val FIRST_PAGE = 1;

class GithubPagingSource(
    private val api: NetworkApi,
    private val userName: String
) : PagingSource<Int, Repository>() {

    override fun getRefreshKey(state: PagingState<Int, Repository>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Repository> {
        return try {
            val page = params.key ?: FIRST_PAGE
            val response = api.fetchRepos(userName, page, params.loadSize)
            LoadResult.Page(
                data = response,
                prevKey = if (page == FIRST_PAGE) null else page - 1,
                nextKey = if (response.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}