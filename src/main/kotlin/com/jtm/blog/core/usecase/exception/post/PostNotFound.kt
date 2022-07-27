package com.jtm.blog.core.usecase.exception.post

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Post not found.")
class PostNotFound: RuntimeException()