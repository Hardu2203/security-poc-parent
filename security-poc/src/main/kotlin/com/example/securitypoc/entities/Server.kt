package com.example.securitypoc.entities

import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Server(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private val uuid: UUID? = null,

    val name: String,
    val ip: String,
    val make: String,
    val ram: Long,
    val unit: String,
    val cpuCount: Int,
    val osProduct: String,
    val osVersion: String,
    val tenant: String? = null,
)