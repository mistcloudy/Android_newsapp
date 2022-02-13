package com.News.ns2.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class MemoDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "memo.db";

    public MemoDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // DB를 처음으로 사용할 때
    @Override
    public void onCreate(SQLiteDatabase db) {
        // TABLE 생성
        db.execSQL(MemoContract.SQL_CREATE_MEMO_TABLE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL("ALTER TABLE " + MemoContract.MemoEntry.TABLE_NAME + " ADD " + MemoContract.MemoEntry.COLUMN_NAME_IMAGE + " TEXT");
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
