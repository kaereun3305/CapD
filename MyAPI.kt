package com.example.trying

import retrofit2.Call
import retrofit2.http.*


interface MyAPI {
    @POST("/posts/")
    fun post_posts(@Body post: PostItem?): Call<PostItem?>?

    @PATCH("/posts/{pk}/")
    fun patch_posts(@Path("pk") pk: Int, @Body post: PostItem?): Call<PostItem?>?

    @DELETE("/posts/{pk}/")
    fun delete_posts(@Path("pk") pk: Int): Call<PostItem?>?

    @GET("/posts/")
    fun get_posts(): Call<List<PostItem?>?>?

    @GET("/posts/{pk}/")
    fun get_post_pk(@Path("pk") pk: Int): Call<PostItem?>?
}
