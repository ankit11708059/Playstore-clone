package com.example.cloneplaystore.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cloneplaystore.R;
import com.example.cloneplaystore.model.App;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Random;

public class AppAdapter extends RecyclerView.Adapter<AppAdapter.AppViewHolder> {
    final Context context;
    final List<App> apps;
    final AppClickListener clickListener;

    public AppAdapter(Context context, List<App> apps,AppClickListener clickListener) {
        this.context = context;
        this.apps = apps;
        this.clickListener=clickListener;
    }

    @NonNull
    @Override
    public AppViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_item, parent, false);
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
        String random=new Random().nextInt(50)+10+" MB";
        holder.size.setText(random);
    }

    @Override
    public int getItemCount() {
        return apps.size();
    }

    public class AppViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final ImageView imageView;
        final TextView title;
        final TextView size;
        public AppViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            imageView = itemView.findViewById(R.id.thumbnail);
            imageView.setClipToOutline(true);
            title = itemView.findViewById(R.id.poster_title);
            size=itemView.findViewById(R.id.app_list_size);
        }

        @Override
        public void onClick(View view) {
            if(clickListener!=null){
                clickListener.onClickApp(apps.get(getAdapterPosition()));
            }
        }
    }
}
