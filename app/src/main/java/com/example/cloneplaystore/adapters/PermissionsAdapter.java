package com.example.cloneplaystore.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cloneplaystore.R;
import com.example.cloneplaystore.model.Permission;

import java.util.List;

public class PermissionsAdapter extends RecyclerView.Adapter<PermissionsAdapter.ViewHolder> {
    private final List<Permission> permissions;

    public PermissionsAdapter(List<Permission> reviews) {
        this.permissions = reviews;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.app_permission_item, parent, false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.review.setText(permissions.get(position).getType());
        holder.reviewerName.setText(permissions.get(position).getPermission());
    }

    @Override
    public int getItemCount() {
        return permissions.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView review;
        public final TextView reviewerName;

        ViewHolder(View view) {
            super(view);
            review=view.findViewById(R.id.permission_type);
            reviewerName=view.findViewById(R.id.permission_name);
        }
    }
}
