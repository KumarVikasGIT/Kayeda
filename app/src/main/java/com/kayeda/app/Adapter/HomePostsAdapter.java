package com.kayeda.app.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kayeda.app.Activity.SinglePost;
import com.kayeda.app.Interface.OnItemClick;
import com.kayeda.app.Model.Item;
import com.kayeda.app.Parser.CoverPage;
import com.kayeda.app.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;

public class HomePostsAdapter extends RecyclerView.Adapter<HomePostsAdapter.ViewHolder> {

    private Context context;
    private List<Item> list;
    private OnItemClick itemClick;

    public HomePostsAdapter(Context context, List<Item> list, OnItemClick itemClick) {
        this.context = context;
        this.list = list;
        this.itemClick = itemClick;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_single_post, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvTitle.setText(list.get(position).getTitle());
        Log.d("IMG", "onBindViewHolder: "+ CoverPage.getCover(list.get(position).getContent()));
        Glide.with(context).load(CoverPage.getCover(list.get(position).getContent())).into(holder.ivFeatureImage);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView ivFeatureImage;
        public TextView tvTitle;

        private CardView itemCard;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivFeatureImage = itemView.findViewById(R.id.ivFeatureImage);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            itemCard=itemView.findViewById(R.id.itemCard);

            itemCard.setOnClickListener(v ->
                    itemClick.onSinglePost(getAdapterPosition(), String.valueOf(list.get(getAdapterPosition()).getId()))
            );
        }

    }
//    public String getImage(int position){
//        Document doc= Jsoup.parse(list.get(position).getContent());
//
//        Elements elements=doc.select("img");
//        return elements.get(position).attr("src");
//    }
}

