package com.springsecurity.com.springsecurity.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 
 * @author ratnendrr.girri
 *
 */
@Data
@AllArgsConstructor
public class AuthenticationResponse
{
  private String token;
}
