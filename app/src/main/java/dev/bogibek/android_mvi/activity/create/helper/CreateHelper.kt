package dev.bogibek.android_mvi.activity.create.helper

import dev.bogibek.android_mvi.model.Post

interface CreateHelper {
    suspend fun savePost(post: Post): Post
}