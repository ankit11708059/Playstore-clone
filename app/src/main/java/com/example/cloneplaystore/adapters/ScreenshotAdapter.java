package com.example.cloneplaystore.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cloneplaystore.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ScreenshotAdapter extends RecyclerView.Adapter<ScreenshotAdapter.ViewHolder> {
    private final List<String> screenshots;

    public ScreenshotAdapter(List<String> screenshots) {
        this.screenshots = screenshots;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.app_screenshot_item, parent, false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Picasso.get()
                .load(screenshots.get(position))
                .into(holder.screenshot);
    }

    @Override
    public int getItemCount() {
        return screenshots.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public final ImageView screenshot;

        ViewHolder(View view) {
            super(view);
            screenshot=view.findViewById(R.id.screenshotItem);
            screenshot.setClipToOutline(true);
        }
    }
}
