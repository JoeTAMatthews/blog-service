package com.jtm.blog.entrypoint.controller

import com.jtm.blog.core.usecase.exception.draft.DraftAlreadyFound
import com.jtm.blog.core.usecase.exception.draft.DraftNotFound
import com.jtm.blog.core.util.TestUtil
import com.jtm.blog.data.service.DraftService
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
@WebFluxTest(DraftController::class)
@AutoConfigureWebTestClient
class DraftControllerTest {

    @Autowired
    lateinit var testclient: WebTestClient

    @MockBean
    lateinit var draftService: DraftService

    private val dto = TestUtil.createPostDTO()
    private val created = TestUtil.createDraft()

    @Test
    fun postDraft_shouldReturnOk() {
        `when`(draftService.addDraft(anyOrNull())).thenReturn(Mono.just(created))

        testclient.post()
            .uri("/draft")
            .bodyValue(dto)
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.post.name").isEqualTo("draft_blog")
            .jsonPath("$.post.title").isEqualTo("Draft Blog")
            .jsonPath("$.post.content").isEqualTo("<h1>Draft Content</h1>")

        verify(draftService, times(1)).addDraft(anyOrNull())
        verifyNoMoreInteractions(draftService)
    }

    @Test
    fun postDraft_shouldReturnFound() {
        `when`(draftService.addDraft(anyOrNull())).thenReturn(Mono.error { DraftAlreadyFound() })

        testclient.post()
            .uri("/draft")
            .bodyValue(dto)
            .exchange()
            .expectStatus().isFound

        verify(draftService, times(1)).addDraft(anyOrNull())
        verifyNoMoreInteractions(draftService)
    }

    @Test
    fun getDraft_shouldReturnOk() {
        `when`(draftService.getDraft(anyOrNull())).thenReturn(Mono.just(created))

        testclient.get()
            .uri("/draft/${UUID.randomUUID()}")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.post.name").isEqualTo("draft_blog")
            .jsonPath("$.post.title").isEqualTo("Draft Blog")
            .jsonPath("$.post.content").isEqualTo("<h1>Draft Content</h1>")

        verify(draftService, times(1)).getDraft(anyOrNull())
        verifyNoMoreInteractions(draftService)
    }

    @Test
    fun getDraft_shouldReturnNotFound() {
        `when`(draftService.getDraft(anyOrNull())).thenReturn(Mono.error { DraftNotFound() })

        testclient.get()
            .uri("/draft/${UUID.randomUUID()}")
            .exchange()
            .expectStatus().isNotFound

        verify(draftService, times(1)).getDraft(anyOrNull())
        verifyNoMoreInteractions(draftService)
    }

    @Test
    fun getDrafts_shouldReturnOk() {
        `when`(draftService.getDrafts()).thenReturn(Flux.just(created))

        testclient.get()
            .uri("/draft/all")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$[0].post.name").isEqualTo("draft_blog")
            .jsonPath("$[0].post.title").isEqualTo("Draft Blog")
            .jsonPath("$[0].post.content").isEqualTo("<h1>Draft Content</h1>")

        verify(draftService, times(1)).getDrafts()
        verifyNoMoreInteractions(draftService)
    }

    @Test
    fun deleteDraft_shouldReturnNotFound() {
        `when`(draftService.removeDraft(anyOrNull())).thenReturn(Mono.error { DraftNotFound() })

        testclient.delete()
            .uri("/draft/${UUID.randomUUID()}")
            .exchange()
            .expectStatus().isNotFound

        verify(draftService, times(1)).removeDraft(anyOrNull())
        verifyNoMoreInteractions(draftService)
    }

    @Test
    fun deleteDraft_shouldReturnOk() {
        `when`(draftService.removeDraft(anyOrNull())).thenReturn(Mono.just(created))

        testclient.delete()
            .uri("/draft/${UUID.randomUUID()}")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.post.name").isEqualTo("draft_blog")
            .jsonPath("$.post.title").isEqualTo("Draft Blog")
            .jsonPath("$.post.content").isEqualTo("<h1>Draft Content</h1>")

        verify(draftService, times(1)).removeDraft(anyOrNull())
        verifyNoMoreInteractions(draftService)
    }
}