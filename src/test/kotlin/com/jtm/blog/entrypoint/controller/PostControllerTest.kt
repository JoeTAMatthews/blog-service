package com.jtm.blog.entrypoint.controller

import com.jtm.blog.core.domain.dto.PostDTO
import com.jtm.blog.core.domain.entity.Post
import com.jtm.blog.data.service.PostService
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.reactive.server.WebTestClient
import java.util.*

@RunWith(SpringRunner::class)
@WebFluxTest(PostController::class)
@AutoConfigureWebTestClient
class PostControllerTest {

    @Autowired
    lateinit var testClient: WebTestClient

    @MockBean
    lateinit var postService: PostService

    private val dto = PostDTO("test_blog", "<h1>Test Content</h1>", mutableListOf())
    private val created = Post(UUID.randomUUID(), "test_blog", "<h1>Test Content</h1>")

    @Test
    fun addPost() {}

    @Test
    fun putPost() {}

    @Test
    fun getPost() {}

    @Test
    fun getPosts() {}

    @Test
    fun deletePost() {}

}