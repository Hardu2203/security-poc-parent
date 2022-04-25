package com.example.securitypoc.entities.security

import java.util.*
import javax.persistence.*

@Entity
class TenantUser(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val uuid: UUID? = null,

    @ManyToOne(fetch = FetchType.EAGER)
    val tenant: Tenant? = null,

    @ManyToOne(fetch = FetchType.EAGER)
    val user: User? = null,

//    @OneToMany(
//        mappedBy = "tenantUser",
//        cascade = [CascadeType.ALL],
//        fetch = FetchType.EAGER,
//        orphanRemoval = true
//    )
//    val roles: Set<Role>? = null

    @ManyToMany(cascade = [CascadeType.PERSIST , CascadeType.MERGE], fetch = FetchType.EAGER)
    @JoinTable(
        name = "tenant_user_role",
        joinColumns = [JoinColumn(name = "TENANT_USER_ID", referencedColumnName = "UUID")],
        inverseJoinColumns = [JoinColumn(name = "ROLE_ID", referencedColumnName = "UUID")]
    )
    val roles: Set<Role>? = null
)