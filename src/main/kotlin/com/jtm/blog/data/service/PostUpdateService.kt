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

    /**
     * Update the name of the post.
     *
     * @param id                    the uuid identifier.
     * @param dto                   the post data transfer object.
     * @return Post                 the post updated.
     *
     * @throws PostNotFound         if the post is not found.
     */
    fun updateName(id: UUID, dto: PostDTO): Mono<Post> {
        return postRepository.findById(id)
            .switchIfEmpty(Mono.defer { Mono.error(PostNotFound()) })
            .flatMap { postRepository.save(it.updateName(dto.name)) }
    }

    /**
     * Update the title of the post.
     *
     * @param id                    the uuid identifier.
     * @param dto                   the post data transfer object.
     * @return Post                 the post updated.
     *
     * @throws PostNotFound         if the post is not found.
     */
    fun updateTitle(id: UUID, dto: PostDTO): Mono<Post> {
        return postRepository.findById(id)
            .switchIfEmpty(Mono.defer { Mono.error(PostNotFound()) })
            .flatMap { postRepository.save(it.updateTitle(dto.title)) }
    }

    /**
     * Update the content of the post.
     *
     * @param id                    the uuid identifier.
     * @param dto                   the post data transfer object.
     * @return Post                 the post updated.
     *
     * @throws PostNotFound         if the post is not found.
     */
    fun updateContent(id: UUID, dto: PostDTO): Mono<Post> {
        return postRepository.findById(id)
            .switchIfEmpty(Mono.defer { Mono.error(PostNotFound()) })
            .flatMap { postRepository.save(it.updateContent(dto.content)) }
    }

    /**
     * Update the tags of the post.
     *
     * @param id                    the uuid identifier.
     * @param dto                   the post data transfer object.
     * @return Post                 the post updated.
     *
     * @throws PostNotFound         if the post is not found.
     */
    fun updateTags(id: UUID, dto: PostDTO): Mono<Post> {
        return postRepository.findById(id)
            .switchIfEmpty(Mono.defer { Mono.error(PostNotFound()) })
            .flatMap { postRepository.save(it.updateTags(dto.tags)) }
    }
}