package dev.bogibek.android_mvi.activity.update

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import dev.bogibek.android_mvi.activity.create.helper.UpdateHelperImpl
import dev.bogibek.android_mvi.activity.create.viewmodel.UpdateViewModel
import dev.bogibek.android_mvi.activity.create.viewmodel.UpdateViewModelFactory
import dev.bogibek.android_mvi.activity.update.intentstate.UpdateIntent
import dev.bogibek.android_mvi.activity.update.intentstate.UpdateState
import dev.bogibek.android_mvi.databinding.ActivityUpdateBinding
import dev.bogibek.android_mvi.model.Post
import dev.bogibek.android_mvi.network.RetrofitBuilder
import kotlinx.coroutines.launch

class UpdateActivity : AppCompatActivity() {
    private val binding by lazy { ActivityUpdateBinding.inflate(layoutInflater) }
    private lateinit var viewModel: UpdateViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initViews()
        observeViewModel()
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.state.collect {
                when (it) {
                    is UpdateState.Init -> {
                        Log.d("UpdateActivity", "Init")
                    }

                    is UpdateState.Loading -> {
                        Log.d("UpdateActivity", "Loading")
                    }
                    is UpdateState.UpdatePost -> {
                        Log.d("UpdateActivity", "update: ${it}")
                        finish()
                    }

                    is UpdateState.Error -> {
                        Log.d("UpdateActivity", "Error $it")
                    }
                }
            }
        }
    }

    private fun initViews() {
        val post = intent.getSerializableExtra("post") as Post
        setupViewModel()
        binding.apply {
            etBody.setText(post.body)
            etTitle.setText(post.title)
            binding.btnUpdate.setOnClickListener {
                val title = etTitle.text.toString()
                val body = etBody.text.toString()

                post.title = title
                post.body = body
                Log.d("CreateActivity", post.toString())
                intentUpdatePost(post)
            }

        }
    }

    private fun setupViewModel() {
        val factory = UpdateViewModelFactory(UpdateHelperImpl(RetrofitBuilder.POST_SERVICE))
        viewModel = ViewModelProvider(this, factory).get(UpdateViewModel::class.java)
    }

    private fun intentUpdatePost(post: Post) {
        lifecycleScope.launch {
            viewModel.updateIntent.send(UpdateIntent.UpdatePost(post, post.id))
        }
    }
}