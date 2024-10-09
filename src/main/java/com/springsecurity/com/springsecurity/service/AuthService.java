package com.springsecurity.com.springsecurity.service;

import com.springsecurity.com.springsecurity.domain.SignUpRequest;
import com.springsecurity.com.springsecurity.dto.UserDTO;

/**
 * 
 * @author ratnendrr.girri
 *
 */
public interface AuthService
{
  UserDTO createUser(SignUpRequest signupRequest);
}
