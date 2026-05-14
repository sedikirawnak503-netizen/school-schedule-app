package com.example.schoolschedule.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolschedule.R;
import com.example.schoolschedule.adapters.TeacherAdapter;
import com.example.schoolschedule.database.DatabaseHelper;
import com.example.schoolschedule.models.Teacher;

import java.util.List;

public class TeachersActivity extends AppCompatActivity {

    private EditText etTeacherName, etTeacherEmail, etTeacherPhone, etTeacherSpecialization;
    private Button btnAddTeacher;
    private RecyclerView recyclerViewTeachers;
    private DatabaseHelper db;
    private TeacherAdapter adapter;
    private List<Teacher> teacherList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teachers);

        db = new DatabaseHelper(this);

        etTeacherName = findViewById(R.id.etTeacherName);
        etTeacherEmail = findViewById(R.id.etTeacherEmail);
        etTeacherPhone = findViewById(R.id.etTeacherPhone);
        etTeacherSpecialization = findViewById(R.id.etTeacherSpecialization);
        btnAddTeacher = findViewById(R.id.btnAddTeacher);
        recyclerViewTeachers = findViewById(R.id.recyclerViewTeachers);

        recyclerViewTeachers.setLayoutManager(new LinearLayoutManager(this));

        loadTeachers();

        btnAddTeacher.setOnClickListener(v -> addTeacher());
    }

    private void loadTeachers() {
        teacherList = db.getAllTeachers();
        adapter = new TeacherAdapter(this, teacherList, db);
        recyclerViewTeachers.setAdapter(adapter);
    }

    private void addTeacher() {
        String name = etTeacherName.getText().toString().trim();
        String email = etTeacherEmail.getText().toString().trim();
        String phone = etTeacherPhone.getText().toString().trim();
        String specialization = etTeacherSpecialization.getText().toString().trim();

        if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || specialization.isEmpty()) {
            Toast.makeText(this, "يرجى ملء جميع الحقول", Toast.LENGTH_SHORT).show();
            return;
        }

        Teacher teacher = new Teacher();
        teacher.setName(name);
        teacher.setEmail(email);
        teacher.setPhone(phone);
        teacher.setSpecialization(specialization);

        long result = db.addTeacher(teacher);
        if (result > 0) {
            Toast.makeText(this, "تم إضافة المعلم بنجاح", Toast.LENGTH_SHORT).show();
            etTeacherName.setText("");
            etTeacherEmail.setText("");
            etTeacherPhone.setText("");
            etTeacherSpecialization.setText("");
            loadTeachers();
        } else {
            Toast.makeText(this, "فشل إضافة المعلم", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadTeachers();
    }
}
