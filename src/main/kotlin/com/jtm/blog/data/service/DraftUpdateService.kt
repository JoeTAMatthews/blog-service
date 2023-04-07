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

    /**
     * Update the name of the draft.
     *
     * @param id                    the uuid identifier.
     * @param dto                   the post data transfer object.
     * @return Draft                the draft updated.
     *
     * @throws DraftNotFound        if the draft is not found.
     */
    fun updateName(id: UUID, dto: PostDTO): Mono<Draft> {
        return draftRepository.findById(id)
            .switchIfEmpty(Mono.defer { Mono.error(DraftNotFound()) })
            .flatMap { draftRepository.save(it.updateName(dto.name)) }
    }

    /**
     * Update the title of the draft.
     *
     * @param id                    the uuid identifier.
     * @param dto                   the post data transfer object.
     * @return Draft                the draft updated.
     *
     * @throws DraftNotFound        if the draft is not found.
     */
    fun updateTitle(id: UUID, dto: PostDTO): Mono<Draft> {
        return draftRepository.findById(id)
            .switchIfEmpty(Mono.defer { Mono.error(DraftNotFound()) })
            .flatMap { draftRepository.save(it.updateTitle(dto.title)) }
    }

    /**
     * Update the content of the draft.
     *
     * @param id                    the uuid identifier.
     * @param dto                   the post data transfer object.
     * @return Draft                the draft updated.
     *
     * @throws DraftNotFound        if the draft is not found.
     */
    fun updateContent(id: UUID, dto: PostDTO): Mono<Draft> {
        return draftRepository.findById(id)
            .switchIfEmpty(Mono.defer { Mono.error(DraftNotFound()) })
            .flatMap { draftRepository.save(it.updateContent(dto.content)) }
    }

    /**
     * Update the tags of the draft.
     *
     * @param id                    the uuid identifier.
     * @param dto                   the post data transfer object.
     * @return Draft                the draft updated.
     *
     * @throws DraftNotFound        if the draft is not found.
     */
    fun updateTags(id: UUID, dto: PostDTO): Mono<Draft> {
        return draftRepository.findById(id)
            .switchIfEmpty(Mono.defer { Mono.error(DraftNotFound()) })
            .flatMap { draftRepository.save(it.updateTags(dto.tags)) }
    }
}