package com.example.securitypoc.entities.security

import java.util.*
import javax.persistence.*

@Entity
class Authority(

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private val uuid: UUID? = null,

    val permission: String? = null,

    @ManyToMany(mappedBy = "authorities")
    private var roles: MutableSet<Role?>? = null

)