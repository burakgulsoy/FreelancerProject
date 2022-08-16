package com.burakgulsoy.freelancerproject.retrofit.modelAPIs;


import com.burakgulsoy.freelancerproject.models.Role;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RoleAPI {

    @GET("/api/roles")
    Call<List<Role>> getAll();

    @POST("/api/addrole")
    Call<Role> addRole(@Body Role role);

    @POST("/api/updaterole")
    Call<Role> updateRole(@Body Role role);

    @POST("/api/deleterole")
    Call<Role> deleteRole(@Body Role role);

    @GET("/api/roles/{role_id}")
    Call<Role> getById(@Path("role_id") int role_id);


}
