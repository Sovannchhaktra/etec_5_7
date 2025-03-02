package com.spring.security.Security

import com.spring.security.entity.Users
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.security.Key
import java.security.SecureRandom
import java.util.*
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

@Component
class JwtService {

    private fun getSignInKey(): SecretKey {
        val keyBytes = Decoders.BASE64URL.decode("3383bd331882b2c27817d5296033304e6d464ca32dec200e3c49352fdb80f392")
        return Keys.hmacShaKeyFor(keyBytes)
    }

    fun generateToken(user: Users): String {
        return Jwts
            .builder()
            .setSubject(user.username)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))
            .signWith(getSignInKey())
            .compact()
    }

    fun validateToken(token: String, username: String): Boolean {
        return extractExpiration(token).before(Date())
    }
    private fun extractExpiration(token: String): Date {
        return extractClaim(token, Claims::getExpiration)
    }
    fun <T> extractClaim(token: String, claimsResolver: (Claims) -> T): T {
        val claims = extractAllClaims(token)
        return claimsResolver(claims)
    }

    private fun extractAllClaims(token: String): Claims {
        return Jwts.parser()
            .setSigningKey(getSignInKey())
            .build()
            .parseClaimsJws(token)
            .body
    }

    fun extractUsername(token: String): String {
        return extractClaim(token, Claims::getSubject)
    }

}