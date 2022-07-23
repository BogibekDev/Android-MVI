package dev.bogibek.android_mvi.activity.create.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.bogibek.android_mvi.activity.update.intentstate.UpdateIntent
import dev.bogibek.android_mvi.activity.update.intentstate.UpdateState
import dev.bogibek.android_mvi.model.Post
import dev.bogibek.android_mvi.repository.UpdateRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class UpdateViewModel(private val repository: UpdateRepository) : ViewModel() {

    val updateIntent = Channel<UpdateIntent>(Channel.UNLIMITED)
    private val _state = MutableStateFlow<UpdateState>(UpdateState.Init)
    val state: StateFlow<UpdateState> get() = _state


    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            updateIntent.consumeAsFlow().collect {
                when (it) {
                    is UpdateIntent.UpdatePost -> updatePost(it.post, it.id)
                }
            }
        }
    }

    private fun updatePost(post: Post, id: Int) {
        viewModelScope.launch {
            _state.value = UpdateState.Loading
            _state.value = try {
                UpdateState.UpdatePost(repository.updatePost(post, id))
            } catch (e: Exception) {
                UpdateState.Error(e.localizedMessage)
            }
        }
    }


}