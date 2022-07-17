package com.jtm.blog.core.usecase.repository

import com.jtm.blog.core.domain.entity.Draft
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.UUID

@Repository
interface DraftRepository: ReactiveMongoRepository<Draft, UUID> {

    fun findByName(name: String): Mono<Draft>

    fun findByTags(tags: MutableList<String>): Flux<Draft>
}