package com.culturateca.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category-Id", nullable = false, unique = true)
    private Long categoryId;

    @Column(name = "category-name", nullable = false)
    private String categoryName;

    @JoinColumn(name = "masterpiece", nullable = false)
    @ManyToMany(fetch = FetchType.LAZY)
    private List<MasterPiece> masterPiece;
}
