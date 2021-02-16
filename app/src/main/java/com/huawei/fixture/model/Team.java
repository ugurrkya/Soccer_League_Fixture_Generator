
package com.huawei.fixture.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Team implements Serializable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("team_name")
    @Expose
    private String teamName;
    @SerializedName("team_amblem")
    @Expose
    private String teamAmblem;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamAmblem() {
        return teamAmblem;
    }

    public void setTeamAmblem(String teamAmblem) {
        this.teamAmblem = teamAmblem;
    }

}
