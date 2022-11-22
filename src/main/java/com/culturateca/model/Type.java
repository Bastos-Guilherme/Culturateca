package com.culturateca.model;

public enum Type {

    BOOK("Book"),
    HANDOUT("Handout"),
    BLUE_RAY("BlueRay"),
    CD("CD"),
    DVD("DVD"),
    K7("K7"),
    MAP("Map"),
    MAGAZINE("Magazine"),
    MANGA("Manga"),
    LP("LP");

    public final String value;

    Type(String value) {
        this.value = value;
    }
}
