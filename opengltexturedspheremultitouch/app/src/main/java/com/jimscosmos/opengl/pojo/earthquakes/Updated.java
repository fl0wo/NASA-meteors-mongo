package com.jimscosmos.opengl.pojo.earthquakes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Updated {

    @SerializedName("$numberDouble")
    @Expose
    private String $numberDouble;

    public String get$numberDouble() {
        return $numberDouble;
    }

    public void set$numberDouble(String $numberDouble) {
        this.$numberDouble = $numberDouble;
    }

}
