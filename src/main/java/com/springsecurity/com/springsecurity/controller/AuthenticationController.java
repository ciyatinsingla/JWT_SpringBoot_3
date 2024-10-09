package com.springsecurity.com.springsecurity.controller;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.springsecurity.com.springsecurity.config.JwtUtil;
import com.springsecurity.com.springsecurity.domain.AuthenticationRequest;
import com.springsecurity.com.springsecurity.domain.AuthenticationResponse;
import com.springsecurity.com.springsecurity.service.impl.UserDetailsServiceImpl;

import java.io.IOException;

/**
 * 
 * @author ratnendrr.girri
 *
 */
@RestController
public class AuthenticationController
{
  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private UserDetailsServiceImpl userDetailsService;

  @Autowired
  private JwtUtil jwtUtil;

  @PostMapping("/authenticate")
  public AuthenticationResponse createAuthenticationToken(@RequestBody AuthenticationRequest authenticationResquest,
      HttpServletResponse response)
      throws BadCredentialsException, DisabledException, UsernameNotFoundException, IOException
  {
    try
    {
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationResquest.getEmail(),
          authenticationResquest.getPassword()));
    }
    catch (BadCredentialsException e)
    {
      throw new BadCredentialsException("Incorrect username or password!");
    }
    catch (DisabledException disabledException)
    {
      response.sendError(HttpServletResponse.SC_NOT_FOUND, "User is not activated");
      return null;
    }

    final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationResquest.getEmail());

    final String token = jwtUtil.generateToken(userDetails.getUsername());

    return new AuthenticationResponse(token);

  }

}
