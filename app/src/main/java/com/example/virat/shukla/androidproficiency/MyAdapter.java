package com.example.virat.shukla.androidproficiency;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<InnerRowsClass> list = new ArrayList<>();

    void swapData(List<InnerRowsClass> updatedList) {
        list = updatedList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        InnerRowsClass item = list.get(position);
        if (item != null) {
            holder.mView.setVisibility(View.VISIBLE);
            holder.mDescription.setText(item.getDescription());
            holder.mTitle.setText(item.getTitle());

            if (null != item.getImageHref()) {
                // Used Picasso library to load images from URL
                Picasso.get()
                        .load(item.getImageHref())
                        .placeholder(R.drawable.ic_sync)
                        .error(R.drawable.default_image)
                        .into(holder.mImage);
            } else {
                holder.mImage.setImageResource(R.drawable.default_image);
            }

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        final ImageView mImage;
        final TextView mTitle;
        final TextView mDescription;

        ViewHolder(View view) {
            super(view);
            mView = view;
            mImage = view.findViewById(R.id.image);
            mTitle = view.findViewById(R.id.title);
            mDescription = view.findViewById(R.id.description);
        }
    }
}
