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
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id", nullable = false)
    private Long addressId;

    @Column(name = "zip_code", nullable = false)
    private String zipCode;

    @Column(name = "number", nullable = false)
    private Integer number;

    @Column(name = "street", nullable = false)
    private String street;

    @Column(name = "neighborhood", nullable = false)
    private String neighborhood;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "state", nullable = false)
    private String state;

    @Column(name = "country", nullable = false)
    private String country;

    @Column(name = "extra_info")
    private String extraInfo;

    @JoinColumn(name="addressLocation")
    @ManyToOne(fetch = FetchType.LAZY)
    private List<Location> location;

    @JoinColumn(name="addressPublisher")
    @OneToOne(fetch = FetchType.LAZY)
    private Publisher publisher;

    @JoinColumn(name="addressStudio")
    @OneToOne(fetch = FetchType.LAZY)
    private Studio studio;
}