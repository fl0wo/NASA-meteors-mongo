package com.jimscosmos.opengl.pojo.meteors;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Meteor {

    @SerializedName("_id")
    @Expose
    private Id1 id1;
    @SerializedName("fall")
    @Expose
    private String fall;
    @SerializedName("geolocation")
    @Expose
    private Geolocation geolocation;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("mass")
    @Expose
    private String mass;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("nametype")
    @Expose
    private String nametype;
    @SerializedName("recclass")
    @Expose
    private String recclass;
    @SerializedName("reclat")
    @Expose
    private String reclat;
    @SerializedName("reclong")
    @Expose
    private String reclong;
    @SerializedName("year")
    @Expose
    private String year;

    public Id1 getId1() {
        return id1;
    }

    public void setId1(Id1 id1) {
        this.id1 = id1;
    }

    public String getFall() {
        return fall;
    }

    public void setFall(String fall) {
        this.fall = fall;
    }

    public Geolocation getGeolocation() {
        return geolocation;
    }

    public void setGeolocation(Geolocation geolocation) {
        this.geolocation = geolocation;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMass() {
        return mass;
    }

    public void setMass(String mass) {
        this.mass = mass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNametype() {
        return nametype;
    }

    public void setNametype(String nametype) {
        this.nametype = nametype;
    }

    public String getRecclass() {
        return recclass;
    }

    public void setRecclass(String recclass) {
        this.recclass = recclass;
    }

    public String getReclat() {
        return reclat;
    }

    public void setReclat(String reclat) {
        this.reclat = reclat;
    }

    public String getReclong() {
        return reclong;
    }

    public void setReclong(String reclong) {
        this.reclong = reclong;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "Meteor{" +
                "fall='" + fall + '\'' +
                ", geolocation=" + geolocation +
                ", mass='" + mass + '\'' +
                ", name='" + name + '\'' +
                ", year='" + year + '\'' +
                '}';
    }
}

