/**
 * 
 */
package com.springsecurity.com.springsecurity.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @author ratnendrr.girri
 *
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter
{
  @Autowired
  private UserDetailsService userDetailsService;

  @Autowired
  private JwtUtil jwtUtil;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException
  {
    if (!request.getRequestURI().startsWith("/jwtsb3/login"))
    {
      String username = extractUsername(request);
      if (username != null && SecurityContextHolder.getContext().getAuthentication() == null)
      {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (userDetails != null)
        {
          UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,
              null, userDetails.getAuthorities());
          authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

          // Set the authentication in the security context
          SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
      }
    }
    filterChain.doFilter(request, response);
  }

  private String extractUsername(HttpServletRequest request)
  {
    String token = getTokenFromRequest(request);

    if (token != null && jwtUtil.isTokenExpired(token))
      return jwtUtil.extractUsername(token);

    return null;
  }

  private String getTokenFromRequest(HttpServletRequest request)
  {
    String bearerToken = request.getHeader("Authorization");

    if (bearerToken != null && bearerToken.startsWith("Bearer "))
      return bearerToken.substring(7);

    return null;
  }

}