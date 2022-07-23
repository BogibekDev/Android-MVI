package dev.bogibek.android_mvi.repository

import dev.bogibek.android_mvi.activity.create.helper.CreateHelper
import dev.bogibek.android_mvi.model.Post


class CreateRepository(private val mainHelper: CreateHelper) {
    suspend fun savePost(post: Post) = mainHelper.savePost(post)
}