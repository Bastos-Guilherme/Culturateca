package com.social.culturateca.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "location")
@Data
@NoArgsConstructor
public class Location {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  private double latitude;

  private double longitude;

  @ManyToOne
  @JoinColumn(name = "location")
  private Location location;

  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  @ManyToOne
  @JoinColumn(name = "curator")
  private Curator curator;
}
