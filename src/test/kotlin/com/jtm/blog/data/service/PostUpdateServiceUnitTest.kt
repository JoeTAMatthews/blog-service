package com.jtm.blog.data.service

import com.jtm.blog.core.usecase.exception.post.PostNotFound
import com.jtm.blog.core.usecase.repository.PostRepository
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
class PostUpdateServiceUnitTest {

    private val postRepository: PostRepository = mock()
    private val updateService = PostUpdateService(postRepository)

    private val dto = TestUtil.updatePostDTO()
    private val created = TestUtil.createPost()
    private val updated = TestUtil.updatePost()

    @Test
    fun updateName_shouldThrowNotFound() {
        `when`(postRepository.findById(any(UUID::class.java))).thenReturn(Mono.empty())

        val returned = updateService.updateName(UUID.randomUUID(), dto)

        verify(postRepository, times(1)).findById(any(UUID::class.java))
        verifyNoMoreInteractions(postRepository)

        StepVerifier.create(returned)
            .expectError(PostNotFound::class.java)
            .verify()
    }

    @Test
    fun updateName_shouldReturn() {
        `when`(postRepository.findById(any(UUID::class.java))).thenReturn(Mono.just(created))
        `when`(postRepository.save(anyOrNull())).thenReturn(Mono.just(updated))

        val returned = updateService.updateName(UUID.randomUUID(), dto)

        verify(postRepository, times(1)).findById(any(UUID::class.java))
        verifyNoMoreInteractions(postRepository)

        StepVerifier.create(returned)
            .assertNext { TestUtil.assertUpdatePost(it) }
            .verifyComplete()
    }

    @Test
    fun updateTitle_shouldThrowNotFound() {
        `when`(postRepository.findById(any(UUID::class.java))).thenReturn(Mono.empty())

        val returned = updateService.updateTitle(UUID.randomUUID(), dto)

        verify(postRepository, times(1)).findById(any(UUID::class.java))
        verifyNoMoreInteractions(postRepository)

        StepVerifier.create(returned)
            .expectError(PostNotFound::class.java)
            .verify()
    }


    @Test
    fun updateTitle_shouldReturn() {
        `when`(postRepository.findById(any(UUID::class.java))).thenReturn(Mono.just(created))
        `when`(postRepository.save(anyOrNull())).thenReturn(Mono.just(updated))

        val returned = updateService.updateTitle(UUID.randomUUID(), dto)

        verify(postRepository, times(1)).findById(any(UUID::class.java))
        verifyNoMoreInteractions(postRepository)

        StepVerifier.create(returned)
            .assertNext { TestUtil.assertUpdatePost(it) }
            .verifyComplete()
    }

    @Test
    fun updateContent_shouldThrowNotFound() {
        `when`(postRepository.findById(any(UUID::class.java))).thenReturn(Mono.empty())

        val returned = updateService.updateContent(UUID.randomUUID(), dto)

        verify(postRepository, times(1)).findById(any(UUID::class.java))
        verifyNoMoreInteractions(postRepository)

        StepVerifier.create(returned)
            .expectError(PostNotFound::class.java)
            .verify()
    }

    @Test
    fun updateContent_shouldReturn() {
        `when`(postRepository.findById(any(UUID::class.java))).thenReturn(Mono.just(created))
        `when`(postRepository.save(anyOrNull())).thenReturn(Mono.just(updated))

        val returned = updateService.updateContent(UUID.randomUUID(), dto)

        verify(postRepository, times(1)).findById(any(UUID::class.java))
        verifyNoMoreInteractions(postRepository)

        StepVerifier.create(returned)
            .assertNext { TestUtil.assertUpdatePost(it) }
            .verifyComplete()
    }

    @Test
    fun updateTags_shouldThrowNotFound() {
        `when`(postRepository.findById(any(UUID::class.java))).thenReturn(Mono.empty())

        val returned = updateService.updateTags(UUID.randomUUID(), dto)

        verify(postRepository, times(1)).findById(any(UUID::class.java))
        verifyNoMoreInteractions(postRepository)

        StepVerifier.create(returned)
            .expectError(PostNotFound::class.java)
            .verify()
    }

    @Test
    fun updateTags_shouldReturn() {
        `when`(postRepository.findById(any(UUID::class.java))).thenReturn(Mono.just(created))
        `when`(postRepository.save(anyOrNull())).thenReturn(Mono.just(updated))

        val returned = updateService.updateTags(UUID.randomUUID(), dto)

        verify(postRepository, times(1)).findById(any(UUID::class.java))
        verifyNoMoreInteractions(postRepository)

        StepVerifier.create(returned)
            .assertNext { TestUtil.assertUpdatePost(it) }
            .verifyComplete()
    }
}