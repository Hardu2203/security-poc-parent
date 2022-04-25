package com.example.securitypoc.entities.security

import java.util.*
import javax.persistence.*

@Entity
class Tenant (

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) val uuid: UUID? = null,

    val name: String? = null,

//    @ManyToMany(cascade = [CascadeType.PERSIST , CascadeType.MERGE], fetch = FetchType.EAGER)
//    @JoinTable(
//        name = "tenant_user",
//        joinColumns = [JoinColumn(name = "TENANT_ID", referencedColumnName = "UUID")],
//        inverseJoinColumns = [JoinColumn(name = "USER_ID", referencedColumnName = "UUID")]
//    )
//    val users: Set<User>? = null

//    @OneToMany(
//        mappedBy = "tenant",
//        cascade = [CascadeType.ALL],
//        fetch = FetchType.EAGER,
//        orphanRemoval = true
//    )
//    private val roles: Set<Role>? = null

    @OneToMany(
        mappedBy = "tenant",
        cascade = [CascadeType.ALL],
        fetch = FetchType.EAGER,
        orphanRemoval = true
    )
    val tenantUser: MutableSet<TenantUser> = mutableSetOf()
)