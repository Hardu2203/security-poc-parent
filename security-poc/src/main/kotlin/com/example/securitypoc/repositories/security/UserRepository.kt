package com.example.securitypoc.repositories.security

import com.example.securitypoc.entities.security.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface UserRepository: JpaRepository<User, UUID> {


    fun findByUsernameIgnoreCase(username: String?): User?


}