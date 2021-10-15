package com.vipulsaluja.paging3demo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vipulsaluja.paging3demo.R
import com.vipulsaluja.paging3demo.ui.adapter.RepoListAdapter
import com.vipulsaluja.paging3demo.ui.adapter.RepoLoadStateAdapter
import com.vipulsaluja.paging3demo.viewmodel.GithubViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private var recyclerView: RecyclerView? = null

    private var searchJob: Job? = null

    private var repoListAdapter: RepoListAdapter? = null

    private var viewModel: GithubViewModel? = null// by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(GithubViewModel::class.java)
        repoListAdapter = RepoListAdapter()
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView?.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        recyclerView?.adapter = repoListAdapter?.withLoadStateHeaderAndFooter(
            header = RepoLoadStateAdapter(retry = { search("google") }),
            footer = RepoLoadStateAdapter(retry = { search("google") })
        )
        recyclerView?.layoutManager = LinearLayoutManager(this)

        search("google")
    }

    private fun search(username: String) {
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            viewModel?.searchRepos(username)?.collect {
                repoListAdapter?.submitData(it)
            }
        }
    }
}