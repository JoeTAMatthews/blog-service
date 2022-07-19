package com.jtm.blog.core.domain.dto

data class PostDTO(val name: String, var title: String, val content: String, val tags: MutableList<String>)
