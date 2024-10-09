package com.springsecurity.com.springsecurity.service;

import com.springsecurity.com.springsecurity.domain.SignUpRequest;
import com.springsecurity.com.springsecurity.entity.User;

/**
 * 
 * @author ratnendrr.girri
 *
 */
public interface AuthService
{
  User createUser(SignUpRequest signupRequest);
  
  User fetchUser(String userEmail);
  
  String getToken(String userEmail);
}
