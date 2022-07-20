package com.jtm.blog.data.service

import com.jtm.blog.core.domain.dto.PostDTO
import com.jtm.blog.core.domain.entity.Draft
import com.jtm.blog.core.usecase.exception.draft.DraftNotFound
import com.jtm.blog.core.usecase.repository.DraftRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.util.UUID

@Service
class DraftUpdateService @Autowired constructor(private val draftRepository: DraftRepository) {

    fun updateName(id: UUID, dto: PostDTO): Mono<Draft> {
        return draftRepository.findById(id)
            .switchIfEmpty(Mono.defer { Mono.error(DraftNotFound()) })
            .flatMap { draftRepository.save(it.updateName(dto.name)) }
    }

    fun updateTitle(id: UUID, dto: PostDTO): Mono<Draft> {
        return draftRepository.findById(id)
            .switchIfEmpty(Mono.defer { Mono.error(DraftNotFound()) })
            .flatMap { draftRepository.save(it.updateTitle(dto.title)) }
    }

    fun updateContent(id: UUID, dto: PostDTO): Mono<Draft> {
        return draftRepository.findById(id)
            .switchIfEmpty(Mono.defer { Mono.error(DraftNotFound()) })
            .flatMap { draftRepository.save(it.updateContent(dto.content)) }
    }

    fun updateTags(id: UUID, dto: PostDTO): Mono<Draft> {
        return draftRepository.findById(id)
            .switchIfEmpty(Mono.defer { Mono.error(DraftNotFound()) })
            .flatMap { draftRepository.save(it.updateTags(dto.tags)) }
    }
}