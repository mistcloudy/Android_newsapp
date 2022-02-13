package com.News.ns2.adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.News.ns2.CancelPopup;
import com.News.ns2.Memo2Activity;
import com.News.ns2.R;
import com.News.ns2.models.Memo;

import java.util.ArrayList;


public class MemoRecyclerAdapter2 extends RecyclerView.Adapter<MemoRecyclerAdapter2.ViewHolder> implements com.News.ns2.adapters.OnItemClickListener {


    public void clearItem() {
        mData.clear();
    }

    private final Context mContext;
    private ArrayList<Memo> mData = new ArrayList<>();
    RecyclerView recyclerView;
    com.News.ns2.adapters.OnItemClickListener listener;

    /*public void removeitem(long id) {
        mMemoFacade.delete(id);
        mData.remove(id);
        notifyDataSetChanged();
    }*/
/* private Stack<Pair<Integer, Memo>> mUndoStack;

    public void delete(int adapterPosition) {
        mUndoStack.push(new Pair<>(adapterPosition, mData.remove(adapterPosition)));
        notifyItemRemoved(adapterPosition);
        notifyItemRangeChanged(adapterPosition, mData.size());
    }


    public void undo() {
        Pair<Integer, Memo> pair = mUndoStack.pop();
        mData.add(pair.first, pair.second);
        notifyItemInserted(pair.first);
    }

    public void move(int adapterPosition, int targetPosition) {
        mData.add(targetPosition, mData.remove(adapterPosition));
    }*/

    // EventBus 용 이벤트
   /* public static class ItemClickEvent {
        public ItemClickEvent(View imageView, View titleView, View contentView, String key) {
           // this.position = position;
            this.key = key;
            this.imageView = imageView;
            this.titleView = titleView;
            this.contentView = contentView;
        }

       // public int position;
        public String key;
        public View imageView;
        public View titleView;
        public View contentView;


    }
*/







    public MemoRecyclerAdapter2(Context context)
 {
        mContext = context;

       // mListener = listener;
       // mLongListener = longListener;

     //   mUndoStack = new Stack<>();
    }

    public void addItem(Memo item) {
        mData.add(item);
        notifyItemChanged(0);
    }

