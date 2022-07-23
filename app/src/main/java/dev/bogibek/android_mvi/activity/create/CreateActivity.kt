package dev.bogibek.android_mvi.activity.create

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import dev.bogibek.android_mvi.activity.create.helper.CreateHelperImpl
import dev.bogibek.android_mvi.activity.create.intentstate.CreateIntent
import dev.bogibek.android_mvi.activity.create.intentstate.CreateState
import dev.bogibek.android_mvi.activity.create.viewmodel.CreateViewModel
import dev.bogibek.android_mvi.activity.create.viewmodel.CreateViewModelFactory
import dev.bogibek.android_mvi.databinding.ActivityCreateBinding
import dev.bogibek.android_mvi.model.Post
import dev.bogibek.android_mvi.network.RetrofitBuilder
import kotlinx.coroutines.launch

class CreateActivity : AppCompatActivity() {

    private val binding by lazy { ActivityCreateBinding.inflate(layoutInflater) }
    private lateinit var viewModel: CreateViewModel
    private lateinit var recyclerView: RecyclerView
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
                    is CreateState.Init -> {
                        Log.d("CreateActivity", "Init")
                    }

                    is CreateState.Loading -> {
                        Log.d("CreateActivity", "Loading")
                    }
                    is CreateState.SavePost -> {
                        Log.d("CreateActivity", "SavePOst: ${it}")
                        finish()
                    }

                    is CreateState.Error -> {
                        Log.d("CreateActivity", "Error $it")
                    }
                }
            }
        }
    }

    private fun initViews() {
        setupViewModel()
        binding.apply {
            binding.btnSave.setOnClickListener {
                val title = etTitle.text.toString()
                val body = etBody.text.toString()
                val post = Post(1, 2, title, body)
                Log.d("CreateActivity", post.toString())
                intentSavePost(post)
            }

        }
    }

    private fun setupViewModel() {
        val factory = CreateViewModelFactory(CreateHelperImpl(RetrofitBuilder.POST_SERVICE))
        viewModel = ViewModelProvider(this, factory).get(CreateViewModel::class.java)
    }

    private fun intentSavePost(post: Post) {
        lifecycleScope.launch {
            viewModel.mainIntent.send(CreateIntent.SavePost(post))
        }

    }
}