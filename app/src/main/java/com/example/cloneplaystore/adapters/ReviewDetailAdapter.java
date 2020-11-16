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
import com.example.cloneplaystore.model.Review;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ReviewDetailAdapter extends RecyclerView.Adapter<ReviewDetailAdapter.ViewHolder> {
    private final List<Review> reviews;

    public ReviewDetailAdapter(List<Review> reviews, Context mContext) {
        this.reviews = reviews;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.app_review_item, parent, false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.review.setText(reviews.get(position).getText());
        holder.reviewerName.setText(reviews.get(position).getUserName());
        Picasso.get()
                .load(reviews.get(position).getUserImage())
                .placeholder(R.drawable.load)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView review;
        public final TextView reviewerName;
        public final ImageView imageView;

        ViewHolder(View view) {
            super(view);
            review=view.findViewById(R.id.reviewContent);
            reviewerName=view.findViewById(R.id.reviewer);
            reviewerName.setVisibility(View.VISIBLE);
            imageView=view.findViewById(R.id.review_profile_pic);
            imageView.setVisibility(View.VISIBLE);
            imageView.setClipToOutline(true);
        }
    }
}
