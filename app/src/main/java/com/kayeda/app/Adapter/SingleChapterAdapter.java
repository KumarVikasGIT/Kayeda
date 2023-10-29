package com.kayeda.app.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kayeda.app.Interface.OnItemClick;
import com.kayeda.app.Model.Item;
import com.kayeda.app.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class SingleChapterAdapter extends RecyclerView.Adapter<SingleChapterAdapter.ViewHolder> {

    private Context context;
    private ArrayList<String> list;

    public SingleChapterAdapter(Context context, ArrayList<String> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_single_page, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("img", "onBindViewHolder: "+list.get(position).toString());

        if (list.get(position).toString()!=null)
            Glide.with(context).load(list.get(position).toString()).into(holder.ivPage);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView ivPage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivPage = itemView.findViewById(R.id.ivPage);

        }

    }
}

