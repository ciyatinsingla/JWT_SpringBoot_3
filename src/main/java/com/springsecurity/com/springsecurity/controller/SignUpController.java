package com.springsecurity.com.springsecurity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.springsecurity.com.springsecurity.domain.SignUpRequest;
import com.springsecurity.com.springsecurity.dto.UserDTO;
import com.springsecurity.com.springsecurity.service.AuthService;

/**
 * 
 * @author ratnendrr.girri
 *
 */
@RestController
public class SignUpController
{
  @Autowired
  private AuthService authService;

  @PostMapping("/register")
  public ResponseEntity<?> signupUser(@RequestBody SignUpRequest signupRequest)
  {

    UserDTO createdUser = authService.createUser(signupRequest);
    if (createdUser == null)
    {
      return new ResponseEntity<>("User not created, come again later!", HttpStatus.BAD_REQUEST);
    }

    return new ResponseEntity<>(createdUser, HttpStatus.CREATED);

  }

}
