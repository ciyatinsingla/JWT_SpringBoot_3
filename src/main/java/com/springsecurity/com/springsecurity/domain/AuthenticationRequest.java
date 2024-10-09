package com.springsecurity.com.springsecurity.domain;

import lombok.Data;

/**
 * 
 * @author ratnendrr.girri
 *
 */
@Data
public class AuthenticationRequest
{
  private String email;
  private String password;
}
