package com.springsecurity.com.springsecurity.controller;

import java.io.IOException;

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

import com.springsecurity.com.springsecurity.domain.AuthenticationRequest;
import com.springsecurity.com.springsecurity.domain.AuthenticationResponse;
import com.springsecurity.com.springsecurity.service.AuthService;
import com.springsecurity.com.springsecurity.service.impl.UserDetailsServiceImpl;

import jakarta.servlet.http.HttpServletResponse;

/**
 * 
 * @author ratnendrr.girri
 *
 */
@RestController
public class AuthenticationController
{
  @Autowired
  private AuthService authService;

  @Autowired
  private UserDetailsServiceImpl userDetailsService;

  @Autowired
  private AuthenticationManager authenticationManager;

  @PostMapping("/authenticate")
  public AuthenticationResponse createAuthenticationToken(@RequestBody AuthenticationRequest authenticationResquest,
      HttpServletResponse response)
      throws BadCredentialsException, DisabledException, UsernameNotFoundException, IOException
  {
    String username = authenticationResquest.getEmail();
    String password = authenticationResquest.getPassword();
    try
    {
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }
    catch (Exception e)
    {
      throw new BadCredentialsException("Incorrect username or password!");
    }

    final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationResquest.getEmail());
    final String token = authService.getToken(userDetails.getUsername());

    return new AuthenticationResponse(token);

  }

}
