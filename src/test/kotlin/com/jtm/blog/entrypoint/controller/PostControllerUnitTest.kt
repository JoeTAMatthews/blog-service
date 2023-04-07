package com.jtm.blog.entrypoint.controller

import com.jtm.blog.core.usecase.exception.draft.DraftNotFound
import com.jtm.blog.core.usecase.exception.post.PostAlreadyFound
import com.jtm.blog.core.usecase.exception.post.PostNotFound
import com.jtm.blog.core.util.TestUtil
import com.jtm.blog.data.service.PostService
import com.jtm.blog.data.service.PostUpdateService
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
class PostControllerUnitTest {

    @Autowired
    lateinit var testClient: WebTestClient

    @MockBean
    lateinit var postService: PostService

    @MockBean
    lateinit var updateService: PostUpdateService

    private val dto = TestUtil.createPostDTO()
    private val created = TestUtil.createPost()
    private val updated = TestUtil.updatePost()

    @Test
    fun publishDraft_shouldReturnOk() {
        `when`(postService.publishDraft(anyOrNull())).thenReturn(Mono.just(created))

        testClient.get()
            .uri("/post/publish/${UUID.randomUUID()}")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.name").isEqualTo("test_blog")
            .jsonPath("$.title").isEqualTo("Test Blog")
            .jsonPath("$.content").isEqualTo("<h1>Test Content</h1>")

        verify(postService, times(1)).publishDraft(anyOrNull())
        verifyNoMoreInteractions(postService)
    }

    @Test
    fun publishDraft_shouldReturnNotFound() {
        `when`(postService.publishDraft(anyOrNull())).thenReturn(Mono.error { DraftNotFound() })

        testClient.get()
            .uri("/post/publish/${UUID.randomUUID()}")
            .exchange()
            .expectStatus().isNotFound

        verify(postService, times(1)).publishDraft(anyOrNull())
        verifyNoMoreInteractions(postService)
    }

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
    fun updateName_shouldReturnNotFound() {
        `when`(updateService.updateName(anyOrNull(), anyOrNull())).thenReturn(Mono.error { PostNotFound() })

        testClient.put()
            .uri("/post/update/${UUID.randomUUID()}/name")
            .bodyValue(dto)
            .exchange()
            .expectStatus().isNotFound

        verify(updateService, times(1)).updateName(anyOrNull(), anyOrNull())
        verifyNoMoreInteractions(updateService)
    }

    @Test
    fun updateName_shouldReturn() {
        `when`(updateService.updateName(anyOrNull(), anyOrNull())).thenReturn(Mono.just(updated))

        testClient.put()
            .uri("/post/update/${UUID.randomUUID()}/name")
            .bodyValue(dto)
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.name").isEqualTo("update_blog")
            .jsonPath("$.title").isEqualTo("Update Blog")
            .jsonPath("$.content").isEqualTo("<h1>Update Content</h1>")

        verify(updateService, times(1)).updateName(anyOrNull(), anyOrNull())
        verifyNoMoreInteractions(updateService)
    }

    @Test
    fun updateTitle_shouldReturnNotFound() {
        `when`(updateService.updateTitle(anyOrNull(), anyOrNull())).thenReturn(Mono.error { PostNotFound() })

        testClient.put()
            .uri("/post/update/${UUID.randomUUID()}/title")
            .bodyValue(dto)
            .exchange()
            .expectStatus().isNotFound

        verify(updateService, times(1)).updateTitle(anyOrNull(), anyOrNull())
        verifyNoMoreInteractions(updateService)
    }

    @Test
    fun updateTitle_shouldReturn() {
        `when`(updateService.updateTitle(anyOrNull(), anyOrNull())).thenReturn(Mono.just(updated))

        testClient.put()
            .uri("/post/update/${UUID.randomUUID()}/title")
            .bodyValue(dto)
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.name").isEqualTo("update_blog")
            .jsonPath("$.title").isEqualTo("Update Blog")
            .jsonPath("$.content").isEqualTo("<h1>Update Content</h1>")

        verify(updateService, times(1)).updateTitle(anyOrNull(), anyOrNull())
        verifyNoMoreInteractions(updateService)
    }

    @Test
    fun updateContent_shouldReturnNotFound() {
        `when`(updateService.updateContent(anyOrNull(), anyOrNull())).thenReturn(Mono.just(updated))

        testClient.put()
            .uri("/post/update/${UUID.randomUUID()}/content")
            .bodyValue(dto)
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.name").isEqualTo("update_blog")
            .jsonPath("$.title").isEqualTo("Update Blog")
            .jsonPath("$.content").isEqualTo("<h1>Update Content</h1>")

        verify(updateService, times(1)).updateContent(anyOrNull(), anyOrNull())
        verifyNoMoreInteractions(updateService)
    }

    @Test
    fun updateContent_shouldReturn() {
        `when`(updateService.updateContent(anyOrNull(), anyOrNull())).thenReturn(Mono.just(updated))

        testClient.put()
            .uri("/post/update/${UUID.randomUUID()}/content")
            .bodyValue(dto)
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.name").isEqualTo("update_blog")
            .jsonPath("$.title").isEqualTo("Update Blog")
            .jsonPath("$.content").isEqualTo("<h1>Update Content</h1>")

        verify(updateService, times(1)).updateContent(anyOrNull(), anyOrNull())
        verifyNoMoreInteractions(updateService)
    }

    @Test
    fun updateTags_shouldReturnNotFound() {
        `when`(updateService.updateTags(anyOrNull(), anyOrNull())).thenReturn(Mono.error { PostNotFound() })

        testClient.put()
            .uri("/post/update/${UUID.randomUUID()}/tags")
            .bodyValue(dto)
            .exchange()
            .expectStatus().isNotFound

        verify(updateService, times(1)).updateTags(anyOrNull(), anyOrNull())
        verifyNoMoreInteractions(updateService)
    }

    @Test
    fun updateTags_shouldReturn() {
        `when`(updateService.updateTags(anyOrNull(), anyOrNull())).thenReturn(Mono.just(updated))

        testClient.put()
            .uri("/post/update/${UUID.randomUUID()}/tags")
            .bodyValue(dto)
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.name").isEqualTo("update_blog")
            .jsonPath("$.title").isEqualTo("Update Blog")
            .jsonPath("$.content").isEqualTo("<h1>Update Content</h1>")
            .jsonPath("$.tags").isNotEmpty

        verify(updateService, times(1)).updateTags(anyOrNull(), anyOrNull())
        verifyNoMoreInteractions(updateService)
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