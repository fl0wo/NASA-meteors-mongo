package com.jimscosmos.opengl.pojo;

import com.jimscosmos.opengl.pojo.earthquakes.Earthquake;
import com.jimscosmos.opengl.pojo.meteors.Meteor;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PojoService {


    String BASE_URL = "https://webhooks.mongodb-stitch.com/api/client/v2.0/app/nasanoserverapplication-cqses/service/http_service/incoming_webhook/";

    @GET("all_meteors")
    Observable<List<Meteor>> getAllMeteors();

    //meteors_by_limit

    @GET("meteors_by_limit")
    Observable<List<Meteor>> getMeteorsByLimit(@Query("limits") int numerOfMeteors);

    @GET("meteors_by_limit")
    Observable<List<Meteor>> getMeteors();

    @GET("all_earthquakes")
    Observable<List<Earthquake>> getAllEarthquakes();
}
