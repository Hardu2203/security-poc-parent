package com.example.securitypoc.entities.security

import java.util.*
import javax.persistence.*

@Entity
class Role(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private val uuid: UUID? = null,

    private var name: String? = null,

//    @ManyToOne(fetch = FetchType.EAGER)
//    private val tenant: Tenant? = null,

//    @ManyToMany(mappedBy = "roles")
//    private var users: MutableSet<User?>? = null,

    @ManyToMany(mappedBy = "roles")
    var tenantUsers: MutableSet<TenantUser?>? = null,

//    @ManyToOne(fetch = FetchType.EAGER)
//    val tenantUser: TenantUser? = null,

    @ManyToMany(
        cascade = [CascadeType.MERGE],
        fetch = FetchType.EAGER
    ) @JoinTable(
        name = "role_authority",
        joinColumns = [JoinColumn(name = "ROLE_ID", referencedColumnName = "UUID")],
        inverseJoinColumns = [JoinColumn(name = "AUTHORITY_ID", referencedColumnName = "UUID")]
    )
    var authorities: MutableSet<Authority?>? = null

)