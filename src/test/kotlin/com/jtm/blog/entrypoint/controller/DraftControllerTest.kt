package com.jtm.blog.entrypoint.controller

import com.jtm.blog.core.domain.dto.PostDTO
import com.jtm.blog.core.domain.entity.Draft
import com.jtm.blog.core.domain.entity.Post
import com.jtm.blog.data.service.DraftService
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
@WebFluxTest(DraftController::class)
@AutoConfigureWebTestClient
class DraftControllerTest {

    @Autowired
    lateinit var testclient: WebTestClient

    @MockBean
    lateinit var draftService: DraftService

    private val dto = PostDTO("test_blog", "<h1>Test Content</h1>", mutableListOf())
    private val created = Draft(UUID.randomUUID(), Post(UUID.randomUUID(), "test_blog", "<h1>Test Content</h1>"))

    @Test
    fun postDraft() {}

    @Test
    fun putDraft() {}

    @Test
    fun getDraft() {}

    @Test
    fun getDrafts() {}

    @Test
    fun deleteDraft() {}
}