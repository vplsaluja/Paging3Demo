package com.vipulsaluja.paging3demo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.vipulsaluja.paging3demo.repository.GithubRepository
import com.vipulsaluja.paging3demo.data.models.Repo
import com.vipulsaluja.paging3demo.network.RetrofitClient
import kotlinx.coroutines.flow.Flow


/**
 * Created by Vipul Saluja on 14-10-2021.
 */
class GithubViewModel() : ViewModel() {
    private var repo: GithubRepository
    private var currentUserName: String? = null
    private var currentSearchResult: Flow<PagingData<Repo>>? = null
    private val retrofitClient = RetrofitClient()

    init {
        repo = GithubRepository(retrofitClient.getNetworkApi())
    }

    fun searchRepos(username: String): Flow<PagingData<Repo>> {
        val lastResult = currentSearchResult
        if (username == currentUserName && lastResult != null) {
            return lastResult
        }
        currentUserName == username
        val newResult = repo.getRepos(username)
            .cachedIn(viewModelScope)
        currentSearchResult = newResult
        return newResult
    }
}