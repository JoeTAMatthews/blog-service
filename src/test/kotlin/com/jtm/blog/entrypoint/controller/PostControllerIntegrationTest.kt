package com.jtm.blog.entrypoint.controller

import com.jtm.blog.BlogApplication
import com.jtm.blog.core.usecase.repository.DraftRepository
import com.jtm.blog.core.usecase.repository.PostRepository
import com.jtm.blog.core.util.TestUtil
import org.joda.convert.ToString
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.reactive.server.WebTestClient
import java.util.*

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = [BlogApplication::class])
@AutoConfigureWebTestClient
@AutoConfigureDataMongo
class PostControllerIntegrationTest {

    @Autowired
    lateinit var postRepository: PostRepository

    @Autowired
    lateinit var draftRepository: DraftRepository

    @Autowired
    lateinit var testClient: WebTestClient

    private val dto = TestUtil.createPostDTO()
    private val updateDto = TestUtil.updatePostDTO()
    private val post = TestUtil.createPost()
    private val draft = TestUtil.createDraft()

    @After
    fun tearDown() {
        postRepository.deleteAll().block()
        draftRepository.deleteAll().block()
    }

    @Test
    fun publishDraft_shouldReturnNotFound() {
        testClient.get()
            .uri("/post/publish/${UUID.randomUUID()}")
            .exchange()
            .expectStatus().isNotFound
            .expectBody()
            .jsonPath("$.message").isEqualTo("Draft not found.")
    }

    @Test
    fun publishDraft_shouldReturnData() {
        draftRepository.save(draft).block()

        testClient.get()
            .uri("/post/publish/${draft.id}")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.name").isEqualTo("draft_blog")
            .jsonPath("$.title").isEqualTo("Draft Blog")
            .jsonPath("$.content").isEqualTo("<h1>Draft Content</h1>")
    }

    @Test
    fun addPost_shouldReturnAlreadyFound() {
        postRepository.save(post).block()

        testClient.post()
            .uri("/post")
            .bodyValue(dto)
            .exchange()
            .expectStatus().isFound
            .expectBody()
            .jsonPath("$.message").isEqualTo("Post already found.")
    }

    @Test
    fun addPost_shouldReturnData() {
        testClient.post()
            .uri("/post")
            .bodyValue(dto)
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.name").isEqualTo("test_blog")
            .jsonPath("$.title").isEqualTo("Test Blog")
            .jsonPath("$.content").isEqualTo("<h1>Test Content</h1>")
    }

    @Test
    fun putName_shouldReturnNotFound() {
        testClient.put()
            .uri("/post/update/${UUID.randomUUID()}/name")
            .bodyValue(updateDto)
            .exchange()
            .expectStatus().isNotFound
            .expectBody()
            .jsonPath("$.message").isEqualTo("Post not found.")
    }

    @Test
    fun putName_shouldReturnData() {
        val p = postRepository.save(post).block() ?: return

        testClient.put()
            .uri("/post/update/${p.id}/name")
            .bodyValue(updateDto)
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.name").isEqualTo("update_blog")
            .jsonPath("$.title").isEqualTo("Test Blog")
            .jsonPath("$.content").isEqualTo("<h1>Test Content</h1>")
    }

    @Test
    fun putTitle_shouldReturnNotFound() {
        testClient.put()
            .uri("/post/update/${UUID.randomUUID()}/title")
            .bodyValue(updateDto)
            .exchange()
            .expectStatus().isNotFound
            .expectBody()
            .jsonPath("$.message").isEqualTo("Post not found.")
    }

    @Test
    fun putTitle_shouldReturnData() {
        postRepository.save(post).block() ?: return

        testClient.put()
            .uri("/post/update/${post.id}/title")
            .bodyValue(updateDto)
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.name").isEqualTo("test_blog")
            .jsonPath("$.title").isEqualTo("Update Blog")
    }

    @Test
    fun putContent_shouldReturnNotFound() {
        testClient.put()
            .uri("/post/update/${UUID.randomUUID()}/content")
            .bodyValue(updateDto)
            .exchange()
            .expectStatus().isNotFound
            .expectBody()
            .jsonPath("$.message").isEqualTo("Post not found.")
    }

    @Test
    fun putContent_shouldReturnData() {
        postRepository.save(post).block() ?: return

        testClient.put()
            .uri("/post/update/${post.id}/content")
            .bodyValue(updateDto)
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.name").isEqualTo("test_blog")
            .jsonPath("$.title").isEqualTo("Test Blog")
            .jsonPath("$.content").isEqualTo("<h1>Update Content</h1>")
    }

    @Test
    fun putTags_shouldReturnNotFound() {
        testClient.put()
            .uri("/post/update/${UUID.randomUUID()}/tags")
            .bodyValue(updateDto)
            .exchange()
            .expectStatus().isNotFound
            .expectBody()
            .jsonPath("$.message").isEqualTo("Post not found.")
    }

    @Test
    fun putTags_shouldReturnData() {
        postRepository.save(post).block() ?: return

        testClient.put()
            .uri("/post/update/${post.id}/tags")
            .bodyValue(updateDto)
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.name").isEqualTo("test_blog")
            .jsonPath("$.title").isEqualTo("Test Blog")
            .jsonPath("$.content").isEqualTo("<h1>Test Content</h1>")
    }

    @Test
    fun getPost_shouldReturnNotFound() {
        testClient.get()
            .uri("/post/${UUID.randomUUID()}")
            .exchange()
            .expectStatus().isNotFound
            .expectBody()
            .jsonPath("$.message").isEqualTo("Post not found.")
    }

    @Test
    fun getPost_shouldReturnData() {
        postRepository.save(post).block()

        testClient.get()
            .uri("/post/${post.id}")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.name").isEqualTo("test_blog")
            .jsonPath("$.title").isEqualTo("Test Blog")
            .jsonPath("$.content").isEqualTo("<h1>Test Content</h1>")
    }

    @Test
    fun getPosts_shouldReturnData() {
        postRepository.save(post).block()
        postRepository.save(TestUtil.createPost()).block()

        testClient.get()
            .uri("/post/all")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$[0].name").isEqualTo("test_blog")
            .jsonPath("$[0].title").isEqualTo("Test Blog")
            .jsonPath("$[0].content").isEqualTo("<h1>Test Content</h1>")
    }

    @Test
    fun deletePost_shouldReturnNotFound() {
        testClient.delete()
            .uri("/post/${UUID.randomUUID()}")
            .exchange()
            .expectStatus().isNotFound
            .expectBody()
            .jsonPath("$.message").isEqualTo("Post not found.")
    }

    @Test
    fun deletePost_shouldReturnData() {
        postRepository.save(post).block()

        testClient.delete()
            .uri("/post/${post.id}")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.name").isEqualTo("test_blog")
            .jsonPath("$.title").isEqualTo("Test Blog")
            .jsonPath("$.content").isEqualTo("<h1>Test Content</h1>")
    }
}