package com.twave.todolist.data;

import android.provider.BaseColumns;

/**
 * Created by TIGER on 2017-05-29.
 */
// TODO (0-2) 테이블 스키마 정의
public class TaskContract {

    public static final class TaskEntity implements BaseColumns {
        // 테이블
        public static final String TABLE_NAME = "tasks";

        // 컬럼
        public static  final String COL_DESC = "desc";
        public static  final String COL_PRIORITY = "priority";
    }
}
