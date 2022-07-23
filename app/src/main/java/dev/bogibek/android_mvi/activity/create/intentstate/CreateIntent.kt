package dev.bogibek.android_mvi.activity.create.intentstate

import dev.bogibek.android_mvi.model.Post


sealed class CreateIntent {
    data class SavePost(val post: Post) : CreateIntent()
}