package com.social.culturateca.model;

import java.util.List;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "curator")
@Data
@NoArgsConstructor
public class Curator {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false)
  private String email;

  @ManyToOne
  @JoinColumn(name = "location")
  private Location location;

  private String password;

  @Column(nullable = false)
  private String name;

  @Enumerated(EnumType.STRING)
  private Gender gender;

  private String phone;

  @Column(name = "ispublic")
  private Boolean isPublic;

  private String bio;

  @JdbcTypeCode(SqlTypes.JSON)
  private List<Long> following;

  @Column(name = "profilepicture")
  private String profilePicture;

}
