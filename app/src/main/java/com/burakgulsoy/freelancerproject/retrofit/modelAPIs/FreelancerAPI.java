package com.burakgulsoy.freelancerproject.retrofit.modelAPIs;

import com.burakgulsoy.freelancerproject.models.Freelancer;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface FreelancerAPI {

    @GET("/api/freelancers")
    Call<List<Freelancer>> getAll();

    @POST("/api/addfreelancer")
    Call<Freelancer> addFreelancer(@Body Freelancer freelancer);

    @POST("/api/updatefreelancer")
    Call<Freelancer> updateFreelancer(@Body Freelancer freelancer);

    @POST("/api/deletefreelancer")
    Call<Freelancer> deleteFreelancer(@Body Freelancer freelancer);

    @GET("/api/freelancers/{freelancer_id}")
    Call<Freelancer> getById(@Path("freelancer_id") int freelancer_id);

}
