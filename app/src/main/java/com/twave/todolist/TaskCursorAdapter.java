package com.twave.todolist;

import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.twave.todolist.data.TaskContract.TaskEntity;

/**
 * Created by TIGER on 2017-05-29.
 */
// TODO (0-6) RecyclerView 에 Adapter 생성
public class TaskCursorAdapter extends RecyclerView.Adapter<TaskCursorAdapter.TaskViewHolder>{

    Context mContext;
    Cursor mCursor;

    public TaskCursorAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.one_task_layout, parent, false);

        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        int idIndex = mCursor.getColumnIndex(TaskEntity._ID);
        int descIndex = mCursor.getColumnIndex(TaskEntity.COL_DESC);
        int priorityIndex = mCursor.getColumnIndex(TaskEntity.COL_PRIORITY);

        mCursor.moveToPosition(position);

        int id = mCursor.getInt(idIndex);
        String desc = mCursor.getString(descIndex);
        int priority = mCursor.getInt(priorityIndex);

        holder.itemView.setTag(id);
        holder.tvTaskDesc.setText(desc);
        holder.tvPriority.setText(String.valueOf(priority));

        GradientDrawable priorityCircle = (GradientDrawable) holder.tvPriority.getBackground();
        int priorityColor = getPriorityColor(priority);
        priorityCircle.setColor(priorityColor);
    }

    private int getPriorityColor(int priority) {
        int priorityColor = 0;

        switch (priority) {
            case 1 :
                priorityColor = ContextCompat.getColor(mContext, R.color.materialRed);
                break;
            case 2 :
                priorityColor = ContextCompat.getColor(mContext, R.color.materialOrange);
                break;
            case 3 :
                priorityColor = ContextCompat.getColor(mContext, R.color.materialYellow);
                break;
            default:
                break;
        }

        return priorityColor;
    }

    @Override
    public int getItemCount() {
        if(mCursor == null) {
            return  0;
        }

        return mCursor.getCount();
    }

    // TODO (0-19)
    public Cursor swapCursor(Cursor c) {
        // check if this cursor is the same as the previous cursor (mCursor)
        if (mCursor == c) {
            return null; // bc nothing has changed
        }
        Cursor temp = mCursor;
        this.mCursor = c; // new cursor value assigned

        //check if this is a valid cursor, then update the cursor
        if (c != null) {
            this.notifyDataSetChanged();
        }
        return temp;
    }


    class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView tvTaskDesc;
        TextView tvPriority;

        public TaskViewHolder(View itemView) {
            super(itemView);

            tvTaskDesc = (TextView) itemView.findViewById(R.id.tvTaskDesc);
            tvPriority = (TextView) itemView.findViewById(R.id.tvPriority);
        }
    }
}
