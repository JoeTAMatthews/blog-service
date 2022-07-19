package com.jtm.blog.entrypoint.controller

import com.jtm.blog.core.usecase.exception.post.PostAlreadyFound
import com.jtm.blog.core.usecase.exception.post.PostNotFound
import com.jtm.blog.core.util.TestUtil
import com.jtm.blog.data.service.PostService
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.times
import org.mockito.Mockito.`when`
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

@RunWith(SpringRunner::class)
@WebFluxTest(PostController::class)
@AutoConfigureWebTestClient
class PostControllerTest {

    @Autowired
    lateinit var testClient: WebTestClient

    @MockBean
    lateinit var postService: PostService

    private val dto = TestUtil.createPostDTO()
    private val created = TestUtil.createPost()

    @Test
    fun addPost_shouldReturnOk() {
        `when`(postService.addPost(anyOrNull())).thenReturn(Mono.just(created))

        testClient.post()
            .uri("/post")
            .bodyValue(dto)
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.name").isEqualTo("test_blog")
            .jsonPath("$.title").isEqualTo("Test Blog")
            .jsonPath("$.content").isEqualTo("<h1>Test Content</h1>")

        verify(postService, times(1)).addPost(anyOrNull())
        verifyNoMoreInteractions(postService)
    }

    @Test
    fun addPost_shouldReturnFound() {
        `when`(postService.addPost(anyOrNull())).thenReturn(Mono.error { PostAlreadyFound() })

        testClient.post()
            .uri("/post")
            .bodyValue(dto)
            .exchange()
            .expectStatus().isFound

        verify(postService, times(1)).addPost(anyOrNull())
        verifyNoMoreInteractions(postService)
    }

    @Test
    fun getPost_shouldReturnOk() {
        `when`(postService.getPost(anyOrNull())).thenReturn(Mono.just(created))

        testClient.get()
            .uri("/post/${UUID.randomUUID()}")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.name").isEqualTo("test_blog")
            .jsonPath("$.title").isEqualTo("Test Blog")
            .jsonPath("$.content").isEqualTo("<h1>Test Content</h1>")

        verify(postService, times(1)).getPost(anyOrNull())
        verifyNoMoreInteractions(postService)
    }

    @Test
    fun getPost_shouldReturnNotFound() {
        `when`(postService.getPost(anyOrNull())).thenReturn(Mono.error { PostNotFound() })

        testClient.get()
            .uri("/post/${UUID.randomUUID()}")
            .exchange()
            .expectStatus().isNotFound

        verify(postService, times(1)).getPost(anyOrNull())
        verifyNoMoreInteractions(postService)
    }

    @Test
    fun getPosts_shouldReturnOk() {
        `when`(postService.getPosts()).thenReturn(Flux.just(created))

        testClient.get()
            .uri("/post/all")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$[0].name").isEqualTo("test_blog")
            .jsonPath("$[0].title").isEqualTo("Test Blog")
            .jsonPath("$[0].content").isEqualTo("<h1>Test Content</h1>")

        verify(postService, times(1)).getPosts()
        verifyNoMoreInteractions(postService)
    }

    @Test
    fun deletePost_shouldReturnOk() {
        `when`(postService.removePost(anyOrNull())).thenReturn(Mono.just(created))

        testClient.delete()
            .uri("/post/${UUID.randomUUID()}")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.name").isEqualTo("test_blog")
            .jsonPath("$.title").isEqualTo("Test Blog")
            .jsonPath("$.content").isEqualTo("<h1>Test Content</h1>")

        verify(postService, times(1)).removePost(anyOrNull())
        verifyNoMoreInteractions(postService)
    }

    @Test
    fun deletePost_shouldReturnNotFound() {
        `when`(postService.removePost(anyOrNull())).thenReturn(Mono.error { PostNotFound() })

        testClient.delete()
            .uri("/post/${UUID.randomUUID()}")
            .exchange()
            .expectStatus().isNotFound

        verify(postService, times(1)).removePost(anyOrNull())
        verifyNoMoreInteractions(postService)
    }
}