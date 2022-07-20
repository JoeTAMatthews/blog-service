package com.jtm.blog.data.service

import com.jtm.blog.core.domain.dto.PostDTO
import com.jtm.blog.core.domain.entity.Post
import com.jtm.blog.core.usecase.exception.post.PostAlreadyFound
import com.jtm.blog.core.usecase.exception.post.PostNotFound
import com.jtm.blog.core.usecase.repository.PostRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.UUID

@Service
class PostService @Autowired constructor(private val postRepository: PostRepository) {

    fun addPost(dto: PostDTO): Mono<Post> {
        return postRepository.findByName(dto.name)
            .flatMap<Post?> { Mono.error(PostAlreadyFound()) }
            .switchIfEmpty(Mono.defer { postRepository.save(Post(dto)) })
    }

    fun getPost(id: UUID): Mono<Post> {
        return postRepository.findById(id)
            .switchIfEmpty(Mono.defer { Mono.error(PostNotFound()) })
    }

    fun getPosts(): Flux<Post> {
        return postRepository.findAll()
    }

    fun removePost(id: UUID): Mono<Post> {
        return postRepository.findById(id)
            .switchIfEmpty(Mono.defer { Mono.error(PostNotFound()) })
            .flatMap { postRepository.delete(it).thenReturn(it) }
    }
}