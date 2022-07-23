package dev.bogibek.android_mvi.activity.update.intentstate

import dev.bogibek.android_mvi.model.Post

sealed class UpdateState {
    object Init : UpdateState()
    object Loading : UpdateState()

    data class UpdatePost(val post: Post) : UpdateState()

    data class Error(val error: String?) : UpdateState()
}