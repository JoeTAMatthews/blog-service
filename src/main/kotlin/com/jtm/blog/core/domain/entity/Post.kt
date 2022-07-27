package com.jtm.blog.core.domain.entity

import com.jtm.blog.core.domain.dto.PostDTO
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.UUID

@Document("blog_posts")
data class Post(@Id val id: UUID, var name: String, var title: String, var content: String, var tags: MutableList<String> = mutableListOf(), var updated: Long = System.currentTimeMillis(), var created: Long = System.currentTimeMillis()) {

    constructor(dto: PostDTO): this(UUID.randomUUID(), dto.name, dto.title, dto.content, dto.tags)

    fun updateName(name: String): Post {
        this.name = name
        this.updated()
        return this
    }

    fun updateTitle(title: String): Post {
        this.title = title
        this.updated()
        return this
    }

    fun updateContent(content: String): Post {
        this.content = content
        this.updated()
        return this
    }

    fun updateTags(tags: MutableList<String>): Post {
        this.tags = tags
        this.updated()
        return this
    }

    private fun updated() {
        this.updated = System.currentTimeMillis()
    }
}
