package dev.bogibek.android_mvi.activity.create.helper

import dev.bogibek.android_mvi.model.Post
import dev.bogibek.android_mvi.network.service.PostService

class CreateHelperImpl(private val postService: PostService) : CreateHelper {
    override suspend fun savePost(post: Post) = postService.savePost(post)

}