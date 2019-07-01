package com.e.series.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.e.series.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class BrowserListAdapter extends RecyclerView.Adapter<BrowserListAdapter.ViewHolder> {

    private ArrayList<BrowserItem> bList;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView poster_path;
        public TextView title;
        public TextView tagline;
        public TextView release_date;
        public TextView vote_average;
        public CardView cardItem;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            poster_path = itemView.findViewById(R.id.poster_path);
            title = itemView.findViewById(R.id.title);
            tagline = itemView.findViewById(R.id.tagline);
            release_date = itemView.findViewById(R.id.release_date);
            vote_average = itemView.findViewById(R.id.popularity);
            cardItem = itemView.findViewById(R.id.cardItem);

        }

    }

    public BrowserListAdapter(ArrayList<BrowserItem> bList) {
        this.bList = bList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BrowserItem bItem = bList.get(position);

        Picasso.get().load(bItem.getPoster_path()).into(holder.poster_path);
        holder.title.setText(bItem.getTitle());
        holder.tagline.setText(bItem.getTagline());
        holder.vote_average.setText(bItem.getVote_average());
        holder.release_date.setText(bItem.getRelease_date());

        holder.cardItem.setId(position);
    }

    @Override
    public int getItemCount() {
        return bList.size();
    }
}
