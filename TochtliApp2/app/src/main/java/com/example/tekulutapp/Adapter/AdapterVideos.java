package com.example.tekulutapp.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tekulutapp.Models.Videos;
import com.example.tekulutapp.R;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;

public class AdapterVideos extends RecyclerView.Adapter<AdapterVideos.ViewHolder> implements View.OnClickListener{
    ArrayList<Videos> model;
    LayoutInflater inflater;
    //LISTENER
    private View.OnClickListener listener;
    public AdapterVideos(Context context, ArrayList<Videos> model) {
        this.inflater = LayoutInflater.from(context);
        this.model = model;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.view_videos_single,parent,false);
        view.setOnClickListener(this);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String name = model.get(position).getNombre();
        String url = model.get(position).getUrl();

        holder.video.setText(name);
        holder.videito.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {

                String videoId = url;
                youTubePlayer.cueVideo(videoId,0);

            }
        });


    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    @Override
    public void onClick(View view) {
        if(listener != null){
            listener.onClick(view);
        }

    }
    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView video;
        YouTubePlayerView videito;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            videito = itemView.findViewById(R.id.main_video);
            video = itemView.findViewById(R.id.txt_video_name);

        }
    }
}
