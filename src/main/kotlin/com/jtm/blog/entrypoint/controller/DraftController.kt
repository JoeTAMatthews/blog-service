package com.jtm.blog.entrypoint.controller

import com.jtm.blog.core.domain.dto.PostDTO
import com.jtm.blog.core.domain.entity.Draft
import com.jtm.blog.data.service.DraftService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.UUID

@RestController
@RequestMapping("/draft")
class DraftController @Autowired constructor(private val draftService: DraftService) {

    @PostMapping
    fun postDraft(@RequestBody dto: PostDTO): Mono<Draft> = draftService.addDraft(dto)

    @GetMapping("/{id}")
    fun getDraft(@PathVariable id: UUID): Mono<Draft> = draftService.getDraft(id)

    @GetMapping("/all")
    fun getDrafts(): Flux<Draft> = draftService.getDrafts()

    @DeleteMapping("/{id}")
    fun deleteDraft(@PathVariable id: UUID): Mono<Draft> = draftService.removeDraft(id)
}