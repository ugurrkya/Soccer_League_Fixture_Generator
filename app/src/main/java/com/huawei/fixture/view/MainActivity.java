package com.huawei.fixture.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.huawei.fixture.R;
import com.huawei.fixture.model.Team;
import com.huawei.fixture.model.RetrofitClient;
import com.huawei.fixture.model.TeamsDaoInterface;
import com.huawei.fixture.viewmodel.RecyclerAdapter;

import java.io.Serializable;
import java.util.ArrayList;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class MainActivity extends AppCompatActivity {
    List<Team> teamList;
    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;
    Button draw_btn;
    Animation blink_anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        overridePendingTransition(
                R.anim.enter_act, R.anim.exit_act);
        draw_btn = findViewById(R.id.draw_fixture);
        teamList = new ArrayList<Team>();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerAdapter = new RecyclerAdapter(getApplicationContext(), teamList);
        recyclerView.setAdapter(recyclerAdapter);



        TeamsDaoInterface apiService = RetrofitClient.getClient().create(TeamsDaoInterface.class);
        Call<List<Team>> call = apiService.getTeams();
        call.enqueue(new Callback<List<Team>>() {
            @Override
            public void onResponse(Call<List<Team>> call, Response<List<Team>> response) {
                teamList = response.body();
                Log.d("TAG", "Response = " + teamList);
                recyclerAdapter.setTeamList(teamList);
            }

            @Override
            public void onFailure(Call<List<Team>> call, Throwable t) {
                Log.d("TAG", "Response = " + t.toString());
            }
        });

        blink_anim = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.blink);
        draw_btn.startAnimation(blink_anim);
        draw_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent drawIntent = new Intent(MainActivity.this,DrawActivity.class);

                drawIntent.putExtra("list", (Serializable) teamList);


                startActivity(drawIntent);

            }
        });

    }

    public void onStop () {
        super.onStop();
        overridePendingTransition(
                R.anim.enter_act, R.anim.exit_act);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        overridePendingTransition(
                R.anim.enter_act, R.anim.exit_act);
    }

    @Override
    protected void onStart() {
        super.onStart();
        overridePendingTransition(
                R.anim.enter_act, R.anim.exit_act);
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(
                R.anim.enter_act, R.anim.exit_act);
    }

}