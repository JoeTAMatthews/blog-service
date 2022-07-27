package com.jtm.blog.data.service

import com.jtm.blog.core.domain.dto.PostDTO
import com.jtm.blog.core.domain.entity.Post
import com.jtm.blog.core.usecase.exception.post.PostNotFound
import com.jtm.blog.core.usecase.repository.PostRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.util.UUID

@Service
class PostUpdateService @Autowired constructor(private val postRepository: PostRepository) {

    fun updateName(id: UUID, dto: PostDTO): Mono<Post> {
        return postRepository.findById(id)
            .switchIfEmpty(Mono.defer { Mono.error(PostNotFound()) })
            .flatMap { postRepository.save(it.updateName(dto.name)) }
    }

    fun updateTitle(id: UUID, dto: PostDTO): Mono<Post> {
        return postRepository.findById(id)
            .switchIfEmpty(Mono.defer { Mono.error(PostNotFound()) })
            .flatMap { postRepository.save(it.updateTitle(dto.title)) }
    }

    fun updateContent(id: UUID, dto: PostDTO): Mono<Post> {
        return postRepository.findById(id)
            .switchIfEmpty(Mono.defer { Mono.error(PostNotFound()) })
            .flatMap { postRepository.save(it.updateContent(dto.content)) }
    }

    fun updateTags(id: UUID, dto: PostDTO): Mono<Post> {
        return postRepository.findById(id)
            .switchIfEmpty(Mono.defer { Mono.error(PostNotFound()) })
            .flatMap { postRepository.save(it.updateTags(dto.tags)) }
    }
}