package com.mes;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Webservice
{
    /**
     * @GET declares an HTTP GET request
     * @Path("user") annotation on the userId parameter marks it as a
     * replacement for the {user} placeholder in the @GET path
     */
    @GET("/users/{user}")
    Call<Earthquake> getEarthquake(@Path("earthquake") int earthquakeId);
}
