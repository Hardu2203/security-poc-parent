package com.example.securitypoc.config.security

import com.example.securitypoc.repositories.security.AccessRuleRepository
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.http.HttpMethod
import org.springframework.security.authorization.AuthorityAuthorizationManager
import org.springframework.security.authorization.AuthorizationDecision
import org.springframework.security.authorization.AuthorizationManager
import org.springframework.security.core.Authentication
import org.springframework.security.web.access.intercept.RequestAuthorizationContext
import org.springframework.security.web.access.intercept.RequestMatcherDelegatingAuthorizationManager
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.stereotype.Component
import java.net.http.HttpClient
import java.util.function.Supplier

@Component
class AccessRuleAuthorizationManager(private val rules: AccessRuleRepository) :
    AuthorizationManager<RequestAuthorizationContext> {

    private var delegate: RequestMatcherDelegatingAuthorizationManager? = null

    override fun check(
        authentication: Supplier<Authentication>,
        requestObject : RequestAuthorizationContext
    ): AuthorizationDecision {
        return delegate!!.check(authentication, requestObject.request)!!
    }

    @EventListener
    fun applyRules(event: ApplicationReadyEvent?) {
        val builder = RequestMatcherDelegatingAuthorizationManager.builder()
        val accessRules = rules.findAll()
        for (rule in accessRules) {
            builder.add(
                AntPathRequestMatcher(rule.pattern, rule.httpMethod),
                AuthorityAuthorizationManager.hasAuthority(rule.authority)
            )
        }
        delegate = builder.build()
    }
}