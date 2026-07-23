package com.social.culturateca.model;

import java.util.List;
import java.util.Map;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "copy")
@Data
@NoArgsConstructor
public class Copy {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @JdbcTypeCode(SqlTypes.JSON)
  private Map<Long, String> property;

  @ManyToOne
  @JoinColumn(name = "canonic")
  private Canonic canonic;

  @OneToOne
  @JoinColumn(name = "location")
  private Location location;

  @ManyToMany(mappedBy = "copies")
  private List<Collection> collections;
}
