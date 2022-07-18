package com.jtm.blog.core.usecase.exception.post

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.FOUND, reason = "Post already found.")
class PostAlreadyFound: RuntimeException()