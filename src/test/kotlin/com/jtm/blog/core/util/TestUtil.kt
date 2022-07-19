package com.jtm.blog.core.util

import com.jtm.blog.core.domain.dto.PostDTO
import com.jtm.blog.core.domain.entity.Draft
import com.jtm.blog.core.domain.entity.Post
import org.assertj.core.api.Assertions.assertThat
import java.util.*

class TestUtil {
    companion object {
        fun createPost(): Post {
            return Post(UUID.randomUUID(), "test_blog", "Test Blog", "<h1>Test Content</h1>")
        }

        fun createPostDTO(): PostDTO {
            return PostDTO("test_blog", "Test Blog", "<h1>Test Content</h1>", mutableListOf())
        }

        fun createDraft(): Draft {
            return Draft(UUID.randomUUID(), Post(UUID.randomUUID(), "draft_blog", "Draft Blog", "<h1>Draft Content</h1>"))
        }

        fun assertDraft(draft: Draft) {
            assertThat(draft.post.name).isEqualTo("draft_blog")
            assertThat(draft.post.title).isEqualTo("Draft Blog")
            assertThat(draft.post.content).isEqualTo("<h1>Draft Content</h1>")
        }

        fun assertPost(post: Post) {
            assertThat(post.name).isEqualTo("test_blog")
            assertThat(post.title).isEqualTo("Test Blog")
            assertThat(post.content).isEqualTo("<h1>Test Content</h1>")
        }
    }
}