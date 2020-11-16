package com.example.cloneplaystore.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cloneplaystore.R;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {
    private final List<String> screenshots;

    public ReviewAdapter(List<String> screenshots) {
        this.screenshots = screenshots;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.app_review_item, parent, false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.review.setText(screenshots.get(position));
    }

    @Override
    public int getItemCount() {
        return screenshots.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView review;
        public final TextView reviewerName;
        public final ImageView imageView;

        ViewHolder(View view) {
            super(view);
            review=view.findViewById(R.id.reviewContent);
            reviewerName=view.findViewById(R.id.reviewer);
            reviewerName.setVisibility(View.GONE);
            imageView=view.findViewById(R.id.review_profile_pic);
            imageView.setVisibility(View.GONE);
        }
    }
}
