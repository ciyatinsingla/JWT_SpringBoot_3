package com.springsecurity.com.springsecurity.entity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.springsecurity.com.springsecurity.domain.SignUpRequest;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

/**
 * 
 * @author ratnendrr.girri
 *
 */
@Data
@Entity
@Table(name = "users")
@NoArgsConstructor
public class User
{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private String email;
  private String password;
  private String phone;

  public User(SignUpRequest signUpRequest)
  {
    super();
    this.name = signUpRequest.getName();
    this.email = signUpRequest.getEmail();
    this.phone = signUpRequest.getPhone();
    this.password = new BCryptPasswordEncoder().encode(signUpRequest.getPassword());
  }

}
