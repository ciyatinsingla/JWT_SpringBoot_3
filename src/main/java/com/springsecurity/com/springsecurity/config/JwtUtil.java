package com.springsecurity.com.springsecurity.config;

import java.security.Key;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.springsecurity.com.springsecurity.entity.Token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

/**
 * 
 * @author ratnendrr.girri
 *
 */
@Component
public class JwtUtil
{
  @Value("${jwt.token.expiry.minutes:60}")
  private long tokenExpiryMinutes;

  private static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";

  public String extractUsername(String token)
  {
    return extractClaim(token, Claims::getSubject);
  }

  public Date extractExpiration(String token)
  {
    return extractClaim(token, Claims::getExpiration);
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver)
  {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  public Boolean isTokenExpired(String token)
  {
    return extractExpiration(token).before(new Date());
  }

  public Boolean validateToken(String token, UserDetails userDetails)
  {
    final String username = extractUsername(token);
    return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
  }

  public String generateToken(String userName, Token token)
  {
    Map<String, Object> claims = new HashMap<>();
    return createToken(claims, userName, token);
  }

  private Claims extractAllClaims(String token)
  {
    return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
  }

  private Key getSignKey()
  {
    byte[] keyBytes = Decoders.BASE64.decode(SECRET);
    return Keys.hmacShaKeyFor(keyBytes);
  }

  /**
   * Creates a JWT token with the specified claims and user information.
   *
   * @param claims
   *          a map of claims to be included in the token payload. These claims can contain additional information about
   *          the user.
   * @param userName
   *          the userName of the user for whom the token is being created. This will be set as the subject of the
   *          token.
   * @param token
   *          an instance of the Token class, which may contain expiration information. If provided, its expiration time
   *          will be set based on the configured token expiry duration.
   * @return a compact, URL-safe string representing the signed JWT token.
   * 
   * @throws IllegalArgumentException
   *           if the claims or userName are null.
   */
  private String createToken(Map<String, Object> claims, String userName, Token token)
  {
    final LocalDateTime tokenExpiry = LocalDateTime.now().plusMinutes(tokenExpiryMinutes);
    if (token != null)
      token.setExpirationTime(LocalDateTime.now().plusMinutes(tokenExpiryMinutes));

    return Jwts.builder().setClaims(claims).setSubject(userName).setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(Timestamp.valueOf(tokenExpiry)).signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
  }

}
