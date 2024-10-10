package com.springsecurity.com.springsecurity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.springsecurity.com.springsecurity.domain.AuthenticationRequest;
import com.springsecurity.com.springsecurity.domain.AuthenticationResponse;
import com.springsecurity.com.springsecurity.domain.SignUpRequest;
import com.springsecurity.com.springsecurity.entity.Token;
import com.springsecurity.com.springsecurity.entity.User;
import com.springsecurity.com.springsecurity.service.AuthService;
import com.springsecurity.com.springsecurity.service.TokenService;

/**
 * 
 * @author ratnendrr.girri
 *
 */
@RestController
public class UserController
{
  @Autowired
  private AuthService authService;

  @Autowired
  private TokenService tokenService;

  @Autowired
  private UserDetailsService userDetailsService;

  @Autowired
  private AuthenticationManager authenticationManager;

  @PostMapping("/register")
  public ResponseEntity<?> signupUser(@RequestBody SignUpRequest signupRequest)
  {
    User existingUser = authService.fetchUser(signupRequest.getEmail());
    if (existingUser != null)
      return new ResponseEntity<>("User already exists", HttpStatus.BAD_REQUEST);
    User createdUser = authService.createUser(signupRequest);
    if (createdUser == null)
      return new ResponseEntity<>("User not created, try again!", HttpStatus.BAD_REQUEST);

    final String jwt = authService.getToken(createdUser.getEmail());
    return new ResponseEntity<>(new AuthenticationResponse(jwt), HttpStatus.CREATED);
  }

  @PostMapping("/signin")
  public ResponseEntity<?> loginUser(@RequestBody AuthenticationRequest loginRequest)
  {
    String username = loginRequest.getEmail();
    String password = loginRequest.getPassword();
    try
    {
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }
    catch (BadCredentialsException e)
    {
      return new ResponseEntity<>("Incorrect username or password!", HttpStatus.UNAUTHORIZED);
    }
    catch (Exception e)
    {
      return new ResponseEntity<>("Issue occured will loging in.", HttpStatus.UNAUTHORIZED);
    }
    final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
    final String jwt = authService.getToken(userDetails.getUsername());

    return new ResponseEntity<>(new AuthenticationResponse(jwt), HttpStatus.OK);
  }

  @GetMapping("/signout")
  public ResponseEntity<?> logout(@RequestHeader(value = "token") String token)
  {
    Token authToken = tokenService.findByToken(token);
    if (authToken != null)
    {
      tokenService.deleteToken(authToken.getToken());
      SecurityContextHolder.clearContext();
      return new ResponseEntity<>("Logged out successfully", HttpStatus.OK);
    }
    return new ResponseEntity<>("Token is invalid", HttpStatus.BAD_REQUEST);
  }

}
