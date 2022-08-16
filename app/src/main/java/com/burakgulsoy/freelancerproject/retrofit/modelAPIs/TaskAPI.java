package com.burakgulsoy.freelancerproject.retrofit.modelAPIs;


import com.burakgulsoy.freelancerproject.models.Task;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface TaskAPI {

    @GET("/api/tasks")
    Call<List<Task>> getAll();

    @POST("/api/addtask")
    Call<Task> addTask(@Body Task task);

    @POST("/api/updatetask")
    Call<Task> updateTask(@Body Task task);

    @POST("/api/deletetask")
    Call<Task> deleteTask(@Body Task task);

    @GET("/api/tasks/{task_id}")
    Call<Task> getById(@Path("task_id") int task_id);

}
