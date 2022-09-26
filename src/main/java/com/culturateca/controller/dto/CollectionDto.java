package com.culturateca.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CollectionDto {

    private Long collectionId;
    private String collectionName;
    private List<Long> masterPiece;
}
