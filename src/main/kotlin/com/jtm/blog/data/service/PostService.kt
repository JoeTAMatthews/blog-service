package com.jtm.blog.data.service

import com.jtm.blog.core.domain.dto.PostDTO
import com.jtm.blog.core.domain.entity.Post
import com.jtm.blog.core.usecase.repository.PostRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.UUID

@Service
class PostService @Autowired constructor(private val postRepository: PostRepository) {

    fun addPost(postDTO: PostDTO): Mono<Post> = Mono.empty()

    fun updatePost(id: UUID, postDTO: PostDTO): Mono<Post> = Mono.empty()

    fun getPost(id: UUID): Mono<Post> = Mono.empty()

    fun getPosts(): Flux<Post> = Flux.empty()

    fun removePost(id: UUID): Mono<Post> = Mono.empty()
}