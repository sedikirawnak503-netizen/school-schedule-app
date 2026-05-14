package com.example.schoolschedule.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolschedule.R;
import com.example.schoolschedule.activities.EditScheduleActivity;
import com.example.schoolschedule.database.DatabaseHelper;
import com.example.schoolschedule.models.Schedule;

import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder> {

    private Context context;
    private List<Schedule> scheduleList;
    private DatabaseHelper db;

    public ScheduleAdapter(Context context, List<Schedule> scheduleList, DatabaseHelper db) {
        this.context = context;
        this.scheduleList = scheduleList;
        this.db = db;
    }

    @NonNull
    @Override
    public ScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_schedule, parent, false);
        return new ScheduleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleViewHolder holder, int position) {
        Schedule schedule = scheduleList.get(position);

        holder.tvClassName.setText("الفصل: " + schedule.getClassName());
        holder.tvTeacherName.setText("المعلم: " + schedule.getTeacherName());
        holder.tvSubjectName.setText("المادة: " + schedule.getSubjectName());
        holder.tvDay.setText("اليوم: " + schedule.getDay());
        holder.tvTime.setText(schedule.getStartTime() + " - " + schedule.getEndTime());
        holder.tvLocation.setText("المكان: " + schedule.getLocation());

        holder.btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(context, EditScheduleActivity.class);
            intent.putExtra("schedule_id", schedule.getId());
            context.startActivity(intent);
        });

        holder.btnDelete.setOnClickListener(v -> {
            db.deleteSchedule(schedule.getId());
            scheduleList.remove(position);
            notifyItemRemoved(position);
            Toast.makeText(context, "تم حذف الجدول", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return scheduleList.size();
    }

    public static class ScheduleViewHolder extends RecyclerView.ViewHolder {
        TextView tvClassName, tvTeacherName, tvSubjectName, tvDay, tvTime, tvLocation;
        Button btnEdit, btnDelete;

        public ScheduleViewHolder(@NonNull View itemView) {
            super(itemView);
            tvClassName = itemView.findViewById(R.id.tvClassName);
            tvTeacherName = itemView.findViewById(R.id.tvTeacherName);
            tvSubjectName = itemView.findViewById(R.id.tvSubjectName);
            tvDay = itemView.findViewById(R.id.tvDay);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
