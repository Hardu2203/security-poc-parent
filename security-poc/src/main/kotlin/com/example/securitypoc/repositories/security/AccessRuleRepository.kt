package com.example.securitypoc.repositories.security

import com.example.securitypoc.entities.security.AccessRule
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface AccessRuleRepository: CrudRepository<AccessRule, String> {
}