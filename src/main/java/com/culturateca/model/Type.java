package com.culturateca.model;

public enum Type {

    BOOK("book"),
    HANDOUT("handout"),
    BLUE_RAY("blue_ray"),
    CD("cd"),
    DVD("dvd"),
    K7("k7"),
    MAP("map"),
    MAGAZINE("magazine"),
    MANGA("manga"),
    LP("lp");

    public final String value;

    Type(String value) {
        this.value = value;
    }
}
