package dev.bogibek.android_mvi.repository

import dev.bogibek.android_mvi.activity.update.helper.UpdateHelper
import dev.bogibek.android_mvi.model.Post


class UpdateRepository(private val mainHelper: UpdateHelper) {
    suspend fun updatePost(post: Post,id:Int) = mainHelper.updatePost(post,id)
}