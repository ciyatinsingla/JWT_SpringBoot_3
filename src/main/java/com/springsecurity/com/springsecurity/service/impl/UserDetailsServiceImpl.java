package com.springsecurity.com.springsecurity.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.springsecurity.com.springsecurity.entity.User;
import com.springsecurity.com.springsecurity.repository.UserRepository;

/**
 * 
 * @author ratnendrr.girri
 *
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService
{
  @Autowired
  private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException
  {
    User user = userRepository.findByEmail(email);
    if (user == null)
      throw new UsernameNotFoundException("User not found.", null);
    
    return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
        new ArrayList<>());
  }

}
