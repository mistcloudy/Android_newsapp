package com.News.ns2.Recycler;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.News.ns2.DetailActivity;
import com.News.ns2.R;
import com.News.ns2.Room.AppDatabase;
import com.News.ns2.Room.User;

import java.util.ArrayList;


public class RecyclerAdapter extends Adapter<RecyclerAdapter.MyViewHolder> {


    private ArrayList<User> userData = new ArrayList<>();


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.memorecycler_itemview,parent,false);
        return new MyViewHolder(view); // 바인딩된 데이터가 리사이클러뷰에 채워짐
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.onBind(userData.get(position), position);// 바인드 해주기 위해 onbind로 이동 , 몇번째 데이터인지도 확
    }

    @Override
    public int getItemCount() {
        return userData.size();
    } //몇개의 데이터가 있는지 반환

    public void addItem(User user) {
        userData.add(user);
        notifyDataSetChanged();
    }

    public void addItems(ArrayList<User> users) {
        userData = users;
        notifyDataSetChanged();//함수 변경사항 고지
    }
//함수를 통해 들어온 데이터를 리스트에 담음
    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView key;
        private TextView title;
        private TextView description;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            key = itemView.findViewById(R.id.key);
            title = itemView.findViewById(R.id.memoTextView1);
            description = itemView.findViewById(R.id.memoTextView2);
        }

        public void onBind(User user, int position) {
            String s = "" + (position+1);
            key.setText(s);
            title.setText(user.getTitle());
            description.setText(user.getDes());
            //값 넣어주기
            itemView.setOnLongClickListener(v -> { //삭제 ?
                userData.remove(user);
                AppDatabase.getInstance(itemView.getContext()).userDao().delete(user);

                notifyDataSetChanged();
                return false;
            });

            itemView.setOnClickListener(v -> { //수정 ?

                Intent intent = new Intent(itemView.getContext(), DetailActivity.class);
                intent.putExtra("data", user);
                itemView.getContext().startActivity(intent);

            });
        }
    }
}
