package dev.bogibek.android_mvi.activity.create.helper

import dev.bogibek.android_mvi.activity.update.helper.UpdateHelper
import dev.bogibek.android_mvi.model.Post
import dev.bogibek.android_mvi.network.service.PostService

class UpdateHelperImpl(private val postService: PostService) : UpdateHelper {
    override suspend fun updatePost(post: Post,id:Int) = postService.updatePost(post,id)

}