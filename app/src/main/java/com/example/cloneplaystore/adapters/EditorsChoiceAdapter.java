package com.example.cloneplaystore.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cloneplaystore.AppDetailActivity;
import com.example.cloneplaystore.R;
import com.example.cloneplaystore.model.App;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Random;

public class EditorsChoiceAdapter extends RecyclerView.Adapter<EditorsChoiceAdapter.AppViewHolder> {
    final Context context;
    final List<App> apps;

    public EditorsChoiceAdapter(Context context, List<App> apps) {
        this.context = context;
        this.apps = apps;
    }

    @NonNull
    @Override
    public AppViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.editors_choice_item, parent, false);
        return new AppViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppViewHolder holder, int position) {
        String poster = apps.get(position).getIcon();
        Picasso.get()
                .load(poster)
                .placeholder(R.drawable.load)
                .into(holder.imageView);
        holder.title.setText(apps.get(position).getTitle());
        String random = new Random().nextInt(50)+10+" MB";
        holder.size.setText(random);
        holder.summary.setText(apps.get(position).getSummary());
        holder.score.setText(apps.get(position).getScoreText());
    }

    @Override
    public int getItemCount() {
        return apps.size();
    }

    public class AppViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final ImageView imageView;
        final TextView title;
        final TextView size;
        final TextView summary;
        final Button moreInfo;
        final TextView score;
        public AppViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            imageView = itemView.findViewById(R.id.thumbnail);
            imageView.setClipToOutline(true);
            title = itemView.findViewById(R.id.poster_title);
            size=itemView.findViewById(R.id.app_list_size);
            summary=itemView.findViewById(R.id.summary);
            moreInfo=itemView.findViewById(R.id.moreInfo);
            moreInfo.setOnClickListener(this);
            score=itemView.findViewById(R.id.score_editor);
        }

        @Override
        public void onClick(View view) {
            Intent intent=new Intent(context, AppDetailActivity.class);
            Bundle args=new Bundle();
            args.putParcelable("App",apps.get(getAdapterPosition()));
            intent.putExtras(args);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
            context.startActivity(intent);
        }
    }
}