    public Memo getItem(int position){
        return mData.get(position);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 뷰를 새로 만들 때
        View convertView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_memo, parent, false);
        return new ViewHolder(convertView);


    }




    @Override
    public int getItemCount() {
        return mData.size();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull final com.News.ns2.adapters.MemoRecyclerAdapter2.ViewHolder holder, final int position) {
        // 데이터
        Memo memo = mData.get(position);

        // 화면에 뿌리기
        holder.titleTextView.setText(memo.getTitle());
       holder.contentTextView.setText(memo.getContent());
       // holder.date.setText(memo.getDate());
        if (memo.getImagePath() != null) {
            Glide.with(mContext).load(memo.getImagePath()).into(holder.imageView);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // MemoActivity#onItemClick
                Intent intent = new Intent(mContext, Memo2Activity.class);
                intent.putExtra("key",holder.date.getText().toString());
                mContext.startActivity(intent);
            }
        });

        holder.delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // MemoActivity#onItemClick
                Intent intent = new Intent(mContext, CancelPopup.class);
                intent.putExtra("key",holder.date.getText().toString());
                intent.putExtra("position", position);
               // mContext.startActivityForResult(intent, 202);

            }
        });


       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            holder.itemView.setOnContextClickListener(new View.OnContextClickListener() {
                @Override
                public boolean onContextClick(View v) {
                    EventBus.getDefault().post(new ItemConClickEvent(
                            position,
                            memo.getId()));
                    return true;
                }
            });
        }*/

    }

    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    @Override
    public void OnItemClick(ViewHolder holder, View view, int position) {
        if(listener != null){
            listener.OnItemClick(holder,view,position);
        }
    }

    //외부에서 리스너를 설정할 수 있도록 메서드 추가 하기
    public void setItemClickListener(com.News.ns2.adapters.OnItemClickListener listener){
        this.listener = listener;
    }


    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView date;
        TextView titleTextView;
        TextView contentTextView;
        ImageView imageView;
        Button delete_btn;

        public ViewHolder(View itemView) {
            super(itemView);

            // 레이아웃 들고 오기
            TextView date = (TextView) itemView.findViewById(R.id.date_text);
            TextView titleTextView = (TextView) itemView.findViewById(R.id.title_text);
            TextView contentTextView = (TextView) itemView.findViewById(R.id.content_text);
            ImageView imageView = (ImageView) itemView.findViewById(R.id.image_view);
            Button delete_btn = itemView.findViewById(R.id.delete_btn);
            //itemView.setOnCreateContextMenuListener(this);
            // 뷰 홀더에 넣는다
            this.date = date;
            this.titleTextView = titleTextView;
            this.contentTextView = contentTextView;
            this.imageView = imageView;


        }



       /* @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
          ((Activity) itemView.getContext()).getMenuInflater().inflate(R.menu.context_menu_memo, menu);
         //   MenuItem delete = menu.add(Menu.NONE, R.id.action_delete, 1, "삭제");
            //delete.setOnMenuItemClickListener(onMenuItemClickListener);

        }*/


     /*   @Override
        public boolean onContextItemSelected(MenuItem item) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            switch (item.getItemId()) {
                case R.id.action_delete:
                    // 삭제를 누르면 확인을 받고 싶다
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle("확인");
                    builder.setMessage("정말 삭제하시겠습니까");
                    builder.setIcon(R.mipmap.ic_launcher);
                    // 긍정 버튼
                    builder.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            deleteMemo(info.id);
                        }
                    });
                    // 부정 버튼
                    builder.setNegativeButton("취소", null);
                    builder.show();
                    return true;
                default:
                    return onContextItemSelected(item);
            }
        }

        private void showCustomDialog() {
            View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_login, null, false);
            final EditText idEditText = (EditText) view.findViewById(R.id.id_edit);
            final EditText passWordEditText = (EditText) view.findViewById(R.id.password_edit);

            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle("확인");
            builder.setMessage("정말 삭제하시겠습니까");
            builder.setIcon(R.mipmap.ic_launcher);
            builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String id = idEditText.getText().toString();
                    String pass = passWordEditText.getText().toString();
                  //  Toast.makeText(MemoActivity.this, id + " " + pass, Toast.LENGTH_SHORT).show();
                }
            });
            builder.setNegativeButton("취소", null);
            builder.setView(view);
            builder.show();
        }
        */




       /* private final MenuItem.OnMenuItemClickListener onMenuItemClickListener = new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_delete:
                        mData.remove(getAdapterPosition());
                        mDbHelper.getWritableDatabase().delete(MemoContract.MemoEntry.TABLE_NAME, "_id=" + getAdapterPosition(), null);
                        notifyItemRemoved(getAdapterPosition());
                        notifyItemRangeRemoved(getAdapterPosition(), mData.size());
                        notifyItemRangeChanged(getAdapterPosition(), mData.size());
                        notifyDataSetChanged();
                        break;
                }
                return true;
            }
        };*/

    }


}




     /*   @Override
        public boolean onContextItemSelected(MenuItem item) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            switch (item.getItemId()) {
                case R.id.action_delete:
                    // 삭제를 누르면 확인을 받고 싶다
                    AlertDialog.Builder builder = new AlertDialog.Builder(mcontext);
                    builder.setTitle("확인");
                    builder.setMessage("정말 삭제하시겠습니까");
                    builder.setIcon(R.mipmap.ic_launcher);
                    // 긍정 버튼
                    builder.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    // 부정 버튼
                    builder.setNegativeButton("취소", null);
                    builder.show();
                    return true;
                default:
                    return onContextItemSelected(item);
            }
        }*/



