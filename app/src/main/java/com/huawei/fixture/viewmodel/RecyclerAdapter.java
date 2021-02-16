package com.huawei.fixture.viewmodel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.huawei.fixture.R;
import com.huawei.fixture.model.Team;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyviewHolder> {

    Context context;
    List<Team> teamList;

    public RecyclerAdapter(Context context, List<Team> teamList) {
        this.context = context;
        this.teamList = teamList;
    }

    public void setTeamList(List<Team> teamList) {
        this.teamList = teamList;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerAdapter.MyviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_adapter,parent,false);
        return new MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.MyviewHolder holder, int position) {
        holder.teamName.setText(teamList.get(position).getTeamName().toString());

        Glide.with(context).load(teamList.get(position).getTeamAmblem()).apply(RequestOptions.centerCropTransform()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        if(teamList != null){
            return teamList.size();
        }
        return 0;

    }

    public class MyviewHolder extends RecyclerView.ViewHolder {
        TextView teamName;
        CircleImageView image;

        public MyviewHolder(View itemView) {
            super(itemView);
            teamName = (TextView)itemView.findViewById(R.id.team_name);
            image = (CircleImageView) itemView.findViewById(R.id.image);
        }
    }
}