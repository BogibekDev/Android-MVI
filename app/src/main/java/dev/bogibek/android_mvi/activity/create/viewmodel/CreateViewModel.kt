package dev.bogibek.android_mvi.activity.create.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.bogibek.android_mvi.activity.create.intentstate.CreateIntent
import dev.bogibek.android_mvi.activity.create.intentstate.CreateState
import dev.bogibek.android_mvi.activity.update.intentstate.UpdateIntent
import dev.bogibek.android_mvi.model.Post
import dev.bogibek.android_mvi.repository.CreateRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class CreateViewModel(private val repository: CreateRepository) : ViewModel() {

    val mainIntent = Channel<CreateIntent>(Channel.UNLIMITED)
    private val _state = MutableStateFlow<CreateState>(CreateState.Init)
    val state: StateFlow<CreateState> get() = _state

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            mainIntent.consumeAsFlow().collect {
                when (it) {
                    is CreateIntent.SavePost -> apiAllPost(it.post)
                }
            }
        }
    }

    fun apiAllPost(post: Post) {
        viewModelScope.launch {
            _state.value = CreateState.Loading
            _state.value = try {
                CreateState.SavePost(repository.savePost(post))
            } catch (e: Exception) {
                CreateState.Error(e.localizedMessage)
            }
        }
    }


}