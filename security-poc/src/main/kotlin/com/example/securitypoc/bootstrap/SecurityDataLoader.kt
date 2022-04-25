package com.example.securitypoc.bootstrap

import com.example.securitypoc.entities.Server
import com.example.securitypoc.entities.security.*
import com.example.securitypoc.repositories.ServerRepository
import com.example.securitypoc.repositories.security.*
import org.springframework.boot.CommandLineRunner
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import javax.transaction.Transactional

@Component
class SecurityDataLoader(
    private val authorityRepository: AuthorityRepository,
    private val roleRepository: RoleRepository,
    private val accessRuleRepository: AccessRuleRepository,
    private val userRepository: UserRepository,
    private val tenantRepository: TenantRepository,
    private val tenantUserRepository: TenantUserRepository,
    private val serverRepository: ServerRepository
) : CommandLineRunner {

    @Transactional
    override fun run(vararg args: String?) {

        //server auths
        val createServer: Authority = authorityRepository.save(Authority(permission = "servers.create"))
        val readServer: Authority = authorityRepository.save(Authority(permission = "servers.read"))
        val updateServer: Authority = authorityRepository.save(Authority(permission = "servers.update"))
        val deleteServer: Authority = authorityRepository.save(Authority(permission = "servers.delete"))

        //Api path to authority
        this.accessRuleRepository.save(AccessRule(pattern = "/api/servers/**", authority =  createServer.permission, httpMethod = HttpMethod.POST.toString()))
        this.accessRuleRepository.save(AccessRule(pattern ="/api/servers/**", authority = readServer.permission, httpMethod = HttpMethod.GET.toString()))
        this.accessRuleRepository.save(AccessRule(pattern ="/api/servers/**", authority = updateServer.permission, httpMethod = HttpMethod.PUT.toString()))
        this.accessRuleRepository.save(AccessRule(pattern ="/api/servers/**", authority = deleteServer.permission, httpMethod = HttpMethod.DELETE.toString()))

        //Roles
        val allRole: Role = roleRepository.save(Role(name = "ALL", authorities = mutableSetOf(createServer, readServer, updateServer, deleteServer)))
        val readRole: Role = roleRepository.save(Role(name = "READ", authorities = mutableSetOf(readServer)))

        //Tenants
        val disChem: Tenant = tenantRepository.save(Tenant(name = "Dis-Chem"))
        val purdue: Tenant = tenantRepository.save(Tenant(name = "Purdue"))

        //User
        val danny: User = userRepository.save(User(username = "danny"))
        val pieter: User = userRepository.save(User(username = "pieter"))
        val nelly: User = userRepository.save(User(username = "nelly"))

        //TenantUser
        val disChemDanny = tenantUserRepository.save(TenantUser(tenant = disChem, user = danny, roles = mutableSetOf(allRole)))
        val purdueDanny = tenantUserRepository.save(TenantUser(tenant = purdue, user = danny, roles = mutableSetOf(readRole)))
        val disChemPieter = tenantUserRepository.save(TenantUser(tenant = disChem, user = pieter, roles = mutableSetOf(readRole)))
        val purduePieter = tenantUserRepository.save(TenantUser(tenant = purdue, user = pieter, roles = mutableSetOf(allRole)))

        allRole.tenantUsers = mutableSetOf(disChemDanny, purduePieter)
        readRole.tenantUsers = mutableSetOf(purdueDanny, disChemPieter)

        //Set tenantUser on Tenant
        disChem.tenantUser.add(disChemDanny)
        disChem.tenantUser.add(disChemPieter)
        purdue.tenantUser.add(purdueDanny)
        purdue.tenantUser.add(purduePieter)

        //Set tenantUser on User
        danny.tenantUser.add(disChemDanny)
        danny.tenantUser.add(purdueDanny)
        pieter.tenantUser.add(purduePieter)
        pieter.tenantUser.add(disChemPieter)

        println("user data loaded")

        serverRepository.save(Server(
            name = "ServerName1",
            ip = "192.168.0.11",
            make = "BULL",
            ram = 32,
            unit = "GB",
            cpuCount = 2,
            osProduct = "Linux",
            osVersion = "2.1.3",
            tenant = disChem.uuid.toString()
        ))

    }
}