package com.jtm.blog.data.service

import com.jtm.blog.BlogApplication
import com.jtm.blog.core.domain.dto.PostDTO
import com.jtm.blog.core.domain.entity.Draft
import com.jtm.blog.core.domain.entity.Post
import com.jtm.blog.core.usecase.exception.draft.DraftNotFound
import com.jtm.blog.core.usecase.repository.DraftRepository
import com.jtm.blog.core.util.TestUtil
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import reactor.test.StepVerifier
import java.util.*

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = [BlogApplication::class])
@AutoConfigureDataMongo
class DraftServiceIntegrationTest {

    @Autowired
    lateinit var draftRepository: DraftRepository

    @Autowired
    lateinit var draftService: DraftService

    private val dto = TestUtil.createPostDTO()
    private val draft = TestUtil.createDraft()

    @After
    fun tearDown() {
        draftRepository.deleteAll().block()
    }

    @Test
    fun addDraft_shouldReturnDraft() {
        val draft = draftService.addDraft(dto)

        StepVerifier.create(draft)
            .assertNext { TestUtil.assertPost(it.post) }
            .verifyComplete()
    }

    @Test
    fun getDraft_shouldReturnError_whenFindingById() {
        val draft = draftService.getDraft(UUID.randomUUID())

        StepVerifier.create(draft)
            .expectError(DraftNotFound::class.java)
            .verify()
    }

    @Test
    fun getDraft_shouldReturnData_whenFindingById() {
        val d = draftRepository.save(draft).block() ?: return
        val returned = draftService.getDraft(d.id)

        StepVerifier.create(returned)
            .assertNext { TestUtil.assertDraft(it) }
            .verifyComplete()
    }

    @Test
    fun getDrafts_shouldReturnTwo() {
        draftRepository.save(draft).block()
        draftRepository.save(Draft(UUID.randomUUID(), TestUtil.createPost())).block()

        val returned = draftService.getDrafts()

        StepVerifier.create(returned)
            .assertNext { TestUtil.assertDraft(it) }
            .assertNext { TestUtil.assertPost(it.post) }
            .verifyComplete()
    }

    @Test
    fun getDrafts_shouldReturnEmpty() {
        val returned = draftService.getDrafts()

        StepVerifier.create(returned)
            .expectNextCount(0)
            .verifyComplete()
    }

    @Test
    fun removeDraft_shouldThrowNotFound() {
        val returned = draftService.removeDraft(UUID.randomUUID())

        StepVerifier.create(returned)
            .expectError(DraftNotFound::class.java)
            .verify()
    }

    @Test
    fun removeDraft_shouldReturnData() {
        val saved = draftRepository.save(draft).block() ?: return
        val returned = draftService.removeDraft(saved.id)

        StepVerifier.create(returned)
            .assertNext { TestUtil.assertDraft(it) }
            .verifyComplete()
    }
}