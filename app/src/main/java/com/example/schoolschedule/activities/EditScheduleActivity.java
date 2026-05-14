package com.example.schoolschedule.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.schoolschedule.R;
import com.example.schoolschedule.database.DatabaseHelper;
import com.example.schoolschedule.models.Schedule;

public class EditScheduleActivity extends AppCompatActivity {

    private EditText etEditClassName, etEditTeacherName, etEditSubjectName, etEditDay, etEditStartTime, etEditEndTime, etEditLocation;
    private Button btnUpdateSchedule, btnCancelEdit;
    private DatabaseHelper db;
    private int scheduleId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_schedule);

        // إعداد قاعدة البيانات
        db = new DatabaseHelper(this);

        // الحصول على معرف الجدول من Intent
        scheduleId = getIntent().getIntExtra("schedule_id", -1);

        // ربط العناصر
        etEditClassName = findViewById(R.id.etEditClassName);
        etEditTeacherName = findViewById(R.id.etEditTeacherName);
        etEditSubjectName = findViewById(R.id.etEditSubjectName);
        etEditDay = findViewById(R.id.etEditDay);
        etEditStartTime = findViewById(R.id.etEditStartTime);
        etEditEndTime = findViewById(R.id.etEditEndTime);
        etEditLocation = findViewById(R.id.etEditLocation);
        btnUpdateSchedule = findViewById(R.id.btnUpdateSchedule);
        btnCancelEdit = findViewById(R.id.btnCancelEdit);

        // تحميل بيانات الجدول
        loadScheduleData();

        // تحديث الجدول
        btnUpdateSchedule.setOnClickListener(v -> updateSchedule());

        // إلغاء العملية
        btnCancelEdit.setOnClickListener(v -> finish());
    }

    private void loadScheduleData() {
        Schedule schedule = db.getScheduleById(scheduleId);
        if (schedule != null) {
            etEditClassName.setText(schedule.getClassName());
            etEditTeacherName.setText(schedule.getTeacherName());
            etEditSubjectName.setText(schedule.getSubjectName());
            etEditDay.setText(schedule.getDay());
            etEditStartTime.setText(schedule.getStartTime());
            etEditEndTime.setText(schedule.getEndTime());
            etEditLocation.setText(schedule.getLocation());
        }
    }

    private void updateSchedule() {
        String className = etEditClassName.getText().toString().trim();
        String teacherName = etEditTeacherName.getText().toString().trim();
        String subjectName = etEditSubjectName.getText().toString().trim();
        String day = etEditDay.getText().toString().trim();
        String startTime = etEditStartTime.getText().toString().trim();
        String endTime = etEditEndTime.getText().toString().trim();
        String location = etEditLocation.getText().toString().trim();

        // التحقق من المدخلات
        if (className.isEmpty() || teacherName.isEmpty() || subjectName.isEmpty() ||
            day.isEmpty() || startTime.isEmpty() || endTime.isEmpty() || location.isEmpty()) {
            Toast.makeText(this, "يرجى ملء جميع الحقول", Toast.LENGTH_SHORT).show();
            return;
        }

        // إنشاء جدول محدث
        Schedule schedule = new Schedule();
        schedule.setId(scheduleId);
        schedule.setClassName(className);
        schedule.setTeacherName(teacherName);
        schedule.setSubjectName(subjectName);
        schedule.setDay(day);
        schedule.setStartTime(startTime);
        schedule.setEndTime(endTime);
        schedule.setLocation(location);

        // تحديث في قاعدة البيانات
        int result = db.updateSchedule(schedule);
        if (result > 0) {
            Toast.makeText(this, "تم تحديث الجدول بنجاح", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "فشل تحديث الجدول", Toast.LENGTH_SHORT).show();
        }
    }
}
