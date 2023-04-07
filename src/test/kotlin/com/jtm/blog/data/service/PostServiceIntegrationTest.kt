package com.jtm.blog.data.service

import com.jtm.blog.BlogApplication
import com.jtm.blog.core.domain.entity.Draft
import com.jtm.blog.core.usecase.exception.draft.DraftNotFound
import com.jtm.blog.core.usecase.exception.post.PostAlreadyFound
import com.jtm.blog.core.usecase.exception.post.PostNotFound
import com.jtm.blog.core.usecase.repository.DraftRepository
import com.jtm.blog.core.usecase.repository.PostRepository
import com.jtm.blog.core.util.TestUtil
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
class PostServiceIntegrationTest {

    @Autowired
    lateinit var postRepository: PostRepository

    @Autowired
    lateinit var draftRepository: DraftRepository

    @Autowired
    lateinit var postService: PostService

    private val post = TestUtil.createPost()
    private val draft = TestUtil.createDraft()
    private val dto = TestUtil.createPostDTO()

    @After
    fun tearDown() {
        draftRepository.deleteAll().block()
        postRepository.deleteAll().block()
    }

    @Test
    fun publishDraft_shouldThrowNotFound() {
        val returned = postService.publishDraft(UUID.randomUUID())

        StepVerifier.create(returned)
            .expectError(DraftNotFound::class.java)
            .verify()
    }

    @Test
    fun publishDraft_shouldReturnData() {
        val d = draftRepository.save(draft).block() ?: return
        val returned = postService.publishDraft(d.id)

        StepVerifier.create(returned)
            .assertNext { TestUtil.assertDraft(Draft(UUID.randomUUID(), it)) }
            .verifyComplete()
    }

    @Test
    fun addPost_shouldThrowAlreadyFound() {
        postRepository.save(post).block() ?: return
        val returned = postService.addPost(dto)

        StepVerifier.create(returned)
            .expectError(PostAlreadyFound::class.java)
            .verify()
    }

    @Test
    fun addPost_shouldReturnData() {
        val returned = postService.addPost(dto)

        StepVerifier.create(returned)
            .assertNext { TestUtil.assertPost(it) }
            .verifyComplete()
    }

    @Test
    fun getPost_shouldThrowNotFound() {
        val returned = postService.getPost(UUID.randomUUID())

        StepVerifier.create(returned)
            .expectError(PostNotFound::class.java)
            .verify()
    }

    @Test
    fun getPost_shouldReturnData() {
        val p = postRepository.save(post).block() ?: return
        val returned = postService.getPost(p.id)

        StepVerifier.create(returned)
            .assertNext { TestUtil.assertPost(it) }
            .verifyComplete()
    }

    @Test
    fun getPosts_shouldReturnEmpty() {
        val posts = postService.getPosts()

        StepVerifier.create(posts)
            .expectNextCount(0)
            .verifyComplete()
    }

    @Test
    fun getPosts_shouldReturnTwo() {
        postRepository.save(post).block()
        postRepository.save(TestUtil.createPost()).block()

        val posts = postService.getPosts()

        StepVerifier.create(posts)
            .expectNextCount(2)
            .verifyComplete()
    }

    @Test
    fun removePost_shouldThrowNotFound() {
        val returned = postService.removePost(UUID.randomUUID())

        StepVerifier.create(returned)
            .expectError(PostNotFound::class.java)
            .verify()
    }

    @Test
    fun removePost_shouldReturnData() {
        val saved = postRepository.save(post).block() ?: return
        val returned = postService.removePost(saved.id)

        StepVerifier.create(returned)
            .assertNext { TestUtil.assertPost(it) }
            .verifyComplete()
    }
}