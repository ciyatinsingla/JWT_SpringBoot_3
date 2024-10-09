package com.springsecurity.com.springsecurity.dto;

import com.springsecurity.com.springsecurity.entity.User;

import lombok.Data;

/**
 * 
 * @author ratnendrr.girri
 *
 */
@Data
public class UserDTO
{
  private Long id;
  private String name;
  private String email;
  private String phone;

  public UserDTO(User user)
  {
    this.id = user.getId();
    this.email = user.getEmail();
    this.name = user.getName();
    this.phone = user.getPhone();
  }

}
