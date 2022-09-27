package com.culturateca.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "author")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "author_id", nullable = false, unique = true)
    private Long authorId;

    @Column(name = "name", nullable = false)
    private String Name;

    @Column(name = "birth_date")
    private LocalDateTime birthDate;

    @Column(name = "death_date")
    private LocalDateTime deathDate;

    @Column(name = "nationality", nullable = false)
    private String nationality;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<MasterPiece> masterPiece;
}
