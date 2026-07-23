package com.social.culturateca.model;

import java.util.Map;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "canonic")
public class Canonic {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @JdbcTypeCode(SqlTypes.JSON)
  private Map<Long, String> property;

  private Category category;
}