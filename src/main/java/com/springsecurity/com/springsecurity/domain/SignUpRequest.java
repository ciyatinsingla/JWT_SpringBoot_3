package com.springsecurity.com.springsecurity.domain;

import lombok.Data;

/**
 * 
 * @author ratnendrr.girri
 *
 */
@Data
public class SignUpRequest
{
  private String name;
  private String email;
  private String password;
  private String phone;
}
