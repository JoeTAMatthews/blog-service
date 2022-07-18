package com.jtm.blog.data.service

import com.jtm.blog.core.usecase.exception.post.PostAlreadyFound
import com.jtm.blog.core.usecase.exception.post.PostNotFound
import com.jtm.blog.core.usecase.repository.PostRepository
import com.jtm.blog.core.util.TestUtil
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class PostServiceTest {

    private val postRepository: PostRepository = mock()
    private val postService = PostService(postRepository)

    private val dto = TestUtil.createPostDTO()
    private val created = TestUtil.createPost()

    @Test
    fun addPost_shouldThrowFound_whenSearching() {
        `when`(postRepository.findByName(anyString())).thenReturn(Mono.just(created))

        val returned = postService.addPost(dto)

        verify(postRepository, times(1)).findByName(anyString())
        verifyNoMoreInteractions(postRepository)

        StepVerifier.create(returned)
            .expectError(PostAlreadyFound::class.java)
            .verify()
    }

    @Test
    fun addPost_shouldSucceed() {
        `when`(postRepository.findByName(anyString())).thenReturn(Mono.empty())
        `when`(postRepository.save(anyOrNull())).thenReturn(Mono.just(created))

        val returned = postService.addPost(dto)

        verify(postRepository, times(1)).findByName(anyString())
        verifyNoMoreInteractions(postRepository)

        StepVerifier.create(returned)
            .assertNext {  }
            .verifyComplete()
    }

    fun updatePost_shouldThrowNotFound_whenSearching() {}

    fun updatePost_shouldSucceed() {}

    @Test
    fun getPost_shouldThrowNotFound_whenSearching() {
        `when`(postRepository.findById(any(UUID::class.java))).thenReturn(Mono.empty())

        val returned = postService.getPost(UUID.randomUUID())

        verify(postRepository, times(1)).findById(any(UUID::class.java))
        verifyNoMoreInteractions(postRepository)

        StepVerifier.create(returned)
            .expectError(PostNotFound::class.java)
            .verify()
    }

    @Test
    fun getPost_shouldSucceed() {
        `when`(postRepository.findById(any(UUID::class.java))).thenReturn(Mono.just(created))

        val returned = postService.getPost(UUID.randomUUID())

        verify(postRepository, times(1)).findById(any(UUID::class.java))
        verifyNoMoreInteractions(postRepository)

        StepVerifier.create(returned)
            .assertNext { TestUtil.assertPost(it) }
            .verifyComplete()
    }

    @Test
    fun getPosts() {
        `when`(postRepository.findAll()).thenReturn(Flux.just(created))

        val returned = postService.getPosts()

        verify(postRepository, times(1)).findAll()
        verifyNoMoreInteractions(postRepository)

        StepVerifier.create(returned)
            .assertNext { TestUtil.assertPost(it) }
            .verifyComplete()
    }

    @Test
    fun removePost_shouldThrowNotFound_whenSearching() {
        `when`(postRepository.findById(any(UUID::class.java))).thenReturn(Mono.empty())

        val returned = postService.removePost(UUID.randomUUID())

        verify(postRepository, times(1)).findById(any(UUID::class.java))
        verifyNoMoreInteractions(postRepository)

        StepVerifier.create(returned)
            .expectError(PostNotFound::class.java)
            .verify()
    }

    @Test
    fun removePost_shouldSucceed() {
        `when`(postRepository.findById(any(UUID::class.java))).thenReturn(Mono.just(created))
        `when`(postRepository.delete(anyOrNull())).thenReturn(Mono.empty())

        val returned = postService.removePost(UUID.randomUUID())

        verify(postRepository, times(1)).findById(any(UUID::class.java))
        verifyNoMoreInteractions(postRepository)

        StepVerifier.create(returned)
            .assertNext { TestUtil.assertPost(it) }
            .verifyComplete()
    }
}