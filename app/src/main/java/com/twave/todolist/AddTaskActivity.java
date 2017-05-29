package com.twave.todolist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

// TODO (0-8) 작업을 추가하는 Activity 생성
public class AddTaskActivity extends AppCompatActivity {

    private int mPriority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        // TODO (0-12) 우선순위 초기화
        ((RadioButton)findViewById(R.id.radButton1)).setChecked(true);
        mPriority = 1;
    }


    // TODO (0-10) 우선순위 메소드 생성
    public void onPrioritySelected(View view) {
        // TODO (0-13)
        if(((RadioButton)findViewById(R.id.radButton1)).isChecked()) {
            mPriority = 1;
        } else if(((RadioButton)findViewById(R.id.radButton2)).isChecked()) {
            mPriority = 2;
        } else if(((RadioButton)findViewById(R.id.radButton3)).isChecked()) {
            mPriority = 3;
        }
    }

    // TODO (0-11) 작업 추가 메소드 생성
    public void onClickAddTask(View view) {
        // Not yet implemented
    }
}
