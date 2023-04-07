package com.jtm.blog.data.service

import com.jtm.blog.core.usecase.exception.draft.DraftNotFound
import com.jtm.blog.core.usecase.repository.DraftRepository
import com.jtm.blog.core.util.TestUtil
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.times
import org.mockito.Mockito.`when`
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.springframework.test.context.junit4.SpringRunner
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import java.util.*

@RunWith(SpringRunner::class)
class DraftUpdateServiceUnitTest {

    private val draftRepository: DraftRepository = mock()
    private val updateService = DraftUpdateService(draftRepository)

    private val dto = TestUtil.updatePostDTO()
    private val created = TestUtil.createDraft()
    private val updated = TestUtil.updateDraft()

    @Test
    fun updateName_shouldThrowNotFound() {
        `when`(draftRepository.findById(any(UUID::class.java))).thenReturn(Mono.empty())

        val returned = updateService.updateName(UUID.randomUUID(), dto)

        verify(draftRepository, times(1)).findById(any(UUID::class.java))
        verifyNoMoreInteractions(draftRepository)

        StepVerifier.create(returned)
            .expectError(DraftNotFound::class.java)
            .verify()
    }

    @Test
    fun updateName_shouldReturn() {
        `when`(draftRepository.findById(any(UUID::class.java))).thenReturn(Mono.just(created))
        `when`(draftRepository.save(anyOrNull())).thenReturn(Mono.just(updated))

        val returned = updateService.updateName(UUID.randomUUID(), dto)

        verify(draftRepository, times(1)).findById(any(UUID::class.java))
        verifyNoMoreInteractions(draftRepository)

        StepVerifier.create(returned)
            .assertNext { TestUtil.assertUpdatePost(it.post) }
            .verifyComplete()
    }

    @Test
    fun updateTitle_shouldThrowNotFound() {
        `when`(draftRepository.findById(any(UUID::class.java))).thenReturn(Mono.empty())

        val returned = updateService.updateTitle(UUID.randomUUID(), dto)

        verify(draftRepository, times(1)).findById(any(UUID::class.java))
        verifyNoMoreInteractions(draftRepository)

        StepVerifier.create(returned)
            .expectError(DraftNotFound::class.java)
            .verify()
    }

    @Test
    fun updateTitle_shouldReturn() {
        `when`(draftRepository.findById(any(UUID::class.java))).thenReturn(Mono.just(created))
        `when`(draftRepository.save(anyOrNull())).thenReturn(Mono.just(updated))

        val returned = updateService.updateTitle(UUID.randomUUID(), dto)

        verify(draftRepository, times(1)).findById(any(UUID::class.java))
        verifyNoMoreInteractions(draftRepository)

        StepVerifier.create(returned)
            .assertNext { TestUtil.assertUpdatePost(it.post) }
            .verifyComplete()
    }

    @Test
    fun updateContent_shouldThrowNotFound() {
        `when`(draftRepository.findById(any(UUID::class.java))).thenReturn(Mono.empty())

        val returned = updateService.updateContent(UUID.randomUUID(), dto)

        verify(draftRepository, times(1)).findById(any(UUID::class.java))
        verifyNoMoreInteractions(draftRepository)

        StepVerifier.create(returned)
            .expectError(DraftNotFound::class.java)
            .verify()
    }

    @Test
    fun updateContent_shouldReturn() {
        `when`(draftRepository.findById(any(UUID::class.java))).thenReturn(Mono.just(created))
        `when`(draftRepository.save(anyOrNull())).thenReturn(Mono.just(updated))

        val returned = updateService.updateContent(UUID.randomUUID(), dto)

        verify(draftRepository, times(1)).findById(any(UUID::class.java))
        verifyNoMoreInteractions(draftRepository)

        StepVerifier.create(returned)
            .assertNext { TestUtil.assertUpdatePost(it.post) }
            .verifyComplete()
    }

    @Test
    fun updateTags_shouldThrowNotFound() {
        `when`(draftRepository.findById(any(UUID::class.java))).thenReturn(Mono.empty())

        val returned = updateService.updateTags(UUID.randomUUID(), dto)

        verify(draftRepository, times(1)).findById(any(UUID::class.java))
        verifyNoMoreInteractions(draftRepository)

        StepVerifier.create(returned)
            .expectError(DraftNotFound::class.java)
            .verify()
    }

    @Test
    fun updateTags_shouldReturn() {
        `when`(draftRepository.findById(any(UUID::class.java))).thenReturn(Mono.just(created))
        `when`(draftRepository.save(anyOrNull())).thenReturn(Mono.just(updated))

        val returned = updateService.updateTags(UUID.randomUUID(), dto)

        verify(draftRepository, times(1)).findById(any(UUID::class.java))
        verifyNoMoreInteractions(draftRepository)

        StepVerifier.create(returned)
            .assertNext { TestUtil.assertUpdatePost(it.post) }
            .verifyComplete()
    }
}