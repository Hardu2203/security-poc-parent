package com.example.multitenantsecuritypoc

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MultitenantSecurityPocApplication

fun main(args: Array<String>) {
    runApplication<MultitenantSecurityPocApplication>(*args)
}
