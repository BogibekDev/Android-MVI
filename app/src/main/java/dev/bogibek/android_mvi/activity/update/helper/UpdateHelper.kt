package dev.bogibek.android_mvi.activity.update.helper

import dev.bogibek.android_mvi.model.Post

interface UpdateHelper {
    suspend fun updatePost(post: Post,id:Int): Post
}