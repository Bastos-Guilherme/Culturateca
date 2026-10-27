package com.social.culturateca.controller.DTO;

import java.util.List;
import java.util.Map;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ItemRegistrationRequest {

    private Long categoryId;

    private String name;

    private Map<Long, String> property;

    private Long locationId;

    private Long collectionId;

    private List<Long> hiddenProperties;
}