package com.culturateca.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PublisherDto {

    private Long publisherId;
    private String publisherName;
    private Long address;
    private String foundationDate;
    private Long masterPiece;
}
