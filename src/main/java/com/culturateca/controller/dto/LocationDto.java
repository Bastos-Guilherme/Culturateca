package com.culturateca.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
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