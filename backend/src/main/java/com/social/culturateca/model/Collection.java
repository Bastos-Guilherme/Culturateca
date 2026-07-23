package com.social.culturateca.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "collection")
@Data
@NoArgsConstructor
public class Collection {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "curator")
  private Curator curator;

  private String name;

  @Column(name = "ispublic")
  private Boolean isPublic;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
    name = "collection_copy",
    joinColumns = @JoinColumn(name = "collection"),
    inverseJoinColumns = @JoinColumn(name = "copy")
  )
  private List<Copy> copies;
}
