package com.surajapps.networkapp;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by upret on 4/27/2017.
 */

class RecyclerVAdapter extends RecyclerView.Adapter<RecyclerVAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<Book> list;

    public RecyclerVAdapter(Context context, ArrayList<Book> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_row, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Book book = list.get(position);
        holder.title.setText(book.getTitle());
        holder.description.setText(book.getDescription());
        Picasso.with(context)
                .load(book.getCoverImg())
                .into(holder.imgCover);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView title, description;
        private ImageView imgCover;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            description = (TextView) itemView.findViewById(R.id.description);
            imgCover = (ImageView) itemView.findViewById(R.id.imgCover);
        }
    }
}
