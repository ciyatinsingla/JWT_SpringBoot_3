/**
 * 
 */
package com.springsecurity.com.springsecurity.service;

import java.util.List;

import com.springsecurity.com.springsecurity.entity.Token;

/**
 * @author ratnendrr.girri
 *
 */
public interface TokenService
{
  public Token saveToken(Token token);

  public Token findByToken(String token);

  public void deleteToken(String token);
  
  public List<Token> findByUser(Long userId);
}