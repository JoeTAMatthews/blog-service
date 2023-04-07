package com.jtm.blog.entrypoint.controller

import com.jtm.blog.BlogApplication
import com.jtm.blog.core.domain.entity.Draft
import com.jtm.blog.core.usecase.repository.DraftRepository
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
class DraftControllerIntegrationTest {

    @Autowired
    lateinit var draftRepository: DraftRepository

    @Autowired
    lateinit var testClient: WebTestClient

    private val dto = TestUtil.createPostDTO()
    private val updateDto = TestUtil.updatePostDTO()
    private val draft = TestUtil.createDraft()

    @After
    fun tearDown() {
        draftRepository.deleteAll().block()
    }

    @Test
    fun postDraft_shouldReturnData() {
        testClient.post()
            .uri("/draft")
            .bodyValue(dto)
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.post.name").isEqualTo("test_blog")
            .jsonPath("$.post.title").isEqualTo("Test Blog")
            .jsonPath("$.post.content").isEqualTo("<h1>Test Content</h1>")
    }

    @Test
    fun putName_shouldReturnNotFound() {
        testClient.put()
            .uri("/draft/update/${UUID.randomUUID()}/name")
            .bodyValue(dto)
            .exchange()
            .expectStatus().isNotFound
            .expectBody()
            .jsonPath("$.message").isEqualTo("Draft not found.")
    }

    @Test
    fun putName_shouldReturnData() {
        val d = draftRepository.save(draft).block() ?: return

        testClient.put()
            .uri("/draft/update/${d.id}/name")
            .bodyValue(updateDto)
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.post.name").isEqualTo("update_blog")
            .jsonPath("$.post.title").isEqualTo("Draft Blog")
            .jsonPath("$.post.content").isEqualTo("<h1>Draft Content</h1>")
    }

    @Test
    fun putTitle_shouldReturnNotFound() {
        testClient.put()
            .uri("/draft/update/${UUID.randomUUID()}/title")
            .bodyValue(dto)
            .exchange()
            .expectStatus().isNotFound
            .expectBody()
            .jsonPath("$.message").isEqualTo("Draft not found.")
    }

    @Test
    fun putTitle_shouldReturnData() {
        val d = draftRepository.save(draft).block() ?: return

        testClient.put()
            .uri("/draft/update/${d.id}/title")
            .bodyValue(updateDto)
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.post.name").isEqualTo("draft_blog")
            .jsonPath("$.post.title").isEqualTo("Update Blog")
            .jsonPath("$.post.content").isEqualTo("<h1>Draft Content</h1>")
    }

    @Test
    fun putContent_shouldReturnNotFound() {
        testClient.put()
            .uri("/draft/update/${UUID.randomUUID()}/content")
            .bodyValue(dto)
            .exchange()
            .expectStatus().isNotFound
            .expectBody()
            .jsonPath("$.message").isEqualTo("Draft not found.")
    }

    @Test
    fun putContent_shouldReturnData() {
        val d = draftRepository.save(draft).block() ?: return

        testClient.put()
            .uri("/draft/update/${d.id}/content")
            .bodyValue(updateDto)
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.post.name").isEqualTo("draft_blog")
            .jsonPath("$.post.title").isEqualTo("Draft Blog")
            .jsonPath("$.post.content").isEqualTo("<h1>Update Content</h1>")
    }

    @Test
    fun putTags_shouldReturnNotFound() {
        testClient.put()
            .uri("/draft/update/${UUID.randomUUID()}/tags")
            .bodyValue(dto)
            .exchange()
            .expectStatus().isNotFound
            .expectBody()
            .jsonPath("$.message").isEqualTo("Draft not found.")
    }

    @Test
    fun putTags_shouldReturnData() {
        val d = draftRepository.save(draft).block() ?: return

        testClient.put()
            .uri("/draft/update/${d.id}/tags")
            .bodyValue(updateDto)
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.post.name").isEqualTo("draft_blog")
            .jsonPath("$.post.title").isEqualTo("Draft Blog")
            .jsonPath("$.post.content").isEqualTo("<h1>Draft Content</h1>")
    }

    @Test
    fun getDraft_shouldReturnNotFound() {
        testClient.get()
            .uri("/draft/${UUID.randomUUID()}")
            .exchange()
            .expectStatus().isNotFound
            .expectBody()
            .jsonPath("$.message").isEqualTo("Draft not found.")
    }

    @ToString
    fun getDraft_shouldReturnData() {
        val d = draftRepository.save(draft).block()

        testClient.get()
            .uri("/draft/${d.id}")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.post.name").isEqualTo("draft_blog")
            .jsonPath("$.post.title").isEqualTo("Draft Blog")
            .jsonPath("$.post.content").isEqualTo("<h1>Draft Blog</h1>")
    }

    @Test
    fun getDrafts_shouldReturnData() {
        draftRepository.save(draft).block()
        draftRepository.save(Draft(UUID.randomUUID(), TestUtil.updatePost())).block()

        testClient.get()
            .uri("/draft/all")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$[0].post.name").isEqualTo("draft_blog")
            .jsonPath("$[0].post.title").isEqualTo("Draft Blog")
            .jsonPath("$[0].post.content").isEqualTo("<h1>Draft Content</h1>")
            .jsonPath("$[1].post.name").isEqualTo("update_blog")
            .jsonPath("$[1].post.title").isEqualTo("Update Blog")
            .jsonPath("$[1].post.content").isEqualTo("<h1>Update Content</h1>")
    }

    @Test
    fun removeDraft_shouldReturnNotFound() {
        testClient.delete()
            .uri("/draft/${UUID.randomUUID()}")
            .exchange()
            .expectStatus().isNotFound
            .expectBody()
            .jsonPath("$.message").isEqualTo("Draft not found.")
    }

    @Test
    fun removeDraft_shouldReturnData() {
        val d = draftRepository.save(draft).block() ?: return

        testClient.delete()
            .uri("/draft/${d.id}")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.post.name").isEqualTo("draft_blog")
            .jsonPath("$.post.title").isEqualTo("Draft Blog")
            .jsonPath("$.post.content").isEqualTo("<h1>Draft Content</h1>")
    }
}