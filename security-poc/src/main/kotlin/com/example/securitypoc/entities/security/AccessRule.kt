package com.example.securitypoc.entities.security

import java.util.*
import javax.persistence.*

@Entity
class AccessRule(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private val uuid: UUID? = null,

    var pattern: String? = null,

    @Column
    var authority: String? = null,

    @Column
    var httpMethod: String? = null
)