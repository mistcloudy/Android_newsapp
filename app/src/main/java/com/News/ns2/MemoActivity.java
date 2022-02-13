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
import android.os.Build;
import android.os.Bundle;
import android.transition.ChangeImageTransform;
import android.transition.TransitionSet;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.RecyclerView;

import com.News.ns2.adapters.MemoRecyclerAdapter;
import com.News.ns2.db.MemoContract;
import com.News.ns2.db.MemoFacade;
import com.News.ns2.models.Memo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

public class MemoActivity extends AppCompatActivity  {
    private static final String TAG = com.News.ns2.MemoActivity.class.getSimpleName();
    public static final int REQUEST_CODE_NEW_MEMO = 1000;
    public static final int REQUEST_CODE_UPDATE_MEMO = 1001;

    private List<Memo> mMemoList;
    private MemoRecyclerAdapter mAdapter;
    private RecyclerView mMemoListView;

    private MemoFacade mMemoFacade;

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
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // 새로운 쿼리의 결과 뿌리기
                List<Memo> newMemoList = mMemoFacade.getMemoList(
                        MemoContract.MemoEntry.COLUMN_NAME_TITLE + " LIKE '%" + newText + "%'",
                        null,
                        null,
                        null,
                        null
                );
                mAdapter.swap(newMemoList);

                return true;
            }
        });




        // 메모 퍼사드
        mMemoFacade = new MemoFacade(this);

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
                Intent intent = new Intent(com.News.ns2.MemoActivity.this, Memo2Activity.class);
                startActivityForResult(intent, REQUEST_CODE_NEW_MEMO);
            }
        });

        // 데이터
        mMemoList = mMemoFacade.getMemoList();

        // 어댑터
        mAdapter = new MemoRecyclerAdapter(this, mMemoList);

        // 스와이프 삭제 구현
      /*  ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            //
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                return makeFlag(ItemTouchHelper.ACTION_STATE_DRAG, ItemTouchHelper.UP | ItemTouchHelper.DOWN) |
                        makeFlag(ItemTouchHelper.ACTION_STATE_SWIPE, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            // 스와이프 끝났을 때
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                mAdapter.delete(viewHolder.getAdapterPosition());
                Snackbar.make(viewHolder.itemView, "삭제됨", Snackbar.LENGTH_LONG)
                        .setAction("취소", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                               mAdapter.undo();
                            }
                        }).show();
                // mAdapter.removeitem(viewHolder.getAdapterPosition());
            }

        });
        helper.attachToRecyclerView(mMemoListView);
        mMemoListView.addItemDecoration(helper);
*/
        mMemoListView.setAdapter(mAdapter);

        // ContextMenu
       // registerForContextMenu(mMemoListView);
    }


    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    } // 이벤트 버스 스타트


    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    } // 이벤트 버스 스탑

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            String title = data.getStringExtra("title");
            String content = data.getStringExtra("content");
            String imagePath = data.getStringExtra("image");

            if (requestCode == REQUEST_CODE_NEW_MEMO) {
                // 새 메모
                long newRowId = mMemoFacade.insert(title, content, imagePath);
                if (newRowId == -1) {
                    // 에러
                    Toast.makeText(this, "저장이 실패하였습니다", Toast.LENGTH_SHORT).show();
                } else {
                    // 성공
                    // 리스트 갱신
                    mMemoList = mMemoFacade.getMemoList();
                }
                mAdapter.insert(mMemoList);

            } else if (requestCode == REQUEST_CODE_UPDATE_MEMO) {
                long id = data.getLongExtra("id", -1);
                int position = data.getIntExtra("position", -1);

                // 수정
                if (mMemoFacade.update(id, title, content, imagePath) > 0) {
                    mMemoList = mMemoFacade.getMemoList();
                }
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
                deleteMemo(id);
                mAdapter.delete(mMemoList, position);
                Toast.makeText(this, "삭제 되었습니다", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(this, "취소 되었습니다", Toast.LENGTH_SHORT).show();
        }
        mAdapter.notifyDataSetChanged();
    }

    // 보낸이 : MemoRecyclerAdapter
    @Subscribe
    public void onItemClick(MemoRecyclerAdapter.ItemClickEvent event) {
        Memo memo = mMemoList.get(event.position);

        Intent intent = new Intent(this, Memo2Activity.class);
        intent.putExtra("id", event.id);
        intent.putExtra("memo", memo);
        intent.putExtra("position", event.position);
        intent.putExtra("image", memo.getImagePath());


        ActivityCompat.startActivityForResult(this, intent, REQUEST_CODE_UPDATE_MEMO,
                ActivityOptionsCompat.makeSceneTransitionAnimation(this,
                        Pair.create(event.imageView, "image"),
                        Pair.create(event.titleView, "title"),
                        Pair.create(event.contentView, "content")).toBundle());
    }

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

     public void deleteMemo(long id) {
       // mMemoList.remove((int) id);
        //mAdapter.notifyDataSetChanged();
      //  mAdapter.removeitem(id);
       int deleted = mMemoFacade.delete(id);
        if (id != 0) {
            mMemoList = mMemoFacade.getMemoList();
            mAdapter.swap(mMemoList);
           // mAdapter.notifyDataSetChanged();
           // mAdapter = new MemoRecyclerAdapter(mMemoList);
            //mMemoListView.setAdapter(mAdapter);
        }
    }
}