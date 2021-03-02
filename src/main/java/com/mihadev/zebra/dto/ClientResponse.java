package com.mihadev.zebra.dto;

import java.util.List;

public class ClientResponse {
    private String firstName;
    private String lastName;
    private List<ClientAbonDto> abons;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<ClientAbonDto> getAbons() {
        return abons;
    }

    public void setAbons(List<ClientAbonDto> abons) {
        this.abons = abons;
    }
}
