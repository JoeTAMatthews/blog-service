package com.jtm.blog.core.usecase.repository

import com.jtm.blog.core.domain.entity.Post
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.UUID

@Repository
interface PostRepository: ReactiveMongoRepository<Post, UUID> {

    fun findByName(name: String): Mono<Post>

    fun findByTags(tags: MutableList<String>): Flux<Post>
}