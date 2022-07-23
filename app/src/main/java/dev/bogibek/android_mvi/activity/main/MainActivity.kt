package dev.bogibek.android_mvi.activity.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import dev.bogibek.android_mvi.R
import dev.bogibek.android_mvi.activity.create.CreateActivity
import dev.bogibek.android_mvi.activity.main.helper.MainHelperImpl
import dev.bogibek.android_mvi.activity.main.intentstate.MainIntent
import dev.bogibek.android_mvi.activity.main.intentstate.MainState
import dev.bogibek.android_mvi.activity.main.viewmodel.MainViewModel
import dev.bogibek.android_mvi.activity.main.viewmodel.MainViewModelFactory
import dev.bogibek.android_mvi.adapter.PostAdapter
import dev.bogibek.android_mvi.model.Post
import dev.bogibek.android_mvi.network.RetrofitBuilder
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        observeViewModel()
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.state.collect {
                when (it) {
                    is MainState.Init -> {
                        Log.d("MainActivity", "Init")
                    }

                    is MainState.Loading -> {
                        Log.d("MainActivity", "Loading")
                    }
                    is MainState.AllPosts -> {
                        refreshAdapter(it.posts)
                        Log.d("MainActivity", "AllPosts: ${it.posts.size}")
                    }
                    is MainState.DeletePost -> {
                        intentAllPosts()
                        Log.d("MainActivity", "Delete ${it.post}")
                    }
                    is MainState.Error -> {
                        Log.d("MainActivity", "Error $it")
                    }
                }
            }
        }
    }

    private fun initViews() {
        setupViewModel()
        recyclerView = findViewById(R.id.recyclerView)
        intentAllPosts()
        findViewById<ImageView>(R.id.iv_add).setOnClickListener {
            val intent = Intent(this, CreateActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupViewModel() {
        val factory = MainViewModelFactory(MainHelperImpl(RetrofitBuilder.POST_SERVICE))
        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)
    }

    private fun intentAllPosts() {
        lifecycleScope.launch {
            viewModel.mainIntent.send(MainIntent.AllPosts)
        }
    }

    fun intentDeletePost(id: Int) {
        viewModel.postId = id
        lifecycleScope.launch {
            viewModel.mainIntent.send(MainIntent.DeletePost)
        }
    }

    private fun refreshAdapter(posts: ArrayList<Post>) {
        var adapter = PostAdapter(this, posts)
        recyclerView.adapter = adapter
    }
}