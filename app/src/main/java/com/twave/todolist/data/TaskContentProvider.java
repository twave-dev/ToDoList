package com.twave.todolist.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.twave.todolist.data.TaskContract.TaskEntity;

/**
 * Created by TIGER on 2017-05-29.
 */

// TODO (1-1) ContentProvider 생성
public class TaskContentProvider extends ContentProvider{

    TaskDbHelper mTaskDbHelper;

    // TODO (3-1) Directory of tasks 와 a single item 을 구분하는 정수 상수 정의
    public static final int TASKS = 100;
    public static final int TASK_WITH_ID = 101;

    // TODO (3-2) Uri Matcher 생성
    private static final UriMatcher sUriMatcher = buildUriMatcher();

    private static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(TaskContract.AUTHORITY, TaskContract.PATH_TASKS, TASKS);
        uriMatcher.addURI(TaskContract.AUTHORITY, TaskContract.PATH_TASKS + "/#", TASK_WITH_ID);

        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        // TODO (1-2) DB Helper 객체를 생성한다.
        mTaskDbHelper = new TaskDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        // TODO (5-1) QUERY
        final SQLiteDatabase db = mTaskDbHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);
        Cursor returnCursor;

        switch (match) {
            case TASKS :
                returnCursor = db.query(TaskEntity.TABLE_NAME
                    ,projection
                    ,selection
                    ,selectionArgs
                    ,null
                    ,null
                    ,sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        /*
        CursorLoader, CursorAdapter를 쓸 때,
        Cursor를 changeCursor하기 전에 setNotificationUri를 해야 잘 작동한다.
        기존에 default로 받던 notification는 받지 않게 되고, 지정한 uri에 대해 notification을 받는 것 같다.

        query 함수에서 커서를 전달하기 전에 커서의 setNotificationUri 함수를 호출한다. 해당 함수는 커서에 등록된 관찰자에 데이터 변경을 알려 주도록 설정한다
         */
        returnCursor.setNotificationUri(getContext().getContentResolver(), uri);

        return returnCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        // TODO (4-1) INSERT
        final SQLiteDatabase db = mTaskDbHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case TASKS  :
                long id = db.insert(TaskEntity.TABLE_NAME, null, values);
                if(id > 0) {
                    returnUri = ContentUris.withAppendedId(TaskEntity.CONTENT_URI, id);
                } else {
                    throw new SQLException("Failed to insert row into " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        // TODO (6-1)
        final SQLiteDatabase db = mTaskDbHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);
        int tasksDeleted = 0;

        switch (match) {
            case TASK_WITH_ID:
                String id = uri.getPathSegments().get(1);
                tasksDeleted = db.delete(TaskEntity.TABLE_NAME, "_id = ?", new String[]{id});
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        /*
        내가 특정값을 Provider를 통해 읽어와 사용하고 있는 중에, 다른 패키지에서 그 값을 변경했을 경우 문제가 되지 않겠는가?
        이러한 문제를 해결하기 위해 Content Observer라는 것이 존재한다. 이 녀석의 역할은 특정 URI에 해당하는 데이터가 변경되었는지 감시하는 역할을 한다.
        해당 함수는 ContentResolver들에게 DB의 내용이 변경되었다는 것을 알리는 역할을 한다. 위의 소스는 delete() 함수 즉 특정 레코드를 삭제하고 있다.
        그러므로 DB의 내용이 레코드 삭제를 통해 변경 되었으므로,   ContentResolver 들에게 알리는 것이다.
        Provider 구현자는 DB가 변경되는 부분에 notifyChange() 함수를 넣우 둬야 한다. DB가 변경된 사실을 꼭 알릴 필요가 없다면 마음대로 하라. ^^
        내가 하고 싶은 말은 구현자의 몫이라는 것이다.

        우리가 사용하는 ContentObserver는 System Service이다.
        ContentProvider에서 notifyChange() 함수를 호출하면 바로 ContentService의 notifyChange() 함수가 호출되는 것이다.
         */

        if(tasksDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return tasksDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
