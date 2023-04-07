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

    /**
     * Add a draft using the data transfer object.
     *
     * @param postDTO                   the post data transfer object.
     * @return Draft                    the draft saved.
     */
    fun addDraft(postDTO: PostDTO): Mono<Draft> {
        return draftRepository.save(Draft(UUID.randomUUID(), Post(postDTO)))
    }

    /**
     * Get the draft using the uuid identifier.
     *
     * @param id                        the identifier.
     * @return Draft                    the draft found.
     *
     * @throws DraftNotFound            if the draft is not found by the identifier.
     */
    fun getDraft(id: UUID): Mono<Draft> {
        return draftRepository.findById(id)
            .switchIfEmpty(Mono.defer { Mono.error(DraftNotFound()) })
    }

    /**
     * Get a list of the drafts.
     *
     * @return Draft                    the list of drafts
     */
    fun getDrafts(): Flux<Draft> {
        return draftRepository.findAll()
    }

    /**
     * Remove the draft by the identifier.
     *
     * @param id                        the uuid identifier.
     * @return Draft                    the draft removed.
     *
     * @throws DraftNotFound            if the draft is not found by the identifier.
     */
    fun removeDraft(id: UUID): Mono<Draft> {
        return draftRepository.findById(id)
            .switchIfEmpty(Mono.defer { Mono.error(DraftNotFound()) })
            .flatMap { draftRepository.delete(it).thenReturn(it) }
    }
}