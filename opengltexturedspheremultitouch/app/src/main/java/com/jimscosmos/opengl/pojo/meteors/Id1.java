package com.jimscosmos.opengl.pojo.meteors;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Id1 {

    @SerializedName("$oid")
    @Expose
    private String $oid;

    public String get$oid() {
        return $oid;
    }

    public void set$oid(String $oid) {
        this.$oid = $oid;
    }

}