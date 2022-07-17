package com.jtm.blog.data.service

import com.jtm.blog.core.usecase.repository.PostRepository
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock

@RunWith(MockitoJUnitRunner::class)
class PostServiceTest {

    private val postRepository: PostRepository = mock()
    private val postService = PostService(postRepository)

    fun addPost_shouldThrowFound_whenSearching() {}

    fun addPost_shouldSucceed() {}

    fun updatePost_shouldThrowNotFound_whenSearching() {}

    fun updatePost_shouldSucceed() {}

    fun getPost_shouldThrowNotFound_whenSearching() {}

    fun getPost_shouldSucceed() {}

    fun getPosts() {}

    fun removePost_shouldThrowNotFound_whenSearching() {}

    fun removePost_shouldSucceed() {}
}