package com.twave.todolist.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.twave.todolist.data.TaskContract.TaskEntity;

/**
 * Created by TIGER on 2017-05-29.
 */
// TODO (0-3) DB Helper 생성
public class TaskDbHelper extends SQLiteOpenHelper{

    // DB 명
    private static final String DATABASE_NAME = "tasksDB.db";
    // DB VERSION
    private static final int VERSION = 1;

    // 생성자
    public TaskDbHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 테이블을 생성한다.
        final String CREATE_TABLE = "CREATE TABLE " + TaskEntity.TABLE_NAME + " (" +
                TaskEntity._ID + " INTEGER PRIMARY KEY, " +
                TaskEntity.COL_DESC + " TEXT NOT NULL, " +
                TaskEntity.COL_PRIORITY + " INTEGER NOT NULL);";

        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // DB 버전이 변경되면 기존 스키마를 삭제하고 새로 생성한다.
        db.execSQL("DROP TABLE IF EXISTS " + TaskEntity.TABLE_NAME);
        onCreate(db);
    }
}
