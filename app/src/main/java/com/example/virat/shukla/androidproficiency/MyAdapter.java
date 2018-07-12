package com.example.virat.shukla.androidproficiency;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private InnerClass[] list = null;
    private Context context;

    MyAdapter(Context context) {
        this.context = context;
    }
    void swapData(InnerClass[] updatedList) {
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
        InnerClass item = list[position];
        if (item != null) {
            holder.mDescription.setText(item.getDescription());
            holder.mTitle.setText(item.getTitle());

            Glide.with(context).load(item.getImageHref()).into(holder.mImage);
        }
    }

    @Override
    public int getItemCount() {
        return list.length;
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
