/**
 * 
 */
package com.springsecurity.com.springsecurity.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * @author ratnendrr.girri
 *
 */
@Data
@Entity
@Table(name = "tokens")
public class Token
{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String token;

  @Column(nullable = false)
  private LocalDateTime expirationTime;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;
}
