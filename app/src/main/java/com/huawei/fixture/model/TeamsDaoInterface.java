package com.huawei.fixture.model;

import com.huawei.fixture.model.Team;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;


public interface TeamsDaoInterface {

    //retrieve all teams
    @GET("teams/")
    Call<List<Team>> getTeams();


}
