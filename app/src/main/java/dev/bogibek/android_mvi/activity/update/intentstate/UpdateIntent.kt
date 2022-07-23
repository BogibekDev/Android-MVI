package dev.bogibek.android_mvi.activity.update.intentstate

import dev.bogibek.android_mvi.model.Post


sealed class UpdateIntent {
    data class UpdatePost(val post: Post, val id: Int) : UpdateIntent()
}