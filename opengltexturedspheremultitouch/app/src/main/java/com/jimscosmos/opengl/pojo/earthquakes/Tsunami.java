package com.jimscosmos.opengl.pojo.earthquakes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Tsunami {

    @SerializedName("$numberInt")
    @Expose
    private String $numberInt;

    public String get$numberInt() {
        return $numberInt;
    }

    public void set$numberInt(String $numberInt) {
        this.$numberInt = $numberInt;
    }

}
