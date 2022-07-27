package com.jtm.blog.core.usecase.exception.draft

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Draft not found.")
class DraftNotFound: RuntimeException()