package com.jtm.blog

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@EnableDiscoveryClient
@SpringBootApplication
open class BlogApplication

fun main(args: Array<String>) {
    runApplication<BlogApplication>(*args)
}