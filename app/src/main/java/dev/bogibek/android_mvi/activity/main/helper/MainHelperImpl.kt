package dev.bogibek.android_mvi.activity.main.helper

import dev.bogibek.android_mvi.model.Post
import dev.bogibek.android_mvi.network.service.PostService

class MainHelperImpl(private val postService: PostService) : MainHelper {
    override suspend fun allPosts(): ArrayList<Post> {
        return postService.allPosts()
    }

    override suspend fun deletePost(id: Int): Post {
        return postService.deletePost(id)
    }
}