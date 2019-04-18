package com.jimscosmos.opengl.pojo.meteors;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Coordinate {

    @SerializedName("$numberDouble")
    @Expose
    private String $numberDouble;

    public String get$numberDouble() {
        return $numberDouble;
    }

    public void set$numberDouble(String $numberDouble) {
        this.$numberDouble = $numberDouble;
    }

    @Override
    public String toString() {
        return "Coordinate{" +
                "$numberDouble='" + $numberDouble + '\'' +
                '}';
    }
}