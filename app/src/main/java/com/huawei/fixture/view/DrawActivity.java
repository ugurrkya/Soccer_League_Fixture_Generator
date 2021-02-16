package com.huawei.fixture.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.Slide;
import androidx.transition.TransitionManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.Button;
import android.widget.TextView;


import com.huawei.fixture.R;
import com.huawei.fixture.model.Fixture;
import com.huawei.fixture.model.Team;
import com.huawei.fixture.model.RetrofitClient;
import com.huawei.fixture.model.TeamsDaoInterface;
import com.huawei.fixture.viewmodel.FixtureGenerator;
import com.huawei.fixture.viewmodel.FixtureViewHolder;
import com.huawei.fixture.viewmodel.OnSwipeTouchListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DrawActivity extends AppCompatActivity {
    List<Team> teamList;
    List<String> teamAmblem;
    List<String> team_name;

    List<Fixture> fixture;
    //FixtureViewHolder fixtureViewHolder;


    RecyclerView rv;
    Button nextBtn, prevBtn;
    private int TOTAL_NUM_ITEMS;
    private int ITEMS_PER_PAGE;
    private int ITEMS_REMAINING;
    private int LAST_PAGE;
    private int currentPage = 0;
    private int totalPages;
    TextView week ;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);
        overridePendingTransition(
                R.anim.enter_act, R.anim.exit_act);


        fixture = new ArrayList<>();
        week = findViewById(R.id.week_count);
        Intent intent = getIntent();
        ArrayList<Team> list = (ArrayList<Team>) intent.getSerializableExtra("list");
        Collections.shuffle(list);
        week.setText("Week "+String.valueOf(currentPage+1));
        TeamsDaoInterface apiService = RetrofitClient.getClient().create(TeamsDaoInterface.class);
        Call<List<Team>> call = apiService.getTeams();
        call.enqueue(new Callback<List<Team>>() {
            @Override
            public void onResponse(Call<List<Team>> call, Response<List<Team>> response) {
                teamList = response.body();
            }

            @Override
            public void onFailure(Call<List<Team>> call, Throwable t) {
                Log.d("TAG", "Response = " + t.toString());
            }
        });
        team_name = new ArrayList<String>();
        teamAmblem = new ArrayList<String>();

        for (int c =0; c < list.size(); c++) {
            team_name.add(list.get(c).getTeamName());
            teamAmblem.add(list.get(c).getTeamAmblem());
        }

        FixtureGenerator fixtureGenerator = new FixtureGenerator();
        List<List<Fixture>> rounds = (List<List<Fixture>>) fixtureGenerator.getFixtures(team_name, teamAmblem, true);


        for (int i = 0; i < rounds.size(); i++) {
            List<Fixture> round = (List<Fixture>) rounds.get(i);

            for (Fixture fixtures : round) {
                fixture.add(fixtures);
            }
        }



        TOTAL_NUM_ITEMS = fixture.size();
        ITEMS_PER_PAGE = fixture.size() / rounds.size();
        ITEMS_REMAINING = TOTAL_NUM_ITEMS % ITEMS_PER_PAGE;
        LAST_PAGE = TOTAL_NUM_ITEMS/ITEMS_PER_PAGE;
        totalPages = TOTAL_NUM_ITEMS / ITEMS_PER_PAGE;




        rv = (RecyclerView) findViewById(R.id.rv_fixture);



        rv.setLayoutManager(new LinearLayoutManager(this));


        rv.setAdapter(new FixtureViewHolder(DrawActivity.this, generatePage(currentPage)));


        rv.setOnTouchListener(new OnSwipeTouchListener(this) {

            public void onSwipeRight() {
                if (currentPage == totalPages) {
                    currentPage -= 1;
                    week.setText("Week "+String.valueOf(currentPage+1));
                    Slide slide = new Slide();
                    slide.setSlideEdge(Gravity.END);
                    TransitionManager.beginDelayedTransition(rv, slide);
                    rv.setAdapter(new FixtureViewHolder(DrawActivity.this, generatePage(currentPage)));


                }else if (currentPage >= 1 && currentPage < totalPages) {
                    currentPage -= 1;
                    week.setText("Week "+String.valueOf(currentPage+1));
                    Slide slide = new Slide();
                    slide.setSlideEdge(Gravity.END);
                    TransitionManager.beginDelayedTransition(rv, slide);
                    rv.setAdapter(new FixtureViewHolder(DrawActivity.this, generatePage(currentPage)));

                }else{

                }
            }
            public void onSwipeLeft() {
                if (currentPage == 0) {
                    currentPage += 1;
                    week.setText("Week "+String.valueOf(currentPage+1));

                    Slide slide = new Slide();
                    slide.setSlideEdge(Gravity.START);
                    TransitionManager.beginDelayedTransition(rv, slide);

                    rv.setAdapter(new FixtureViewHolder(DrawActivity.this, generatePage(currentPage)));

                }else if (currentPage >= 1 && currentPage < totalPages) {
                    currentPage += 1;
                    if(currentPage == totalPages){
                        week.setText("Week "+String.valueOf(currentPage));
                    }else{
                        week.setText("Week "+String.valueOf(currentPage+1));
                    }

                    Slide slide = new Slide();
                    slide.setSlideEdge(Gravity.START);
                    TransitionManager.beginDelayedTransition(rv, slide);
                    rv.setAdapter(new FixtureViewHolder(DrawActivity.this, generatePage(currentPage)));

                }

                else{

                }
            }
        });



    }


    public ArrayList<Fixture> generatePage(int currentPage)
    {
        int startItem=(currentPage*ITEMS_PER_PAGE);
        int numOfData=ITEMS_PER_PAGE;
        ArrayList<Fixture> pageData=new ArrayList<>();

        if (currentPage==LAST_PAGE && ITEMS_REMAINING>0)
        {
            for (int i=startItem;i<startItem+ITEMS_REMAINING;i++)
            {
                pageData.add(fixture.get(i));
            }
        }else
        {
            for (int i=startItem;i<startItem+numOfData;i++)
            {
                pageData.add(fixture.get(i));
            }
        }
        return pageData;
    }


    public void onStop () {
        super.onStop();
        overridePendingTransition(
                R.anim.enter_act, R.anim.exit_act);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(
                R.anim.enter_act, R.anim.exit_act);
    }
}