package com.huawei.fixture.viewmodel;

import android.util.Log;

import com.huawei.fixture.model.Fixture;
import com.huawei.fixture.model.Team;
import com.huawei.fixture.model.RetrofitClient;
import com.huawei.fixture.model.TeamsDaoInterface;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FixtureGenerator{
    List<Team> teamList;
    public List<List<Fixture>> getFixtures(List<String> teams, List<String> teamAmblem, boolean includeReverseFixtures) {
        teamList = new ArrayList<Team>();
        TeamsDaoInterface apiService = RetrofitClient.getClient().create(TeamsDaoInterface.class);
        Call<List<Team>> call = apiService.getTeams();
        call.enqueue(new Callback<List<Team>>() {
            @Override
            public void onResponse(Call<List<Team>> call, Response<List<Team>> response) {
                teamList = response.body();
                Log.d("TAG", "Response = " + teamList);
            }

            @Override
            public void onFailure(Call<List<Team>> call, Throwable t) {
                Log.d("TAG", "Response = " + t.toString());
            }
        });
        int numberOfTeams = teams.size();

        boolean ghostTeam = false;
        if (numberOfTeams % 2 != 0) {
            numberOfTeams++;
            ghostTeam = true;
        }

        int totalRounds = numberOfTeams - 1;
        int matchesPerRound = (numberOfTeams / 2)-1;

        List<List<Fixture>> rounds = new LinkedList<List<Fixture>>();
        int home_amblem;
        int away_amblem;
        for (int round = 1; round < totalRounds+1; round++) {

            List<Fixture> fixtures = new LinkedList<Fixture>();
            for (int match = 1; match < matchesPerRound+1; match++) {

                int home = (round + match) % (numberOfTeams - 1);
                int away = (numberOfTeams - 1 - match + round) % (numberOfTeams - 1);

                if (match == 0) {
                    away = numberOfTeams - 2;
                    away_amblem = away;

                }

                home_amblem = home;
                away_amblem = away;

                fixtures.add(new Fixture(teams.get(home), teams.get(away),teamAmblem.get(home_amblem)
                        ,teamAmblem.get(away_amblem)));
            }
            rounds.add(fixtures);
        }

        List<List<Fixture>> interleaved = new LinkedList<List<Fixture>>();

        int evn = 0;
        int odd = (numberOfTeams / 2);
        for (int i = 0; i < rounds.size(); i++) {
            if (i % 2 == 0) {
                interleaved.add(rounds.get(evn++));
            } else {
                interleaved.add(rounds.get(odd++));
            }
        }

        rounds = interleaved;


        for (int roundNumber = 0; roundNumber < rounds.size(); roundNumber++) {
            if (roundNumber % 2 == 1) {
                Fixture fixture = rounds.get(roundNumber).get(0);
                rounds.get(roundNumber).set(0, new Fixture(fixture.getAwayTeam(), fixture.getHomeTeam(), fixture.getAwayAmblem(),
                        fixture.getHomeAmblem()));
            }
        }
        
        if(includeReverseFixtures){
            List<List<Fixture>> reverseFixtures = new LinkedList<List<Fixture>>();
            for(List<Fixture> round: rounds){
                List<Fixture> reverseRound = new LinkedList<Fixture>();
                for(Fixture fixture: round){
                    reverseRound.add(new Fixture(fixture.getAwayTeam(), fixture.getHomeTeam(),fixture.getAwayAmblem(),
                            fixture.getHomeAmblem()));
                }
                reverseFixtures.add(reverseRound);
            }
            rounds.addAll(reverseFixtures);
        }

        return rounds;
    }
}