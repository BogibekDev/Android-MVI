package dev.bogibek.android_mvi.activity.create.intentstate
import dev.bogibek.android_mvi.model.Post

sealed class CreateState {
    object Init : CreateState()
    object Loading : CreateState()

    data class SavePost(val post: Post) : CreateState()

    data class Error(val error: String?) : CreateState()
}