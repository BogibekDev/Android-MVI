package dev.bogibek.android_mvi.network.service

import dev.bogibek.android_mvi.model.Post
import retrofit2.http.*

interface PostService {

    @GET("posts")
    suspend fun allPosts(): ArrayList<Post>

    @DELETE("posts/{id}")
    suspend fun deletePost(@Path("id") id: Int): Post

    @Headers(
        "Content-type:application/json"
    )
    @POST("posts")
    suspend fun savePost(@Body post: Post): Post

    @Headers(
        "Content-type:application/json"
    )
    @PUT("posts/{id}")
    suspend fun updatePost(@Body post: Post, @Path("id") id: Int): Post
}