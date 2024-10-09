/**
 * 
 */
package com.springsecurity.com.springsecurity.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springsecurity.com.springsecurity.entity.Token;
import com.springsecurity.com.springsecurity.repository.TokenRepository;
import com.springsecurity.com.springsecurity.service.TokenService;

/**
 * @author ratnendrr.girri
 *
 */
@Service
public class TokenServiceImpl implements TokenService
{
  @Autowired
  private TokenRepository tokenRepository;

  public Token saveToken(Token token)
  {
    return tokenRepository.save(token);
  }

  public Token findByToken(String token)
  {
    return tokenRepository.findByToken(token);
  }

  public void deleteToken(String token)
  {
    tokenRepository.deleteByToken(token);
  }

  public List<Token> findByUser(Long userId)
  {
    return tokenRepository.findByUserId(userId);
  }

}