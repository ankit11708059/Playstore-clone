package com.example.cloneplaystore.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cloneplaystore.AppDetailActivity;
import com.example.cloneplaystore.R;
import com.example.cloneplaystore.model.App;
import com.squareup.picasso.Picasso;

import java.util.List;

public class searchAdapter extends RecyclerView.Adapter<searchAdapter.ViewHolder> {
    final Context mContext;
    final List<App> apps;
    public searchAdapter(Context context, List<App> searchList) {
        apps=searchList;
        mContext=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.search_recycler_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String poster = apps.get(position).getIcon();
        Picasso.get()
                .load(poster)
                .placeholder(R.drawable.load)
                .into(holder.imageView);
        holder.textView.setText(apps.get(position).getTitle());
        holder.developerName.setText(apps.get(position).getDeveloper());
        holder.rating.setText(apps.get(position).getScoreText());
    }

    @Override
    public int getItemCount() {
        return apps.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView imageView;
        final TextView textView;
        final TextView developerName;
        final TextView rating;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.search_icon);
            imageView.setClipToOutline(true);
            textView=itemView.findViewById(R.id.search_title);
            developerName=itemView.findViewById(R.id.search_developer);
            rating=itemView.findViewById(R.id.search_rating);
            itemView.setOnClickListener(v -> {
                Intent intent=new Intent(mContext, AppDetailActivity.class);
                Bundle args=new Bundle();
                args.putParcelable("App",apps.get(getAdapterPosition()));
                intent.putExtras(args);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                mContext.startActivity(intent);
            });
        }
    }
}
