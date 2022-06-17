package com.culturateca.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsContructor
@NoArgsContructor
@Builder
public class Adress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String zipCode;
    private Long houseNumber;
    private String streetName;
    private String neirbourhood;
    private String city;
    private String country;
    private String additionalInfo;
    @ManytoOne
    private Location location;
}
