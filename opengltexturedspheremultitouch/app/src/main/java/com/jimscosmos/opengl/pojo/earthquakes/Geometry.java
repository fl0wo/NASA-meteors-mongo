package com.jimscosmos.opengl.pojo.earthquakes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Geometry {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("coordinates")
    @Expose
    private List<EarthQuakeCoordinate> coordinates = null;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<EarthQuakeCoordinate> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<EarthQuakeCoordinate> coordinates) {
        this.coordinates = coordinates;
    }

}