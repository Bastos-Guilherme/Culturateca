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
public class MasterPieceDto {

    private Long masterPieceId;
    private String title;
    private List<Long> author;
    private List<Long> language;
    private String releaseDate;
    private List<Long> category;
    private List<Long> collection;
    private List<Long> publisher;
    private Integer pages;
    private List<Long> studio;
    private Integer length;
    private String type;
    private List<Long> location;
    private Integer isbn;
    private Integer edition;
}