/**
 * 
 */
package com.springsecurity.com.springsecurity.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springsecurity.com.springsecurity.entity.Token;

/**
 * @author ratnendrr.girri
 *
 */
@Repository
public interface TokenRepository extends JpaRepository<Token, Long>
{
  Token findByToken(String token);

  void deleteByToken(String token);

  List<Token> findByUserId(Long userId);
}