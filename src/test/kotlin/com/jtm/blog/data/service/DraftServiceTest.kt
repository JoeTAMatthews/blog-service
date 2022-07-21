package com.jtm.blog.data.service

import com.jtm.blog.core.usecase.exception.draft.DraftAlreadyFound
import com.jtm.blog.core.usecase.exception.draft.DraftNotFound
import com.jtm.blog.core.usecase.repository.DraftRepository
import com.jtm.blog.core.util.TestUtil
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.anyString
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class DraftServiceTest {

    private val draftRepository: DraftRepository = mock()
    private val draftService = DraftService(draftRepository)

    private val dto = TestUtil.createPostDTO()
    private val created = TestUtil.createDraft()

    @Test
    fun addDraft_shouldSucceed() {
        `when`(draftRepository.save(anyOrNull())).thenReturn(Mono.just(created))

        val returned = draftService.addDraft(dto)

        verify(draftRepository, times(1)).save(anyOrNull())
        verifyNoMoreInteractions(draftRepository)

        StepVerifier.create(returned)
            .assertNext { TestUtil.assertDraft(it) }
            .verifyComplete()
    }

    fun updateDraft_shouldThrowNotFound_whenSearching() {}

    fun updateDraft_shouldSucceed() {}

    @Test
    fun getDraft_shouldThrowNotFound_whenSearching() {
        `when`(draftRepository.findById(any(UUID::class.java))).thenReturn(Mono.empty())

        val returned = draftService.getDraft(UUID.randomUUID())

        verify(draftRepository, times(1)).findById(any(UUID::class.java))
        verifyNoMoreInteractions(draftRepository)

        StepVerifier.create(returned)
            .expectError(DraftNotFound::class.java)
            .verify()
    }

    @Test
    fun getDraft_shouldSucceed() {
        `when`(draftRepository.findById(any(UUID::class.java))).thenReturn(Mono.just(created))

        val returned = draftService.getDraft(UUID.randomUUID())

        verify(draftRepository, times(1)).findById(any(UUID::class.java))
        verifyNoMoreInteractions(draftRepository)

        StepVerifier.create(returned)
            .assertNext { TestUtil.assertDraft(it) }
            .verifyComplete()
    }

    @Test
    fun getDrafts() {
        `when`(draftRepository.findAll()).thenReturn(Flux.just(created))

        val returned = draftService.getDrafts()

        verify(draftRepository, times(1)).findAll()
        verifyNoMoreInteractions(draftRepository)

        StepVerifier.create(returned)
            .assertNext { TestUtil.assertDraft(it) }
            .verifyComplete()
    }

    @Test
    fun removeDraft_shouldThrowNotFound_whenSearching() {
        `when`(draftRepository.findById(any(UUID::class.java))).thenReturn(Mono.empty())

        val returned = draftService.removeDraft(UUID.randomUUID())

        verify(draftRepository, times(1)).findById(any(UUID::class.java))
        verifyNoMoreInteractions(draftRepository)

        StepVerifier.create(returned)
            .expectError(DraftNotFound::class.java)
            .verify()
    }

    @Test
    fun removeDraft_shouldSucceed() {
        `when`(draftRepository.findById(any(UUID::class.java))).thenReturn(Mono.just(created))
        `when`(draftRepository.delete(anyOrNull())).thenReturn(Mono.empty())

        val returned = draftService.removeDraft(UUID.randomUUID())

        verify(draftRepository, times(1)).findById(any(UUID::class.java))
        verifyNoMoreInteractions(draftRepository)

        StepVerifier.create(returned)
            .assertNext { TestUtil.assertDraft(it) }
            .verifyComplete()
    }
}