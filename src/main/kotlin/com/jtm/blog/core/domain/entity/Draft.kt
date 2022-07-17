package com.jtm.blog.core.domain.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.UUID

@Document("blog_drafts")
data class Draft(@Id val id: UUID, var post: Post) {

    fun updateName(name: String): Draft {
        this.post.updateName(name)
        return this
    }

    fun updateContent(content: String): Draft {
        this.post.updateContent(content)
        return this
    }

    fun addTag(tag: String): Draft {
        this.post.addTag(tag)
        return this
    }
}
