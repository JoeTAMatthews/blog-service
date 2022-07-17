package com.jtm.blog.data.service

import com.jtm.blog.core.domain.dto.PostDTO
import com.jtm.blog.core.domain.entity.Draft
import com.jtm.blog.core.usecase.repository.DraftRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

@Service
class DraftService @Autowired constructor(private val draftRepository: DraftRepository) {

    fun addDraft(postDTO: PostDTO): Mono<Draft> = Mono.empty()

    fun updateDraft(id: UUID, postDTO: PostDTO): Mono<Draft> = Mono.empty()

    fun getDraft(id: UUID): Mono<Draft> = Mono.empty()

    fun getDrafts(): Flux<Draft> = Flux.empty()

    fun removeDraft(id: UUID): Mono<Draft> = Mono.empty()
}