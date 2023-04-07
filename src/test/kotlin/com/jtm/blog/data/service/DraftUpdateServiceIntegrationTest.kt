package com.jtm.blog.data.service

import com.jtm.blog.BlogApplication
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
class DraftUpdateServiceIntegrationTest {

    @Autowired
    lateinit var draftRepository: DraftRepository

    @Autowired
    lateinit var updateService: DraftUpdateService

    private val dto = TestUtil.updatePostDTO()
    private val draft = TestUtil.createDraft()

    @After
    fun tearDown() {
        draftRepository.deleteAll().block()
    }

    @Test
    fun updateName_shouldThrowNotFound() {
        val returned = updateService.updateName(UUID.randomUUID(), dto)

        StepVerifier.create(returned)
            .expectError(DraftNotFound::class.java)
            .verify()
    }

    @Test
    fun updateName_shouldReturnData() {
        val d = draftRepository.save(draft).block() ?: return
        val returned = updateService.updateName(d.id, dto)

        StepVerifier.create(returned)
            .assertNext { assertThat(it.post.name).isEqualTo("update_blog") }
            .verifyComplete()
    }

    @Test
    fun updateTitle_shouldThrowNotFound() {
        val returned = updateService.updateTitle(UUID.randomUUID(), dto)

        StepVerifier.create(returned)
            .expectError(DraftNotFound::class.java)
            .verify()
    }

    @Test
    fun updateTitle_shouldReturnData() {
        val d = draftRepository.save(draft).block() ?: return
        val returned = updateService.updateTitle(d.id, dto)

        StepVerifier.create(returned)
            .assertNext { assertThat(it.post.title).isEqualTo("Update Blog") }
            .verifyComplete()
    }

    @Test
    fun updateContent_shouldThrowNotFound() {
        val returned = updateService.updateContent(UUID.randomUUID(), dto)

        StepVerifier.create(returned)
            .expectError(DraftNotFound::class.java)
            .verify()
    }

    @Test
    fun updateContent_shouldReturnData() {
        val d = draftRepository.save(draft).block() ?: return
        val returned = updateService.updateContent(d.id, dto)

        StepVerifier.create(returned)
            .assertNext { assertThat(it.post.content).isEqualTo("<h1>Update Content</h1>") }
            .verifyComplete()
    }

    @Test
    fun updateTags_shouldThrowNotFound() {
        val returned = updateService.updateTags(UUID.randomUUID(), dto)

        StepVerifier.create(returned)
            .expectError(DraftNotFound::class.java)
            .verify()
    }

    @Test
    fun updateTags_shouldReturnData() {
        val d = draftRepository.save(draft).block() ?: return
        val returned = updateService.updateTags(d.id, dto)

        StepVerifier.create(returned)
            .assertNext { assertThat(it.post.tags.size).isEqualTo(2) }
            .verifyComplete()
    }
}