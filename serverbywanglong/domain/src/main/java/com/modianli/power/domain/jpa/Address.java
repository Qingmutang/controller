package com.modianli.power.domain.jpa;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class Address implements Serializable {

  private String street;

  private String zipcode;

  private String city;

  // private Location location;

  public Address() {
    //   this.location=new Location();
  }

  public Address(String zip, String city, String street) {
    this.zipcode = zip;
    this.city = city;
    this.street = street;

    //  this.location=new Location();
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public String getZipcode() {
    return zipcode;
  }

  public void setZipcode(String zipcode) {
    this.zipcode = zipcode;
  }
//
//    public Location getLocation() {
//        return location;
//    }
//
//    public void setLocation(Location location) {
//        this.location = location;
//    }

}