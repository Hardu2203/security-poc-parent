package com.example.securitypoc.entities.security

import org.springframework.security.core.CredentialsContainer
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*
import javax.persistence.*

@Entity
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private val uuid: UUID? = null,

    private var username: String? = null,
    private var password: String? = null,
    private var accountNonExpired: Boolean? = true,
    private var accountNonLocked: Boolean? = true,
    private var credentialsNonExpired: Boolean? = true,
    private var enabled: Boolean? = true,

    @OneToMany(
        mappedBy = "user",
        cascade = [CascadeType.MERGE],
        fetch = FetchType.EAGER,
        orphanRemoval = true
    )
    val tenantUser: MutableSet<TenantUser> = mutableSetOf()

) : UserDetails, CredentialsContainer {



//    @ManyToMany(cascade = [CascadeType.PERSIST ,CascadeType.MERGE], fetch = FetchType.EAGER)
//    @JoinTable(
//        name = "user_role",
//        joinColumns = [JoinColumn(name = "USER_ID", referencedColumnName = "UUID")],
//        inverseJoinColumns = [JoinColumn(name = "ROLE_ID", referencedColumnName = "UUID")]
//    )
//    private val roles: Set<Role>? = null

//    @ManyToMany(mappedBy = "users")
//    private val tenants: Set<Tenant>? = null



    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        TODO("Not yet implemented")
        //get
    }

    override fun getUsername(): String? {
        return username
    }


    override fun getPassword(): String? {
        return password
    }


    override fun isAccountNonExpired(): Boolean {
        return accountNonExpired!!
    }

    override fun isAccountNonLocked(): Boolean {
        return accountNonLocked!!
    }

    override fun isCredentialsNonExpired(): Boolean {
        return credentialsNonExpired!!
    }

    override fun isEnabled(): Boolean {
        return enabled!!
    }

    override fun eraseCredentials() {
        password = null
    }
}