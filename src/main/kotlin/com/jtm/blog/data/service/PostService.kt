package com.jtm.blog.data.service

import com.jtm.blog.core.domain.dto.PostDTO
import com.jtm.blog.core.domain.entity.Post
import com.jtm.blog.core.usecase.exception.draft.DraftNotFound
import com.jtm.blog.core.usecase.exception.post.PostAlreadyFound
import com.jtm.blog.core.usecase.exception.post.PostNotFound
import com.jtm.blog.core.usecase.repository.DraftRepository
import com.jtm.blog.core.usecase.repository.PostRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.UUID

@Service
class PostService @Autowired constructor(private val postRepository: PostRepository, private val draftRepository: DraftRepository) {

    /**
     * Publish a draft to the blog.
     *
     * @param id                    the draft identifier.
     * @return post                 the post saved.
     *
     * @throws DraftNotFound        if the draft is not found.
     */
    fun publishDraft(id: UUID): Mono<Post> {
        return draftRepository.findById(id)
            .switchIfEmpty(Mono.defer { Mono.error(DraftNotFound()) })
            .flatMap { postRepository.save(it.post) }
            .flatMap { draftRepository.deleteById(id).thenReturn(it) }
    }

    /**
     * Add a post directly from the dto.
     *
     * @param dto                   the post data transfer object.
     * @return post                 the post saved.
     *
     * @throws PostAlreadyFound     if the post is already found.
     */
    fun addPost(dto: PostDTO): Mono<Post> {
        return postRepository.findByName(dto.name)
            .flatMap<Post> { Mono.error(PostAlreadyFound()) }
            .switchIfEmpty(Mono.defer { postRepository.save(Post(dto)) })
    }

    /**
     * Get the post by the identifier.
     *
     * @param id                    the identifier.
     * @return post                 the post found.
     *
     * @throws PostNotFound         if the post is not found.
     */
    fun getPost(id: UUID): Mono<Post> {
        return postRepository.findById(id)
            .switchIfEmpty(Mono.defer { Mono.error(PostNotFound()) })
    }

    /**
     * Get all the posts publicly available.
     *
     * @return post                 the list of posts found.
     */
    fun getPosts(): Flux<Post> {
        return postRepository.findAll()
    }

    /**
     * Remove the post by the identifier.
     *
     * @param id                    the identifier.
     * @return post                 the post removed.
     *
     * @throws PostNotFound         if the post is not found.
     */
    fun removePost(id: UUID): Mono<Post> {
        return postRepository.findById(id)
            .switchIfEmpty(Mono.defer { Mono.error(PostNotFound()) })
            .flatMap { postRepository.delete(it).thenReturn(it) }
    }
}