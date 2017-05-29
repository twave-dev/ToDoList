package com.twave.todolist.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by TIGER on 2017-05-29.
 */
// TODO (0-2) 테이블 스키마 정의
public class TaskContract {

    // TODO (2-1) 권한, BASE URI, Path
    public static final String AUTHORITY = "com.twave.todolist";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH_TASKS = "tasks";

    public static final class TaskEntity implements BaseColumns {
        // TODO (2-2) Content URI 정의
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_TASKS).build();

        // 테이블
        public static final String TABLE_NAME = "tasks";

        // 컬럼
        public static  final String COL_DESC = "desc";
        public static  final String COL_PRIORITY = "priority";
    }
}
