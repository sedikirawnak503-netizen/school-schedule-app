package com.example.schoolschedule.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.schoolschedule.R;
import com.example.schoolschedule.database.DatabaseHelper;
import com.example.schoolschedule.models.Schedule;

public class AddScheduleActivity extends AppCompatActivity {

    private EditText etClassName, etTeacherName, etSubjectName, etDay, etStartTime, etEndTime, etLocation;
    private Button btnSaveSchedule, btnCancelSchedule;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule);

        // إعداد قاعدة البيانات
        db = new DatabaseHelper(this);

        // ربط العناصر
        etClassName = findViewById(R.id.etClassName);
        etTeacherName = findViewById(R.id.etTeacherName);
        etSubjectName = findViewById(R.id.etSubjectName);
        etDay = findViewById(R.id.etDay);
        etStartTime = findViewById(R.id.etStartTime);
        etEndTime = findViewById(R.id.etEndTime);
        etLocation = findViewById(R.id.etLocation);
        btnSaveSchedule = findViewById(R.id.btnSaveSchedule);
        btnCancelSchedule = findViewById(R.id.btnCancelSchedule);

        // حفظ الجدول
        btnSaveSchedule.setOnClickListener(v -> saveSchedule());

        // إلغاء العملية
        btnCancelSchedule.setOnClickListener(v -> finish());
    }

    private void saveSchedule() {
        String className = etClassName.getText().toString().trim();
        String teacherName = etTeacherName.getText().toString().trim();
        String subjectName = etSubjectName.getText().toString().trim();
        String day = etDay.getText().toString().trim();
        String startTime = etStartTime.getText().toString().trim();
        String endTime = etEndTime.getText().toString().trim();
        String location = etLocation.getText().toString().trim();

        // التحقق من المدخلات
        if (className.isEmpty() || teacherName.isEmpty() || subjectName.isEmpty() ||
            day.isEmpty() || startTime.isEmpty() || endTime.isEmpty() || location.isEmpty()) {
            Toast.makeText(this, "يرجى ملء جميع الحقول", Toast.LENGTH_SHORT).show();
            return;
        }

        // إنشاء جدول جديد
        Schedule schedule = new Schedule();
        schedule.setClassName(className);
        schedule.setTeacherName(teacherName);
        schedule.setSubjectName(subjectName);
        schedule.setDay(day);
        schedule.setStartTime(startTime);
        schedule.setEndTime(endTime);
        schedule.setLocation(location);

        // حفظ في قاعدة البيانات
        long result = db.addSchedule(schedule);
        if (result > 0) {
            Toast.makeText(this, "تم حفظ الجدول بنجاح", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "فشل حفظ الجدول", Toast.LENGTH_SHORT).show();
        }
    }
}
