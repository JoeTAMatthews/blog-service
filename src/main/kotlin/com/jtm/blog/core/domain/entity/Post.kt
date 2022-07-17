package com.jtm.blog.core.domain.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.UUID

@Document("blog_posts")
data class Post(@Id val id: UUID, var name: String, var content: String, val tags: MutableList<String> = mutableListOf(), var updated: Long = System.currentTimeMillis(), var created: Long = System.currentTimeMillis()) {

    fun updateName(name: String): Post {
        this.name = name
        updated()
        return this
    }

    fun updateContent(content: String): Post {
        this.content = content
        updated()
        return this
    }

    fun addTag(tag: String): Post {
        this.tags.add(tag)
        updated()
        return this
    }

    private fun updated() {
        this.updated = System.currentTimeMillis()
    }
}
