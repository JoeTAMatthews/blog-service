package com.jtm.blog.data.service

import com.jtm.blog.core.domain.dto.PostDTO
import com.jtm.blog.core.domain.entity.Draft
import com.jtm.blog.core.domain.entity.Post
import com.jtm.blog.core.usecase.exception.draft.DraftAlreadyFound
import com.jtm.blog.core.usecase.exception.draft.DraftNotFound
import com.jtm.blog.core.usecase.repository.DraftRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

@Service
class DraftService @Autowired constructor(private val draftRepository: DraftRepository) {

    fun addDraft(postDTO: PostDTO): Mono<Draft> {
        return draftRepository.findByName(postDTO.name)
            .flatMap<Draft?> { Mono.error(DraftAlreadyFound()) }
            .switchIfEmpty(Mono.defer { draftRepository.save(Draft(UUID.randomUUID(), Post(postDTO))) })
    }

    fun getDraft(id: UUID): Mono<Draft> {
        return draftRepository.findById(id)
            .switchIfEmpty(Mono.defer { Mono.error(DraftNotFound()) })
    }

    fun getDrafts(): Flux<Draft> {
        return draftRepository.findAll()
    }

    fun removeDraft(id: UUID): Mono<Draft> {
        return draftRepository.findById(id)
            .switchIfEmpty(Mono.defer { Mono.error(DraftNotFound()) })
            .flatMap { draftRepository.delete(it).thenReturn(it) }
    }
}