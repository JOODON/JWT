package com.example.springloginform.service

import com.example.springloginform.config.JwtProperties
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.Date
/*
    1.Header : JWT의 유형 및 해쉬 알고리즘 등의 메타데이터를 포함함.
    2.PayLoad : 실제로 전송되는 데이터 claims( 여러 정보들 ) 을 가지고 있음 / Base64로 인코딩됨
    3.Signature : 헤더와 페이로드 내용을 이용하여 서명
*/

@Service
class TokenService(
    jwtProperties: JwtProperties
) {
    //바이트 배열로부터 HMAC SHA 알고리즘을 사용하여 시크릿 키를 생성함.
    private val secretKey= Keys.hmacShaKeyFor(
        jwtProperties.key.toByteArray()
    )

    fun generate(
        userDetails: UserDetails,
        expirationDate: Date,
        additionalClaims: Map<String,Any> = emptyMap()
    ) : String =
        Jwts.builder()
            .claims()
            .subject(userDetails.username)
            .issuedAt(Date(System.currentTimeMillis()))
            .expiration(expirationDate)
            .add(additionalClaims)
            .and()
            .signWith(secretKey)
            .compact()

    fun extractEmail(token: String) : String? = getAllClaims(token).subject

    fun isExpired(token: String) : Boolean = getAllClaims(token).expiration.before(Date(System.currentTimeMillis()))

    fun isValid(token: String,userDetails: UserDetails) : Boolean{
        val email = extractEmail(token)

        return userDetails.username == email && !isExpired(token)
    }

    private fun getAllClaims(token : String) : Claims{
        var parser = Jwts.parser()
            .verifyWith(secretKey)
            .build()

        return parser.parseEncryptedClaims(token).payload
    }


}