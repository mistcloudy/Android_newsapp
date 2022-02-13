package com.News.ns2;

/*import android.content.Intent;
import android.os.Bundle;

import com.example.memo.adapters.MemoAdapter;
import com.example.memo.models.memo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;  */

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.transition.ChangeImageTransform;
import android.transition.TransitionSet;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.News.ns2.adapters.MemoRecyclerAdapter2;
import com.News.ns2.adapters.OnItemClickListener;
import com.News.ns2.models.Memo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class MemoActivity4 extends AppCompatActivity  {
    private static final String TAG = com.News.ns2.MemoActivity4.class.getSimpleName();
    public static final int REQUEST_CODE_NEW_MEMO = 1000;
    public static final int REQUEST_CODE_UPDATE_MEMO = 1001;

    public static final int REQUEST_TEST = 200;
    public static final int REQUEST_RE = 201;
    public static final int REQUEST_DEL = 202;

    private ArrayList<Memo> mMemoList;
    private PreferenceManager pref;
    private MemoRecyclerAdapter2 mAdapter;
    private RecyclerView mMemoListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 화면 전환 기능 켜기
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            TransitionSet set = new TransitionSet();
            set.addTransition(new ChangeImageTransform());
            getWindow().setExitTransition(set);
            getWindow().setEnterTransition(set);
        } // 버전 확인

        SearchView searchView = (SearchView) findViewById(R.id.search_view);

       /* searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // 새로운 쿼리의 결과 뿌리기
                List<Memo> newMemoList = PreferenceManager. (
                        MemoContract.MemoEntry.COLUMN_NAME_TITLE + " LIKE '%" + newText + "%'",
                        null,
                        null,
                        null,
                        null
                );
                mAdapter.swap(newMemoList);

                return true;
            }
        });*/




        // 메모 퍼사드

        mMemoListView = (RecyclerView) findViewById(R.id.memo_list);

        // 애니메이션 커스터마이징
       // RecyclerView.ItemAnimator animator = new DefaultItemAnimator();
       // animator.setChangeDuration(1000);
        //mMemoListView.setItemAnimator(animator);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(com.News.ns2.MemoActivity4.this, Memo2Activity.class);
                startActivityForResult(intent, REQUEST_TEST);
            }
        });

        // 데이터
       // mMemoList = PreferenceManager.getStringArrayPrefList;

        // 어댑터
        mAdapter = new MemoRecyclerAdapter2(this);

        mMemoListView.setAdapter(mAdapter);



        SharedPreferences prefb =getSharedPreferences("memo_contain", MODE_PRIVATE);
        Collection<?> col_val =  prefb.getAll().values();
        Iterator<?> it_val = col_val.iterator();
        Collection<?> col_key = prefb.getAll().keySet();
        Iterator<?> it_key = col_key.iterator();

        while(it_val.hasNext() && it_key.hasNext()) {
            String key = (String) it_key.next();
            String value = (String) it_val.next();
            try {
                JSONObject jsonObject = new JSONObject(value);
                String title = (String) jsonObject.getString("title");
                String content = (String) jsonObject.getString("content");
                String image = (String) jsonObject.getString("image");
             //   mAdapter.addItem(new Memo(key, title, content, image));
            } catch (JSONException e) {
                Log.d("MainActivity","JSONObject : "+e);
            }

            mAdapter.notifyDataSetChanged();

        }

        mAdapter.setItemClickListener(new OnItemClickListener() {
            @Override
            public void OnItemClick(MemoRecyclerAdapter2.ViewHolder holder, View view, int position) {
                Memo item = mAdapter.getItem(position);
                Intent intent = new Intent(getApplicationContext(), Memo2Activity.class);
                String key = intent.getStringExtra("key");
                String title = intent.getStringExtra("title");
                String content = intent.getStringExtra("content");
                String image = intent.getStringExtra("image");
                intent.putExtra("key", key);
                intent.putExtra("title", title);
                intent.putExtra("content", content);
                intent.putExtra("image", image);
                startActivityForResult(intent, REQUEST_RE);
            }
        });
            }


            //
        // ContextMenu
       // registerForContextMenu(mMemoListView);


   /* @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    } // 이벤트 버스 스타트


    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    } // 이벤트 버스 스탑
*/

    public void loadMemo() {
        // SharedPreference "contain"에 저장되어 있는 모든 데이터를 가져온다
        SharedPreferences prefb =getSharedPreferences("memo_contain", MODE_PRIVATE);
        Collection<?> col_val =  prefb.getAll().values();
        Iterator<?> it_val = col_val.iterator();
        Collection<?> col_key = prefb.getAll().keySet();
        Iterator<?> it_key = col_key.iterator();


        while(it_val.hasNext() && it_key.hasNext()) {
            String key = (String) it_key.next();
            Log.d("Result", key);
            String value = (String) it_val.next();
            Log.d("Result", value);

            try {
                JSONObject jsonObject = new JSONObject(value);
                String title = (String) jsonObject.getString("title");
                String content = (String) jsonObject.getString("content");
                String image = (String) jsonObject.getString("image");
             //   mAdapter.addItem(new Memo(key, title, content, image));
            } catch (JSONException e){

             }
            mAdapter.notifyDataSetChanged();
        }


    }
  /*  private void setdata() {
        String title = loadtitle();
        String content = loadcontents();
        long id = getLong(MemoContract.MemoEntry._ID);
        String imageUri = loadimage();

        Memo memo = new Memo(title, content);
        memo.setId(id);
        memo.setImagePath(imageUri);
        mMemoList.add(memo);
    }

    private void deleteid(long id) {
        SharedPreferences sh = getSharedPreferences(MemoContract.MemoEntry.TABLE_NAME, MODE_PRIVATE);
        SharedPreferences.Editor dleEditor = sh.edit();
        dleEditor.remove(MemoContract.MemoEntry.COLUMN_NAME_TITLE);
        dleEditor.remove(MemoContract.MemoEntry.COLUMN_NAME_IMAGE);
        dleEditor.remove(MemoContract.MemoEntry.COLUMN_NAME_CONTENTS);
        dleEditor.apply();

    }
    private void saveid(long id) {
        SharedPreferences sharedPreferences = getSharedPreferences(MemoContract.MemoEntry.TABLE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong("id", id);
        editor.apply();
    }

    private void loadid(long id ) {
        SharedPreferences sharedPreferences = getSharedPreferences(MemoContract.MemoEntry.TABLE_NAME, Context.MODE_PRIVATE);
        sharedPreferences.getLong("id", id);
    }

        private void savetitle() {
        SharedPreferences sharedPreferences = getSharedPreferences(MemoContract.MemoEntry.TABLE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(mMemoList);
        editor.putString("title", json);
        editor.apply();
    }

    private String loadtitle() {
        SharedPreferences sharedPreferences = getSharedPreferences(MemoContract.MemoEntry.TABLE_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("title", null);
        Type type = new TypeToken<ArrayList<Memo>>() {
        }.getType();
        mMemoList = gson.fromJson(json, type);
        if (mMemoList == null) {
            mMemoList = new ArrayList<>();
        }
        return json;
    }

    private void savecontents() {
        SharedPreferences sharedPreferences = getSharedPreferences(MemoContract.MemoEntry.TABLE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(mMemoList);
        editor.putString("content", json);
        editor.apply();
    }

    private String loadcontents() {
        SharedPreferences sharedPreferences = getSharedPreferences(MemoContract.MemoEntry.TABLE_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("content", null);
        Type type = new TypeToken<ArrayList<Memo>>() {
        }.getType();
        mMemoList = gson.fromJson(json, type);
        if (mMemoList == null) {
            mMemoList = new ArrayList<>();
        }
        return json;
    }

    private void saveimage() {
        SharedPreferences sharedPreferences = getSharedPreferences(MemoContract.MemoEntry.TABLE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(mMemoList);
        editor.putString("image", json);
        editor.apply();
    }

    private String loadimage() {
        SharedPreferences sharedPreferences = getSharedPreferences(MemoContract.MemoEntry.TABLE_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("image", null);
        Type type = new TypeToken<ArrayList<Memo>>() {
        }.getType();
        mMemoList = gson.fromJson(json, type);
        if (mMemoList == null) {
            mMemoList = new ArrayList<>();
        }
        return json;
    }*/




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == REQUEST_TEST) {
            if (resultCode == RESULT_OK) {

                Intent intent = getIntent();
                String get_date = data.getStringExtra("date");
                String get_title = data.getStringExtra("title");
                String get_content = data.getStringExtra("content");
                String get_image = data.getStringExtra("image");

              //  mAdapter.addItem(new Memo(get_date,get_title,get_content,get_image));
                mAdapter.notifyDataSetChanged();
                Toast.makeText(com.News.ns2.MemoActivity4.this, "작성 되었습니다", Toast.LENGTH_SHORT).show();

            } else {   // RESULT_CANCEL
                Toast.makeText(com.News.ns2.MemoActivity4.this, "저장 되지 않음", Toast.LENGTH_SHORT).show();
            }

        }

        if (requestCode == REQUEST_RE) {
            if (resultCode == RESULT_OK) {
                String get_title = data.getStringExtra("title");
                String get_content = data.getStringExtra("content");
                String get_image = data.getStringExtra("image");
                mAdapter.notifyDataSetChanged();
            }

        }

        if (requestCode == REQUEST_DEL) {
            if (resultCode == RESULT_OK) {
                Intent intent = getIntent();
                String get_key = data.getStringExtra("key");
               // pref.removeKey(com.example.ns2.MemoActivity4.this,get_key);
             //   mAdapter.clearItem();
                loadMemo();
                mAdapter.notifyDataSetChanged();
            }

        }
    }



    /*    if (resultCode == RESULT_OK) {
            String title = data.getStringExtra("title");
            String content = data.getStringExtra("content");
            String imagePath = data.getStringExtra("image");

            if (requestCode == REQUEST_CODE_NEW_MEMO) {
                // 새 메모
                savetitle();
                savecontents();
                if (imagePath != null) {
                    saveimage();
                }
                setdata();
                mAdapter.insert(mMemoList);

            } else if (requestCode == REQUEST_CODE_UPDATE_MEMO) {
                long id = data.getLongExtra("id", -1);
                int position = data.getIntExtra("position", -1);

                // 수정
                    savetitle();
                    savecontents();
                    if (imagePath != null) {
                        saveimage();
                    }
                setdata();
                mAdapter.update(mMemoList, position);
            }


            Log.d(TAG, "onActivityResult: " + title + ", " + content);
            Toast.makeText(this, "저장 되었습니다", Toast.LENGTH_SHORT).show();
        } else if (resultCode == RESULT_FIRST_USER)  {

            if (requestCode == REQUEST_CODE_NEW_MEMO) {
                Toast.makeText(this, "삭제할 메모가 없습니다", Toast.LENGTH_SHORT).show();
            } else if (requestCode == REQUEST_CODE_UPDATE_MEMO) {
                long id = data.getLongExtra("id", -1);
                int position = data.getIntExtra("position", -1);
                deleteid(id);
                setdata();
                mAdapter.delete(mMemoList, position);
                Toast.makeText(this, "삭제 되었습니다", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(this, "취소 되었습니다", Toast.LENGTH_SHORT).show();
        }
        mAdapter.notifyDataSetChanged();
    }*/

    // 보낸이 : MemoRecyclerAdapter
    /*@Subscribe
    public void onItemClick(MemoRecyclerAdapter.ItemClickEvent event) {

        Intent intent = new Intent(this, Memo2Activity.class);
        intent.putExtra("key", event.key);
        intent.putExtra("title", );
        intent.putExtra("content", content);
        intent.putExtra("image", image);


        ActivityCompat.startActivityForResult(this, intent, REQUEST_RE,
                ActivityOptionsCompat.makeSceneTransitionAnimation(this,
                        Pair.create(event.imageView, "image"),
                        Pair.create(event.titleView, "title"),
                        Pair.create(event.contentView, "content")).toBundle());
    }*/

  /*  @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.action_delete:
                // 삭제를 누르면 확인을 받고 싶다
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
                return super.onContextItemSelected(item);
        }
    }

    private void showCustomDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_login, null, false);
        final EditText idEditText = (EditText) view.findViewById(R.id.id_edit);
        final EditText passWordEditText = (EditText) view.findViewById(R.id.password_edit);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("확인");
        builder.setMessage("정말 삭제하시겠습니까");
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String id = idEditText.getText().toString();
                String pass = passWordEditText.getText().toString();
                Toast.makeText(MemoActivity.this, id + " " + pass, Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("취소", null);
        builder.setView(view);
        builder.show();
    }*/


}