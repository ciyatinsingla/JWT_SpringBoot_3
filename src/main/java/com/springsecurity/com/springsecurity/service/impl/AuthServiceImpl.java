package com.springsecurity.com.springsecurity.service.impl;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.springsecurity.com.springsecurity.config.JwtUtil;
import com.springsecurity.com.springsecurity.domain.SignUpRequest;
import com.springsecurity.com.springsecurity.entity.Token;
import com.springsecurity.com.springsecurity.entity.User;
import com.springsecurity.com.springsecurity.repository.UserRepository;
import com.springsecurity.com.springsecurity.service.AuthService;
import com.springsecurity.com.springsecurity.service.TokenService;

/**
 * 
 * @author ratnendrr.girri
 *
 */
@Service
public class AuthServiceImpl implements AuthService
{
  @Autowired
  private JwtUtil jwtUtil;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private TokenService tokenService;

  public User createUser(SignUpRequest signUpRequest)
  {
    return userRepository.save(new User(signUpRequest));
  }

  @Override
  public User fetchUser(String userEmail)
  {
    return userRepository.findByEmail(userEmail);
  }

  @Override
  public String getToken(String userEmail)
  {
    User user = fetchUser(userEmail);
    List<Token> tokens = tokenService.findByUser(user.getId());
    if (!CollectionUtils.isEmpty(tokens))
    {
      tokens.stream().sorted(Comparator.comparing(Token::getExpirationTime).reversed());
      for (Token token : tokens)
      {
        if (!token.getExpirationTime().isBefore(LocalDateTime.now()))
          return token.getToken();
        tokenService.deleteToken(token.getToken());
      }
    }

    Token authToken = new Token();
    authToken.setUser(user);
    String jwt = jwtUtil.generateToken(userEmail, authToken);
    authToken.setToken(jwt);
    tokenService.saveToken(authToken);

    return jwt;
  }
}
