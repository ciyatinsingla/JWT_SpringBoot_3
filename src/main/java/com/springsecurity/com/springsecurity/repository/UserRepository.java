package com.springsecurity.com.springsecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springsecurity.com.springsecurity.entity.User;

/**
 * 
 * @author ratnendrr.girri
 *
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>
{
  User findFirstByEmail(String email);
}
