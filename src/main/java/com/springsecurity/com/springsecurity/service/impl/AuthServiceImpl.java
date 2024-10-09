package com.springsecurity.com.springsecurity.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.springsecurity.com.springsecurity.domain.SignUpRequest;
import com.springsecurity.com.springsecurity.dto.UserDTO;
import com.springsecurity.com.springsecurity.entity.User;
import com.springsecurity.com.springsecurity.repository.UserRepository;
import com.springsecurity.com.springsecurity.service.AuthService;

/**
 * 
 * @author ratnendrr.girri
 *
 */
@Service
public class AuthServiceImpl implements AuthService
{

  @Autowired
  private UserRepository userRepository;

  public UserDTO createUser(SignUpRequest signUpRequest)
  {
    User user = new User(signUpRequest);
    user.setName(signUpRequest.getName());
    user.setEmail(signUpRequest.getEmail());
    user.setPhone(signUpRequest.getPhone());
    user.setPassword(new BCryptPasswordEncoder().encode(signUpRequest.getPassword()));
    User createdUser = userRepository.save(user);
    UserDTO userDTO = new UserDTO(createdUser);
    return userDTO;
  }
}
