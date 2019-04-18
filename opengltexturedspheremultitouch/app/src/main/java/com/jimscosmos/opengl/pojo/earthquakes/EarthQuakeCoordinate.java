package com.jimscosmos.opengl.pojo.earthquakes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EarthQuakeCoordinate {

    @SerializedName("$numberDouble")
    @Expose
    private String $numberDouble;
    @SerializedName("$numberInt")
    @Expose
    private String $numberInt;

    public String get$numberDouble() {
        return $numberDouble;
    }

    public void set$numberDouble(String $numberDouble) {
        this.$numberDouble = $numberDouble;
    }

    public String get$numberInt() {
        return $numberInt;
    }

    public void set$numberInt(String $numberInt) {
        this.$numberInt = $numberInt;
    }

}
