package com.News.ns2;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.News.ns2.Room.AppDatabase;
import com.News.ns2.Room.User;

public class SaveMemoActivity extends AppCompatActivity {
    // 작성한 데이터를 로컬db에 저장하는 역할
    private final int REQUEST_CODE = 200;
    private EditText description;
    private TextView result;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_memo);

        initialized();
    }

    private void initialized() {
        description = findViewById(R.id.description);
       // result = findViewById(R.id.result);

        db = AppDatabase.getInstance(this);

    }

    //메모저장하는 버튼
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_memo_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.save:
                make_title();
// 세이브 버튼을 누르면 메이크 타이틀 호출
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void make_title() {

        EditText editText = new EditText(getApplicationContext());
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("제목을 입력하세요");
        builder.setView(editText); // 제목을 입력하세요

        builder.setPositiveButton("저장", (dialog, which) -> {
            String s = editText.getText().toString();
            // db에 저장하기
            User memo = new User(s, description.getText().toString());
            db.userDao().insert(memo); // 메모 입력
            Toast.makeText(getApplicationContext(),"저장되었습니다",Toast.LENGTH_SHORT).show();
            dialog.dismiss();
            finish();
        });

        builder.setNegativeButton("취소", (dialog, which) -> {
            dialog.dismiss();
        });

        builder.show();
    }
}