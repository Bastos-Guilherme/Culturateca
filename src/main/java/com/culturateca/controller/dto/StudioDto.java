package com.culturateca.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudioDto {

    private Long studioId;
    private String foundationDate;
    private String companyName;
    private Long hq;
    private Long masterPiece;
}
