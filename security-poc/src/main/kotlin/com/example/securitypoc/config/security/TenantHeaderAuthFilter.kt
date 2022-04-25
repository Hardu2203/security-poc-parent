package com.example.securitypoc.config.security

import com.example.securitypoc.entities.security.User
import com.example.securitypoc.repositories.security.RoleRepository
import com.example.securitypoc.repositories.security.TenantRepository
import com.example.securitypoc.repositories.security.UserRepository
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.security.oauth2.server.resource.web.DefaultBearerTokenResolver
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class TenantHeaderAuthFilter(
    requiresAuthenticationRequestMatcher: org.springframework.security.web.util.matcher.RequestMatcher?,
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository,
    private val tenantRepository: TenantRepository,
) :
    AbstractAuthenticationProcessingFilter(requiresAuthenticationRequestMatcher) {


    override fun doFilter(req: ServletRequest, res: ServletResponse, chain: javax.servlet.FilterChain) {
        val request = req as HttpServletRequest
        val response = res as HttpServletResponse
        if (logger.isDebugEnabled) {
            logger.debug("Request is to process authentication")
        }

//        val authResult = attemptAuthentication(request, response)
        try {
            val authResult = attemptAuthentication(request, response)
            successfulAuthentication(request, response, chain, authResult)
            chain.doFilter(request, response)
        } catch (e: AuthenticationException) {
            logger.error("Authentication Failed", e)
            unsuccessfulAuthentication(request, response, e)
        }
    }

    override fun unsuccessfulAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse, failed: AuthenticationException
    ) {
        SecurityContextHolder.clearContext()
        if (logger.isDebugEnabled) {
            logger.debug("Authentication request failed: $failed", failed)
            logger.debug("Updated SecurityContextHolder to contain null Authentication")
        }
        response.sendError(
            org.springframework.http.HttpStatus.UNAUTHORIZED.value(),
            org.springframework.http.HttpStatus.UNAUTHORIZED.reasonPhrase
        )
    }

    override fun attemptAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse
    ): Authentication {

        val tenant = tenantRepository.findByNameIgnoreCase(getTenant(request) ?: throw BadCredentialsException("Tenant header not found on request"))

        val auth = SecurityContextHolder.getContext().authentication
        logger.debug("Authenticating User: " + auth?.name + "with Tenant:" + tenant?.name)

        val user: User = userRepository.findByUsernameIgnoreCase(auth.name) ?: throw UsernameNotFoundException("User ${auth.name} not found")
//        {
//            val authException = UsernameNotFoundException("User ${auth.name} not found")
//            unsuccessfulAuthentication(
//                request,
//                response,
//                authException
//            )
//            throw authException
//        }
        val tenantUser = user.tenantUser.firstOrNull { it.tenant?.uuid == tenant?.uuid } ?: throw BadCredentialsException("User not connected to tenant")


        val authorities = tenantUser.roles?.flatMap { it.authorities!! }?.map { GrantedAuthority { it?.permission } }


        return JwtAuthenticationToken(auth.principal as Jwt, authorities, auth.name)
    }

    override fun successfulAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: javax.servlet.FilterChain,
        authResult: Authentication
    ) {
        if (logger.isDebugEnabled) {
            logger.debug(
                "Authentication success. Updating SecurityContextHolder to contain: "
                        + authResult
            )
        }
        SecurityContextHolder.getContext().authentication = authResult
    }

    private fun getPassword(request: HttpServletRequest): kotlin.String {
        return request.getHeader("Api-Secret")
    }

    private fun getUsername(request: HttpServletRequest): kotlin.String {
        return request.getHeader("Api-Key")
    }

    private fun getTenant(request: HttpServletRequest): String? {
        return request.getHeader("Tenant")
    }
}