package com.tony.daytwo_videodatabase;

import android.app.Application;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;

public class ViewHolder extends RecyclerView.ViewHolder {

    private TextView name, desc;
    private PlayerView playerView;
    SimpleExoPlayer exoPlayer;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public  void setExoPlayer(Application application, String VideoName, String VideoDesc, String VideoUrl){
        name = itemView.findViewById(R.id.tv_name);
        desc = itemView.findViewById(R.id.tv_desc);
        playerView = itemView.findViewById(R.id.playerView);
    }
}
