package com.jtm.blog.core.usecase.exception.draft

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.FOUND, reason = "Draft already found with that name.")
class DraftAlreadyFound: RuntimeException()