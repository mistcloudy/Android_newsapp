package com.News.ns2;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

//import com.facebook.drawee.backends.pipeline.Fresco;
//import com.facebook.drawee.view.SimpleDraweeView;

public class MynewsAdapter extends RecyclerView.Adapter<MynewsAdapter.MyViewHolder> {
    private List<NewsData> mDataset;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        androidx.constraintlayout.widget.ConstraintLayout v = (androidx.constraintlayout.widget.ConstraintLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.row_news , parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        NewsData news = mDataset.get(position);

        holder.TextView_title.setText(news.getTitle());
        holder.TextView_content.setText(news.getContent());

        Uri uri = Uri.parse(news.getUrlTolmage());
      //  holder.ImageView_news.setImageURI(uri);

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView TextView_title;
        public TextView TextView_content;
     //   public SimpleDraweeView ImageView_news;

        public MyViewHolder(View v){
            super(v);
            TextView_title = v.findViewById(R.id.TextView_title);
            TextView_content = v.findViewById(R.id.TextView_content);
        //    ImageView_news = v.findViewById(R.id.ImageView_news);
        }
    }

    public MynewsAdapter(List<NewsData> myDataset, Context context){
        myDataset = myDataset;
      //  Fresco.initialize(context);
    }
}
