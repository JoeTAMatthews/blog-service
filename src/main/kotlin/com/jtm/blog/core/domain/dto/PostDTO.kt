package com.jtm.blog.core.domain.dto

data class PostDTO(val name: String, val content: String, val tags: MutableList<String>)
