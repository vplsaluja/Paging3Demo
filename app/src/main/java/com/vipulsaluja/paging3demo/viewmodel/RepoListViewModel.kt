package com.vipulsaluja.paging3demo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.vipulsaluja.paging3demo.repository.RepoListRepository
import com.vipulsaluja.paging3demo.data.models.Repository
import com.vipulsaluja.paging3demo.network.RetrofitClient
import kotlinx.coroutines.flow.Flow


/**
 * Created by Vipul Saluja on 14-10-2021.
 */
class RepoListViewModel() : ViewModel() {
    private var repository: RepoListRepository = RepoListRepository(RetrofitClient.getNetworkApi())
    private var currentUserName: String? = null
    private var currentSearchResult: Flow<PagingData<Repository>>? = null

    fun searchRepos(username: String): Flow<PagingData<Repository>> {
        val lastResult = currentSearchResult
        if (username == currentUserName && lastResult != null) {
            return lastResult
        }
        currentUserName = username
        val newResult = repository.fetchRepos(username)
            .cachedIn(viewModelScope)
        currentSearchResult = newResult
        return newResult
    }
}