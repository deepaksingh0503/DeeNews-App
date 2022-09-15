package com.example.nuzsapp;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import org.w3c.dom.Text;

import java.net.InterfaceAddress;
import java.util.ArrayList;

public class NewsListAdaptor extends RecyclerView.Adapter<NewsListAdaptor.ViewHolder> {


    ArrayList<News> items=new ArrayList<>();
    Context context;
    NewsItemClicked listner;

    NewsListAdaptor(Context context, NewsItemClicked listner){
       this.context=context;
        this.listner=listner;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.item_news,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listner.onItemClicked(items.get(viewHolder.getAdapterPosition()));
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

       holder.loading_lottie.setVisibility(View.VISIBLE);
        News current=items.get(position);
        holder.title.setText(current.title);
        holder.author.setText(current.author);
        Glide.with(holder.itemView.getContext()).load(current.imageUrl).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
              holder.loading_lottie.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
               holder.loading_lottie.setVisibility(View.GONE);
                return false;
            }
        }).into(holder.img);


    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    void updateNews(ArrayList<News> updatedlist){
        items.clear();
        items.addAll(updatedlist);
        notifyDataSetChanged();

    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView author;
        ImageView img;
        LottieAnimationView loading_lottie;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
         title   =itemView.findViewById(R.id.title);
         author=itemView.findViewById(R.id.author);
         img=itemView.findViewById(R.id.image);
        loading_lottie=itemView.findViewById(R.id.loading_lottie);

        }
    }

    interface NewsItemClicked{
        void onItemClicked(News item);
    }
}
