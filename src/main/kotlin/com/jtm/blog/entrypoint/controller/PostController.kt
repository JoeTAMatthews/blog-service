package com.jtm.blog.entrypoint.controller

import com.jtm.blog.core.domain.dto.PostDTO
import com.jtm.blog.core.domain.entity.Post
import com.jtm.blog.data.service.PostService
import com.jtm.blog.data.service.PostUpdateService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

@RestController
@RequestMapping("/post")
class PostController @Autowired constructor(private val postService: PostService, private val updateService: PostUpdateService) {

    @PostMapping
    fun addPost(@RequestBody dto: PostDTO): Mono<Post> = postService.addPost(dto)

    @PutMapping("/update/{id}/name")
    fun putName(@PathVariable id: UUID, @RequestBody dto: PostDTO): Mono<Post> = updateService.updateName(id, dto)

    @PutMapping("/update/{id}/title")
    fun putTitle(@PathVariable id: UUID, @RequestBody dto: PostDTO): Mono<Post> = updateService.updateTitle(id, dto)

    @PutMapping("/update/{id}/content")
    fun putContent(@PathVariable id: UUID, @RequestBody dto: PostDTO): Mono<Post> = updateService.updateContent(id, dto)

    @PutMapping("/update/{id}/tags")
    fun putTags(@PathVariable id: UUID, @RequestBody dto: PostDTO): Mono<Post> = updateService.updateTags(id, dto)

    @GetMapping("/{id}")
    fun getPost(@PathVariable id: UUID): Mono<Post> = postService.getPost(id)

    @GetMapping("/all")
    fun getPosts(): Flux<Post> = postService.getPosts()

    @DeleteMapping("/{id}")
    fun deletePost(@PathVariable id: UUID): Mono<Post> = postService.removePost(id)
}