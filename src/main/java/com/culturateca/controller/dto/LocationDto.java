package com.culturateca.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocationDto {

    private Long locationId;
    private Long address;
    private String room;
    private String unit;
    private String shelf;
    private Long masterpiece;
}