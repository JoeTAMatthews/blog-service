package com.jtm.blog.data.service

import com.jtm.blog.BlogApplication
import com.jtm.blog.core.usecase.exception.post.PostNotFound
import com.jtm.blog.core.usecase.repository.PostRepository
import com.jtm.blog.core.util.TestUtil
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import reactor.test.StepVerifier
import java.util.UUID

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = [BlogApplication::class])
@AutoConfigureDataMongo
class PostUpdateServiceIntegrationTest {

    @Autowired
    lateinit var postRepository: PostRepository

    @Autowired
    lateinit var updateService: PostUpdateService

    private val dto = TestUtil.updatePostDTO()
    private val post = TestUtil.createPost()

    @Test
    fun updateName_shouldThrowNotFound() {
        val returned = updateService.updateName(UUID.randomUUID(), dto)

        StepVerifier.create(returned)
            .expectError(PostNotFound::class.java)
            .verify()
    }

    @Test
    fun updateName_shouldReturnData() {
        val p = postRepository.save(post).block() ?: return
        val returned = updateService.updateName(p.id, dto)

        StepVerifier.create(returned)
            .assertNext { assertThat(it.name).isEqualTo("update_blog") }
            .verifyComplete()
    }

    @Test
    fun updateTitle_shouldThrowNotFound() {
        val returned = updateService.updateTitle(UUID.randomUUID(), dto)

        StepVerifier.create(returned)
            .expectError(PostNotFound::class.java)
            .verify()
    }

    @Test
    fun updateTitle_shouldReturnData() {
        val p = postRepository.save(post).block() ?: return
        val returned = updateService.updateTitle(p.id, dto)

        StepVerifier.create(returned)
            .assertNext { assertThat(it.title).isEqualTo("Update Blog") }
            .verifyComplete()
    }

    @Test
    fun updateContent_shouldThrowNotFound() {
        val returned = updateService.updateContent(UUID.randomUUID(), dto)

        StepVerifier.create(returned)
            .expectError(PostNotFound::class.java)
            .verify()
    }

    @Test
    fun updateContent_shouldReturnData() {
        val p = postRepository.save(post).block() ?: return
        val returned = updateService.updateContent(p.id, dto)

        StepVerifier.create(returned)
            .assertNext { assertThat(it.content).isEqualTo("<h1>Update Content</h1>") }
            .verifyComplete()
    }

    @Test
    fun updateTags_shouldThrowNotFound() {
        val returned = updateService.updateTags(UUID.randomUUID(), dto)

        StepVerifier.create(returned)
            .expectError(PostNotFound::class.java)
            .verify()
    }

    @Test
    fun updateTags_shouldReturnData() {
        val p = postRepository.save(post).block() ?: return
        val returned = updateService.updateTags(p.id, dto)

        StepVerifier.create(returned)
            .assertNext { assertThat(it.tags.size).isEqualTo(2) }
            .verifyComplete()
    }
}