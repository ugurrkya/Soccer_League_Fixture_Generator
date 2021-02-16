package com.huawei.fixture.viewmodel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.huawei.fixture.R;
import com.huawei.fixture.model.Fixture;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FixtureViewHolder extends RecyclerView.Adapter<FixtureViewHolder.ViewHolder> {


    Context mcontext;
    List<Fixture> fixtureList;



    public FixtureViewHolder(Context mcontext, List<Fixture> fixtureList) {
        this.mcontext = mcontext;
        this.fixtureList = fixtureList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        //Texts
        TextView away_team_name, home_team_name, versus;

        CircleImageView away_image, home_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            away_team_name = itemView.findViewById(R.id.away_team_name);
            home_team_name = itemView.findViewById(R.id.home_team_name);
            versus = itemView.findViewById(R.id.versus);
            away_image = itemView.findViewById(R.id.away_image);
            home_image = itemView.findViewById(R.id.home_image);



        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(mcontext).inflate(R.layout.item_fixture, viewGroup, false);
        return new FixtureViewHolder.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int position) {

        final Fixture fixture = fixtureList.get(position);

        viewHolder.home_team_name.setText(fixture.getHomeTeam());
        viewHolder.away_team_name.setText(fixture.getAwayTeam());
        Picasso.get().load(fixture.getHomeAmblem()).into(viewHolder.home_image);
        Picasso.get().load(fixture.getAwayAmblem()).into(viewHolder.away_image);









    }



    @Override
    public int getItemCount() {
        return fixtureList.size();
    }
}
